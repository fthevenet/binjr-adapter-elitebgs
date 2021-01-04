/*
 * Copyright 2019-2021 Frederic Thevenet
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package eu.fthevenet.binjr.sources.adapters.elitebgs;

import com.google.gson.Gson;
import eu.binjr.core.data.codec.Decoder;
import eu.binjr.core.data.exceptions.DecodingDataFromAdapterException;
import eu.binjr.core.data.timeseries.DoubleTimeSeriesProcessor;
import eu.binjr.core.data.timeseries.TimeSeriesProcessor;
import eu.binjr.core.data.workspace.TimeSeriesInfo;
import eu.fthevenet.binjr.sources.adapters.elitebgs.api.v5.EBGSFactionsPageV5;
import javafx.scene.chart.XYChart;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EliteBgsDecoder implements Decoder<Double> {
    private static final Logger logger = LogManager.getLogger(EliteBgsDecoder.class);
    private final Gson gson;

    public EliteBgsDecoder() {
        gson = new Gson();
    }

    @Override
    public Map<TimeSeriesInfo<Double>, TimeSeriesProcessor<Double>> decode(InputStream in, List<TimeSeriesInfo<Double>> seriesNames)
            throws IOException, DecodingDataFromAdapterException {
        Map<TimeSeriesInfo<Double>, TimeSeriesProcessor<Double>> map = new HashMap<>();
        try (InputStreamReader reader = new InputStreamReader(in)) {
            var factionPages = gson.fromJson(reader, EBGSFactionsPageV5.class);
            for (var f : factionPages.getDocs()) {
                for (var info : seriesNames) {
                    var proc = new DoubleTimeSeriesProcessor();
                    Path hierarchy = Paths.get(info.getBinding().getTreeHierarchy());
                    String parentSystem = hierarchy.getFileName().toString();
                    if (parentSystem.equalsIgnoreCase(f.getName())) {
                        parentSystem = hierarchy.getParent().getFileName().toString();
                    }
                    if (logger.isTraceEnabled()) {
                        logger.trace("Parent system for faction " + f.getName() + ": " + parentSystem);
                    }
                    // Add influence values from history
                    for (var h : f.getHistory()) {
                        if (parentSystem.equalsIgnoreCase(h.getSystem())) {
                            proc.addSample(new XYChart.Data<>(
                                    ZonedDateTime.parse(h.getUpdatedAt()),
                                    Double.isNaN(h.getInfluence()) ? 0.0 : h.getInfluence() * 100));
                        }
                    }

                    // Add current influence value
                    for (var c : f.getFactionPresence()) {
                        if (parentSystem.equalsIgnoreCase(c.getSystemName())) {
                            proc.addSample(new XYChart.Data<>(
                                    ZonedDateTime.parse(c.getUpdatedAt()),
                                    Double.isNaN(c.getInfluence()) ? 0.0 : c.getInfluence() * 100));
                        }
                    }
                    map.put(info, proc);
                }
            }
        }
        return map;
    }
}
