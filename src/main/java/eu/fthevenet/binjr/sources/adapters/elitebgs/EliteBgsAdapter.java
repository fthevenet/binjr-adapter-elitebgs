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
import eu.fthevenet.binjr.sources.adapters.elitebgs.api.EBGSPage;
import eu.fthevenet.binjr.sources.adapters.elitebgs.api.v4.EBGSFactionsPageV4;
import eu.fthevenet.binjr.sources.adapters.elitebgs.api.v4.EBGSFactionsV4;
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
    private static final String TITLE = "Elite Dangerous BGS";

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
        var systems = getPaginatedNodes(root, "Systems", "Systems", this::addSystemsPage);
        var factions = getPaginatedNodes(root, "Factions", "Factions", this::addFactionsPage);
        root.getInternalChildren().addAll(systems, factions);

        return root;
    }

    private FilterableTreeItem<TimeSeriesBinding> getPaginatedNodes(FilterableTreeItem<TimeSeriesBinding> root, String name, String id, AddPageDelegate onExpandAction) throws DataAdapterException {
        var tree = makeBranch(name, id, root.getValue().getTreeHierarchy());
        String alphabet = "123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        for (int i = 0; i < alphabet.length(); i++) {
            String letter = String.valueOf(alphabet.charAt(i));
            var newBranch = makeBranch(letter, letter, tree.getValue().getTreeHierarchy());
            // add a dummy node so that the branch can be expanded
            newBranch.getInternalChildren().add(new FilterableTreeItem<>(null));
            newBranch.expandedProperty().addListener(new ExpandListener(newBranch, letter, onExpandAction));
            tree.getInternalChildren().add(newBranch);
        }
        return tree;
    }

    private void addAllPages(FilterableTreeItem<TimeSeriesBinding> tree, String beginWith, AddPageDelegate pageDelegate) throws DataAdapterException {
        AtomicInteger nbPages = new AtomicInteger(0);
        AtomicInteger nbHits = new AtomicInteger(0);
        try (var p = Profiler.start(() -> "Retrieving " + nbHits.get() + " elements starting with '" + beginWith + "' (" + nbPages.get() + " pages)", logger::trace)) {
            EBGSPage<?> res = pageDelegate.addSinglePage(tree, beginWith, 1, true);
            if (res != null) {
                logger.info("pages=" + res.pages);
                nbPages.addAndGet(res.pages);
                nbHits.addAndGet(res.total);
                for (int page = 2; page <= nbPages.get(); page++) {
                    pageDelegate.addSinglePage(tree, beginWith, page, false);
                }
            } else {
                logger.warn("No results from synchronous call to addSystemPage");
            }
        }
    }

    private EBGSSystemsPageV4 addSystemsPage(FilterableTreeItem<TimeSeriesBinding> tree, String beginWith, int page, boolean waitForResult) throws DataAdapterException {
        Gson gson = new Gson();
        AtomicReference<EBGSSystemsPageV4> returnValue = new AtomicReference<>(null);
        var res = AsyncTaskManager.getInstance().submit(() -> {
                    var pages = gson.fromJson(getRawPageData(FRONTEND_SYSTEMS, beginWith, page), EBGSSystemsPageV4.class);
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

    private EBGSFactionsPageV4 addFactionsPage(FilterableTreeItem<TimeSeriesBinding> tree, String beginWith, int page, boolean waitForResult) throws DataAdapterException {
        Gson gson = new Gson();
        AtomicReference<EBGSFactionsPageV4> returnValue = new AtomicReference<>(null);
        var res = AsyncTaskManager.getInstance().submit(() -> {
                    var pages = gson.fromJson(getRawPageData(API_FACTIONS, beginWith, page), EBGSFactionsPageV4.class);
                    returnValue.set(pages);
                    return pages;
                },
                event -> {
                    EBGSFactionsPageV4 t = (EBGSFactionsPageV4) event.getSource().getValue();
                    Map<String, EBGSFactionsV4> m = Arrays.stream(t.docs).collect(Collectors.toMap(o -> o._id, (o -> o)));
                    for (EBGSFactionsV4 f : m.values()) {
                        var branch = makeBranch(f.name, f._id, tree.getValue().getTreeHierarchy());
                        for (var s : f.faction_presence) {
                            branch.getInternalChildren().add(makeBranch(s.system_name, f._id, branch.getValue().getTreeHierarchy()));
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
                parentHierarchy + "/" + name,
                this));
    }

    private String getRawPageData(String uriPath, String beginWith, int page) throws DataAdapterException {
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("page", Integer.toString(page)));
        params.add(new BasicNameValuePair("beginsWith", beginWith));

        String entityString = doHttpGet(craftRequestUri(uriPath, params), new BasicResponseHandler());
        logger.trace(entityString);
        return entityString;
    }

    @Override
    public String getEncoding() {
        return StandardCharsets.UTF_8.toString();
    }

    @Override
    public ZoneId getTimeZoneId() {
        return ZoneId.of("UTC");
    }

    @Override
    public String getSourceName() {
        return "[" + TITLE + "]";
    }

    @FunctionalInterface
    private interface AddPageDelegate {
        EBGSPage<?> addSinglePage(FilterableTreeItem<TimeSeriesBinding> tree, String beginWith, int page, boolean waitForResult) throws DataAdapterException;
    }

    private class ExpandListener implements ChangeListener<Boolean> {
        private final FilterableTreeItem<TimeSeriesBinding> newBranch;
        private final String beginWith;
        private final AddPageDelegate addPageDelegate;

        public ExpandListener(FilterableTreeItem<TimeSeriesBinding> newBranch,
                              String beginWith,
                              AddPageDelegate onExpandAction) {
            this.newBranch = newBranch;
            this.beginWith = beginWith;
            this.addPageDelegate = onExpandAction;
        }

        @Override
        public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
            if (newValue) {
                try {
                    // onExpandAction.apply(newBranch, beginWith);
                    addAllPages(newBranch, beginWith, addPageDelegate);
                    //remove dummy node
                    newBranch.getInternalChildren().remove(0);
                    // remove the listener so it isn't executed next time node is expanded
                    newBranch.expandedProperty().removeListener(this);
                } catch (Exception e) {
                    Dialogs.notifyException("Failed to retrieve page starting with '" + beginWith + "'", e);
                }
            }
        }
    }
}
