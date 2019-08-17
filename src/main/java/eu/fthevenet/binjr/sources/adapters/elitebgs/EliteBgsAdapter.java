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
import eu.binjr.common.logging.Profiler;
import eu.binjr.core.data.adapters.HttpDataAdapter;
import eu.binjr.core.data.adapters.TimeSeriesBinding;
import eu.binjr.core.data.async.AsyncTaskManager;
import eu.binjr.core.data.codec.Decoder;
import eu.binjr.core.data.exceptions.CannotInitializeDataAdapterException;
import eu.binjr.core.data.exceptions.DataAdapterException;
import eu.binjr.core.data.workspace.ChartType;
import eu.binjr.core.data.workspace.UnitPrefixes;
import eu.binjr.core.dialogs.Dialogs;
import eu.fthevenet.binjr.sources.adapters.elitebgs.api.v4.EBGSSystemsPageV4;
import eu.fthevenet.binjr.sources.adapters.elitebgs.api.v4.EBGSSystemsV4;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

public class EliteBgsAdapter extends HttpDataAdapter {
    private static final Logger logger = LogManager.getLogger(EliteBgsAdapter.class);
    private static final String SITE = "https://elitebgs.app";
    private static final String API_FACTIONS = "/api/ebgs/v4/factions";
    private static final String API_SYSTEMS = "/api/ebgs/v4/systems";
    private static final String FRONTEND_FACTIONS = "/frontend/factions";
    private static final String FRONTEND_SYSTEMS = "/frontend/systems";
    private static final String TITLE = "Elite BGS";

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
        return craftRequestUri(API_FACTIONS,
                new BasicNameValuePair("id", path),
                new BasicNameValuePair("timemin", Long.toString(begin.toEpochMilli())),
                new BasicNameValuePair("timemax", Long.toString(end.toEpochMilli()))
        );
    }

    @Override
    public Decoder getDecoder() {
        return new EliteBgsDecoder();
    }

    @Override
    public FilterableTreeItem<TimeSeriesBinding> getBindingTree() throws DataAdapterException {
        var root = makeBranch(TITLE, TITLE, "");
        var systems = getSystems(root);
        var factions = getFactions();
        root.getInternalChildren().addAll(systems, factions);

        return root;
    }

    private FilterableTreeItem<TimeSeriesBinding> getSystems(FilterableTreeItem<TimeSeriesBinding> root) throws DataAdapterException {
        var tree = makeBranch("Systems", "Systems", root.getValue().getTreeHierarchy());
        String alphabet = "123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        for (int i = 0; i < alphabet.length(); i++) {
            String letter = String.valueOf(alphabet.charAt(i));
            var newBranch = makeBranch(letter, letter, tree.getValue().getTreeHierarchy());
            // add a dummy node so that the branch can be expanded
            newBranch.getInternalChildren().add(new FilterableTreeItem<>(null));
            newBranch.expandedProperty().addListener(new SystemExpandListener(letter, letter, newBranch, tree));
            tree.getInternalChildren().add(newBranch);
        }
        return tree;
    }

    private void addAllSystemsPages(FilterableTreeItem<TimeSeriesBinding> tree, String beginWith) throws DataAdapterException {
        AtomicInteger nbPages = new AtomicInteger(0);
        AtomicInteger nbHits = new AtomicInteger(0);
        try (var p = Profiler.start(() -> "Retrieving " + nbHits.get() + " system(s) starting with '" + beginWith + "' (" + nbPages.get() + " pages)", logger::trace)) {
            EBGSSystemsPageV4 res = addSystemsPage(tree, beginWith, 1, true);
            if (res != null) {
                logger.info("pages=" + res.pages);
                //  var res = addSystemsPage(tree, beginWith, 1);
                nbPages.addAndGet(res.pages);
                nbHits.addAndGet(res.total);
                for (int page = 2; page <= nbPages.get(); page++) {
                    addSystemsPage(tree, beginWith, page, false);
                }
            }else{
                logger.warn("No results from synchronous call to addSystemPage");
            }
        }
    }

    private EBGSSystemsPageV4 addSystemsPage(FilterableTreeItem<TimeSeriesBinding> tree, String beginWith, int page, boolean waitForResult) throws DataAdapterException {
        Gson gson = new Gson();
        AtomicReference<EBGSSystemsPageV4> returnValue = new AtomicReference<>(null);
        var res = AsyncTaskManager.getInstance().submit(() -> {
                   var pages = gson.fromJson(getSystemsTree(page, beginWith), EBGSSystemsPageV4.class);
                   returnValue.set(pages);
                   return pages;
                },
                event -> {
                    EBGSSystemsPageV4 t = (EBGSSystemsPageV4) event.getSource().getValue();
                    Map<String, EBGSSystemsV4> m = Arrays.stream(t.docs).collect(Collectors.toMap(o -> o._id, (o -> o)));
                    for (EBGSSystemsV4 s : m.values()) {
                        var branch = makeBranch(s.name, s._id, tree.getValue().getTreeHierarchy());
                        for (var f : s.factions) {
                            branch.getInternalChildren().add(makeBranch(f.name, f.faction_id, branch.getValue().getTreeHierarchy()));
                        }
                        tree.getInternalChildren().add(branch);
                    }
                },
                event -> {
                    Dialogs.notifyException("An error occurred while retrieving tree view from source", event.getSource().getException());
                });
        if (waitForResult) {
            try {
                res.get();
            } catch (InterruptedException | ExecutionException e) {
                throw new DataAdapterException(e);
            }
        }
        return returnValue.get();
    }

    private FilterableTreeItem<TimeSeriesBinding> makeBranch(String name, String id, String parentHierarchy) {
        return new FilterableTreeItem<>(new TimeSeriesBinding(
                name,
                id,
                null,
                name,
                UnitPrefixes.METRIC,
                ChartType.LINE,
                "%",
                parentHierarchy + "\\" + name,
                this));
    }

    private String getSystemsTree(int page, String beginWith) throws DataAdapterException {
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("page", Integer.toString(page)));
        params.add(new BasicNameValuePair("beginsWith", beginWith));

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

    private class SystemExpandListener implements ChangeListener<Boolean> {
        private final String currentPath;
        private final FilterableTreeItem<TimeSeriesBinding> newBranch;
        private final FilterableTreeItem<TimeSeriesBinding> tree;
        private final String beginWith;

        public SystemExpandListener(String currentPath, String beginWith, FilterableTreeItem<TimeSeriesBinding> newBranch, FilterableTreeItem<TimeSeriesBinding> tree) {
            this.currentPath = currentPath;
            this.newBranch = newBranch;
            this.beginWith = beginWith;
            this.tree = tree;
        }

        @Override
        public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
            if (newValue) {
                try {
                    addAllSystemsPages(newBranch, beginWith);
                    //remove dummy node
                    newBranch.getInternalChildren().remove(0);
                    // remove the listener so it isn't executed next time node is expanded
                    newBranch.expandedProperty().removeListener(this);
                } catch (Exception e) {
                    Dialogs.notifyException("Failed to retrieve Systems starting with '" + beginWith+"'", e);
                }
            }
        }
    }
}
