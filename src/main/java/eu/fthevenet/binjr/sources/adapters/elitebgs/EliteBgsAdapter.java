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
import com.google.gson.JsonSyntaxException;
import eu.binjr.core.data.adapters.HttpDataAdapter;
import eu.binjr.core.data.adapters.TimeSeriesBinding;
import eu.binjr.core.data.codec.Decoder;
import eu.binjr.core.data.exceptions.CannotInitializeDataAdapterException;
import eu.binjr.core.data.exceptions.DataAdapterException;
import eu.binjr.core.data.workspace.ChartType;
import eu.binjr.core.data.workspace.UnitPrefixes;
import eu.fthevenet.binjr.sources.adapters.elitebgs.api.v4.EBGSSystemsPageV4;
import eu.fthevenet.binjr.sources.adapters.elitebgs.api.v4.EBGSSystemsV4;
import org.apache.http.NameValuePair;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.message.BasicNameValuePair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.fx.ui.controls.tree.FilterableTreeItem;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class EliteBgsAdapter extends HttpDataAdapter {
    private static final Logger logger = LogManager.getLogger(EliteBgsAdapter.class);
    private static final String SITE = "https://elitebgs.app";
    private static final String API_FACTIONS = "/api/ebgs/v4/factions";
    private static final String API_SYSTEMS = "/api/ebgs/v4/systems";
    private static final String FRONTEND_FACTIONS = "/frontend/factions";
    private static final String FRONTEND_SYSTEMS = "/frontend/systems";


    public EliteBgsAdapter() throws CannotInitializeDataAdapterException {
        this(getURL());
    }


    public EliteBgsAdapter(URL baseAddress) throws CannotInitializeDataAdapterException {
        super(baseAddress);
    }

    private static URL getURL() throws CannotInitializeDataAdapterException {
        try {
            return URI.create(SITE).toURL();
        } catch (MalformedURLException e) {
            logger.debug(e::getMessage, e);
            throw new CannotInitializeDataAdapterException(e);
        }
    }

    @Override
    protected URI craftFetchUri(String path, Instant begin, Instant end) throws DataAdapterException {
        return null;
    }

    @Override
    public Decoder getDecoder() {
        return null;
    }

    @Override
    public FilterableTreeItem<TimeSeriesBinding> getBindingTree() throws DataAdapterException {
        var root = new FilterableTreeItem<>(new TimeSeriesBinding(
                "Elite BGS",
                "",
                null,
                "Elite BGS",
                UnitPrefixes.METRIC,
                ChartType.LINE,
                "",
                "Elite BGS\\",
                this));
        var systems = getSystems();
        var factions = getFactions();
        root.getInternalChildren().addAll(systems, factions);

        return root;
    }

    private FilterableTreeItem<TimeSeriesBinding> getSystems() throws DataAdapterException {
        Gson gson = new Gson();
        var tree = new FilterableTreeItem<>(new TimeSeriesBinding(
                "Systems",
                "\\Systems\\",
                null,
                "Systems",
                UnitPrefixes.METRIC,
                ChartType.LINE,
                "%",
                "Elite BGS\\Systems",
                this));
        for (int i = 1; i < 10; i++) {
            addSystemsPage(gson, tree, i);
        }
        return tree;
    }

    private void addSystemsPage(Gson gson, FilterableTreeItem<TimeSeriesBinding> tree, int page) throws DataAdapterException {
        try {
            EBGSSystemsPageV4 t = gson.fromJson(getSystemsTree(page), EBGSSystemsPageV4.class);
            Map<String, EBGSSystemsV4> m = Arrays.stream(t.docs).collect(Collectors.toMap(o -> o._id, (o -> o)));
            for (EBGSSystemsV4 s : m.values()) {
                var branch = new FilterableTreeItem<>(new TimeSeriesBinding(
                        s.name,
                        s._id,
                        null,
                        s.name,
                        UnitPrefixes.METRIC,
                        ChartType.LINE,
                        "%",
                        tree.getValue().getTreeHierarchy() + "\\" + s.name,
                        this));
                for (var f : s.factions) {
                    branch.getInternalChildren().add(new FilterableTreeItem<>(new TimeSeriesBinding(
                            f.name,
                            f.faction_id,
                            null,
                            f.name,
                            UnitPrefixes.METRIC,
                            ChartType.LINE,
                            "%",
                            branch.getValue().getTreeHierarchy() + "\\" + f.name,
                            this)));
                }
                tree.getInternalChildren().add(branch);
            }
//            return tree;
        } catch (JsonSyntaxException e) {
            throw new DataAdapterException("An error occurred while parsing the json response to getBindingTree request", e);
        } /*catch (URISyntaxException e) {
            throw new SourceCommunicationException("Error building URI for request", e);
        }*/
    }

    private String getSystemsTree(int page) throws DataAdapterException {
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("page", Integer.toString(page)));
        params.add(new BasicNameValuePair("beginsWith", ""));

        String entityString = doHttpGet(craftRequestUri(FRONTEND_SYSTEMS, params), new BasicResponseHandler());
        logger.trace(entityString);
        return entityString;
    }

    private FilterableTreeItem<TimeSeriesBinding> getFactions() {
        var factionsRoot = new FilterableTreeItem<>(new TimeSeriesBinding(
                "Factions",
                "\\Factions\\",
                null,
                "Factions",
                UnitPrefixes.METRIC,
                ChartType.LINE,
                "",
                "Elite BGS\\Factions\\",
                this));

        return factionsRoot;
    }

    @Override
    public String getEncoding() {
        return StandardCharsets.UTF_8.toString();
    }

    @Override
    public ZoneId getTimeZoneId() {
        return null;
    }

    @Override
    public String getSourceName() {
        return "[Elite BGS]";
    }
}
