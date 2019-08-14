/*
 * Copyright 2019 Frederic Thevenet
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
import eu.fthevenet.binjr.sources.adapters.elitebgs.api.v4.EBGSFactionsPageV4;
import javafx.scene.chart.XYChart;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EliteBgsDecoder implements Decoder {
    private static final Logger logger = LogManager.getLogger(EliteBgsDecoder.class);

    //   final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").withZone(getTimeZoneId());
    @Override
    public Map<TimeSeriesInfo, TimeSeriesProcessor> decode(InputStream in, List<TimeSeriesInfo> seriesNames) throws IOException, DecodingDataFromAdapterException {
        Map<TimeSeriesInfo, TimeSeriesProcessor> map = new HashMap<>();
        Gson gson = new Gson();
        try (InputStreamReader reader = new InputStreamReader(in)) {
            var factionPages = gson.fromJson(reader, EBGSFactionsPageV4.class);
            for (var f : factionPages.docs) {
                var proc = new DoubleTimeSeriesProcessor();

                logger.info(f.toString());
                for (var h : f.history) {
                    if ("juipek".equalsIgnoreCase(h.system)) {
                        proc.addSample(new XYChart.Data<>(ZonedDateTime.parse(h.updated_at).with(LocalTime.of(0, 0)), h.influence * 100));
                    }
                }
                map.put(seriesNames.get(0), proc);
            }
        }


        return map;
    }
}
