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
import eu.fthevenet.binjr.sources.adapters.elitebgs.api.AbstractPage;
import eu.fthevenet.binjr.sources.adapters.elitebgs.api.QueryParameters;
import eu.fthevenet.binjr.sources.adapters.elitebgs.api.v4.Factions;
import eu.fthevenet.binjr.sources.adapters.elitebgs.api.v4.FactionsPage;
import eu.fthevenet.binjr.sources.adapters.elitebgs.api.v4.Systems;
import eu.fthevenet.binjr.sources.adapters.elitebgs.api.v4.SystemsPage;
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
import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

public class EliteBgsAdapter extends HttpDataAdapter implements EdbgsApiHelper {
    private static final Logger logger = LogManager.getLogger(EliteBgsAdapter.class);
    private static final String SITE = "https://elitebgs.app";
    private static final String API_FACTIONS = "/api/ebgs/v4/factions";
    private static final String API_SYSTEMS = "/api/ebgs/v4/systems";
    private static final String FRONTEND_FACTIONS = "/frontend/factions";
    private static final String FRONTEND_SYSTEMS = "/frontend/systems";
    private static final String TITLE = "ED BGS";
    private final EliteBgsDecoder eliteBgsDecoder;
    private final Gson gson;
    private final List<NameValuePair> queryFilters = new ArrayList<>();
    private final EliteBgsAdapterPreferences prefs;
    private FactionBrowsingMode browsingMode;

    public EliteBgsAdapter() throws CannotInitializeDataAdapterException {
        this(FactionBrowsingMode.BROWSE_BY_SYSTEM);
    }

    public EliteBgsAdapter(FactionBrowsingMode browsingMode, Collection<NameValuePair> queryFilters) throws CannotInitializeDataAdapterException {
        this(browsingMode);
        this.addQueryFilters(queryFilters);
    }

    public EliteBgsAdapter(FactionBrowsingMode browsingMode) throws CannotInitializeDataAdapterException {
        super(getURL());
        this.eliteBgsDecoder = new EliteBgsDecoder();
        gson = new Gson();
        this.browsingMode = browsingMode;
        this.prefs = (EliteBgsAdapterPreferences) getAdapterInfo().getPreferences();
    }

    static public EdbgsApiHelper getHelper() {
        return InstanceHolder.INSTANCE;
    }

    static private EliteBgsAdapter create() {
        try {
            return new EliteBgsAdapter();
        } catch (CannotInitializeDataAdapterException e) {
            throw new UnsupportedOperationException(e);
        }
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
    public Collection<String> suggestFactionName(String beginsWith) throws DataAdapterException {
        if (beginsWith == null || beginsWith.isBlank()) {
            throw new DataAdapterException("Please provide a faction name");
        }
        var pages = gson.fromJson(
                doHttpGet(craftRequestUri(API_FACTIONS, QueryParameters.beginsWith(beginsWith)), new BasicResponseHandler()),
                FactionsPage.class);
        var factionNames = new ArrayList<String>();
        for (Factions f : pages.docs) {
            factionNames.add(f.name);
        }

        return factionNames;
    }

    @Override
    public Collection<String> suggestSystemName(String beginsWith) throws DataAdapterException {
        if (beginsWith == null || beginsWith.isBlank()) {
            throw new DataAdapterException("Please provide a system name");
        }
        var pages = gson.fromJson(
                doHttpGet(craftRequestUri(API_SYSTEMS, QueryParameters.beginsWith(beginsWith)), new BasicResponseHandler()),
                SystemsPage.class);
        var systemNames = new ArrayList<String>();
        for (Systems s : pages.docs) {
            systemNames.add(s.name);
        }

        return systemNames;
    }

    @Override
    public boolean factionExists(String factionName) throws DataAdapterException {
        if (factionName == null || factionName.isBlank()) {
            throw new DataAdapterException("Please provide a faction name");
        }
        var pages = gson.fromJson(
                doHttpGet(craftRequestUri(API_FACTIONS, QueryParameters.name(factionName)), new BasicResponseHandler()),
                FactionsPage.class);
        return pages.docs.length > 0;
    }

    @Override
    public boolean systemExists(String systemName) throws DataAdapterException {
        if (systemName == null || systemName.isBlank()) {
            throw new DataAdapterException("Please provide a system name");
        }
        var pages = gson.fromJson(
                doHttpGet(craftRequestUri(API_SYSTEMS, QueryParameters.name(systemName)), new BasicResponseHandler()),
                SystemsPage.class);
        return pages.docs.length > 0;
    }

    @Override
    protected URI craftFetchUri(String path, Instant begin, Instant end) throws DataAdapterException {
        return craftRequestUri(API_FACTIONS,
                QueryParameters.name(path),
                QueryParameters.timeMin(begin.minus(Duration.ofDays(prefs.fetchReadBehindDays.get().longValue()))),
                QueryParameters.timeMax(end.plus(Duration.ofDays(prefs.fetchReadAheadDays.get().longValue()))));
    }

    @Override
    public Decoder getDecoder() {
        return eliteBgsDecoder;
    }

    @Override
    public FilterableTreeItem<TimeSeriesBinding> getBindingTree() throws DataAdapterException {
        var root = makeBranch(getSourceName(), TITLE, "");
        switch (browsingMode) {
            default:
            case BROWSE_BY_SYSTEM:
                addNodesGroupedByName(root, this::addSystemsPage);
                break;
            case BROWSE_BY_FACTIONS:
                addNodesGroupedByName(root, this::addFactionsPage);
                break;
            case FACTION_LOOKUP:
                var factionName = getParameter(QueryParameters.PARAM_LOOKUP_FACTION)
                        .orElseThrow(() -> new DataAdapterException("No valid faction name for lookup"));
                getSystemsByFactions(root, factionName.getValue());
                break;
            case SYSTEM_LOOKUP:
                var systemName = getParameter(QueryParameters.PARAM_LOOKUP_SYSTEM)
                        .orElseThrow(() -> new DataAdapterException("No valid system name for lookup"));
                getSystems(root, systemName.getValue());
                break;
        }
        return root;
    }

    @Override
    public boolean isSortingRequired() {
        return true;
    }

    private Optional<NameValuePair> getParameter(String parameterName) {
        return queryFilters.stream()
                .filter(q -> q.getName().equals(parameterName))
                .findFirst();
    }

    @Override
    public void loadParams(Map<String, String> params) throws DataAdapterException {
        super.loadParams(params);

        browsingMode = FactionBrowsingMode.valueOf(params.get("browsingMode"));
        addQueryFilters(params.entrySet()
                .stream()
                .filter(e -> QueryParameters.isParameterNameKnown(e.getKey()))
                .map(e -> QueryParameters.of(e.getKey(), e.getValue()))
                .collect(Collectors.toList()));
    }

    @Override
    public Map<String, String> getParams() {
        var params = super.getParams();
        params.put("browsingMode", browsingMode.name());

        for (var q : queryFilters) {
            params.put(q.getName(), q.getValue());
        }
        return params;
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
        StringBuilder sourceName = new StringBuilder("[" + TITLE + "] ");
        String filterString = queryFilters.stream().map(NameValuePair::toString).collect(Collectors.joining(", "));
        filterString = filterString.isBlank() ? "All" : filterString;
        switch (browsingMode) {
            case BROWSE_BY_SYSTEM:
                sourceName.append("By Systems - ").append(filterString);
                break;
            case BROWSE_BY_FACTIONS:
                sourceName.append("By Factions - ").append(filterString);
                break;
            case FACTION_LOOKUP:
                sourceName.append(getParameter(QueryParameters.PARAM_LOOKUP_FACTION).orElse(new BasicNameValuePair("", "")).getValue());
                break;
            case SYSTEM_LOOKUP:
                sourceName.append(getParameter(QueryParameters.PARAM_LOOKUP_SYSTEM).orElse(new BasicNameValuePair("", "")).getValue());
                break;
        }
        return sourceName.toString();
    }

    public void addQueryFilters(Collection<NameValuePair> filters) {
        queryFilters.addAll(QueryParameters.pruneParameters(filters));
    }

    public void addQueryFilters(NameValuePair... filters) {
        addQueryFilters(Arrays.asList(filters));
    }

    public void clearQueryFilters() {
        queryFilters.clear();
    }

    public NameValuePair[] getFilters() {
        return queryFilters.toArray(NameValuePair[]::new);
    }

    private void getSystems(FilterableTreeItem<TimeSeriesBinding> parent, String... systemNames) throws DataAdapterException {
    for(var systemName: systemNames){
        List<NameValuePair> params = new ArrayList<>(queryFilters);
        params.add(QueryParameters.name(systemName));
        AsyncTaskManager.getInstance().submit(() -> {
                    List<FilterableTreeItem<TimeSeriesBinding>> nodes = new ArrayList<>();
                    var systemsPages = gson.fromJson(
                            doHttpGet(craftRequestUri(API_SYSTEMS, params), new BasicResponseHandler()),
                            SystemsPage.class);
                    for (Systems s : systemsPages.docs) {
                        var branch = makeBranch(s.name, s._id, parent.getValue().getTreeHierarchy());
                        for (var sp : s.factions) {
                            branch.getInternalChildren().add(makeBranch(sp.name, sp.name, branch.getValue().getTreeHierarchy()));
                        }
                        nodes.add(branch);
                    }
                    return nodes;
                },
                event -> parent.getInternalChildren().addAll((List<FilterableTreeItem<TimeSeriesBinding>>) event.getSource().getValue()),
                event -> Dialogs.notifyException("An error occurred while retrieving tree view from source", event.getSource().getException())
        );
    }
    }

    private void getSystemsByFactions(FilterableTreeItem<TimeSeriesBinding> parent, String factionName) throws DataAdapterException {
        List<NameValuePair> factionParams = new ArrayList<>(queryFilters);
        factionParams.add(QueryParameters.name(factionName));
        var pages = gson.fromJson(
                doHttpGet(craftRequestUri(FRONTEND_FACTIONS, factionParams), new BasicResponseHandler()),
                FactionsPage.class);
        for (Factions f : pages.docs) {
            int nbFactions = f.faction_presence.length;
            List<List<NameValuePair>> paramPages = new ArrayList<>();
            var currentList = new ArrayList<NameValuePair>();
            paramPages.add(currentList);
            for (int i = 0; i < nbFactions; i++) {
                if ((i + 1) % 10 == 0) {
                    currentList = new ArrayList<>();
                    paramPages.add(currentList);
                }
                currentList.add(QueryParameters.id(f.faction_presence[i].system_id));
            }
            for (var params : paramPages) {
                params.addAll(queryFilters);
                AsyncTaskManager.getInstance().submit(() -> {
                            List<FilterableTreeItem<TimeSeriesBinding>> nodes = new ArrayList<>();
                            var systemsPages = gson.fromJson(
                                    doHttpGet(craftRequestUri(FRONTEND_SYSTEMS, params), new BasicResponseHandler()),
                                    SystemsPage.class);
                            for (Systems s : systemsPages.docs) {
                                var branch = makeBranch(s.name, s._id, parent.getValue().getTreeHierarchy());
                                for (var sp : s.factions) {
                                    branch.getInternalChildren().add(makeBranch(sp.name, sp.name, branch.getValue().getTreeHierarchy()));
                                }
                                nodes.add(branch);
                            }
                            return nodes;
                        },
                        event -> parent.getInternalChildren().addAll((List<FilterableTreeItem<TimeSeriesBinding>>) event.getSource().getValue()),
                        event -> Dialogs.notifyException("An error occurred while retrieving tree view from source", event.getSource().getException())
                );
            }
        }
    }

    private void addNodesGroupedByName(FilterableTreeItem<TimeSeriesBinding> parent, AddPageDelegate addPageDelegate) {
        String alphabet = "123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        for (int i = 0; i < alphabet.length(); i++) {
            String letter = String.valueOf(alphabet.charAt(i));
            var newBranch = makeBranch(letter, letter, parent.getValue().getTreeHierarchy());
            // add a dummy node so that the branch can be expanded
            newBranch.getInternalChildren().add(new FilterableTreeItem<>(null));
            newBranch.expandedProperty().addListener(new ExpandListener(newBranch, letter, addPageDelegate));
            parent.getInternalChildren().add(newBranch);
        }
    }

    private void addAllPages(FilterableTreeItem<TimeSeriesBinding> tree, String beginWith, AddPageDelegate
            pageDelegate) throws DataAdapterException {
        AtomicInteger nbPages = new AtomicInteger(0);
        AtomicInteger nbHits = new AtomicInteger(0);
        try (var p = Profiler.start(() -> "Retrieving " + nbHits.get() + " elements starting with '"
                + beginWith + "' (" + nbPages.get() + " pages)", logger::trace)) {
            AbstractPage<?> res = pageDelegate.addSinglePage(tree, beginWith, 1, true);
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

    private SystemsPage addSystemsPage(FilterableTreeItem<TimeSeriesBinding> tree, String beginWith,
                                       int page, boolean waitForResult) throws DataAdapterException {
        AtomicReference<SystemsPage> returnValue = new AtomicReference<>(null);
        var res = AsyncTaskManager.getInstance().submit(() -> {
                    var pages = gson.fromJson(getRawPageData(API_SYSTEMS, beginWith, page), SystemsPage.class);
                    returnValue.set(pages);
                    return pages;
                },
                event -> {
                    SystemsPage t = (SystemsPage) event.getSource().getValue();
                    for (Systems s : t.docs) {
                        var branch = makeBranch(s.name, s._id, tree.getValue().getTreeHierarchy());
                        for (var f : s.factions) {
                            branch.getInternalChildren().add(makeBranch(f.name, f.name, branch.getValue().getTreeHierarchy()));
                        }
                        tree.getInternalChildren().add(branch);
                    }
                },
                event -> {
                    Dialogs.notifyException("An error occurred while retrieving tree view from source",
                            event.getSource().getException());
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

    private FactionsPage addFactionsPage(FilterableTreeItem<TimeSeriesBinding> tree, String beginWith,
                                         int page, boolean waitForResult) throws DataAdapterException {
        AtomicReference<FactionsPage> returnValue = new AtomicReference<>(null);
        var res = AsyncTaskManager.getInstance().submit(() -> {
                    var pages = gson.fromJson(getRawPageData(API_FACTIONS, beginWith, page), FactionsPage.class);
                    returnValue.set(pages);
                    return pages;
                },
                event -> {
                    FactionsPage t = (FactionsPage) event.getSource().getValue();
                    Map<String, Factions> m = Arrays.stream(t.docs).collect(Collectors.toMap(o -> o._id, (o -> o)));
                    for (Factions f : m.values()) {
                        var branch = makeBranch(f.name, f._id, tree.getValue().getTreeHierarchy());
                        for (var s : f.faction_presence) {
                            branch.getInternalChildren().add(makeBranch(s.system_name, f.name, branch.getValue().getTreeHierarchy()));
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
        var params = new ArrayList<>(queryFilters);
        params.add(QueryParameters.page(page));
        params.add(QueryParameters.beginsWith(beginWith));
        String entityString = doHttpGet(craftRequestUri(uriPath, params), new BasicResponseHandler());
        logger.trace(entityString);
        return entityString;
    }

    @FunctionalInterface
    private interface AddPageDelegate {
        AbstractPage<?> addSinglePage(FilterableTreeItem<TimeSeriesBinding> tree, String beginWith, int page, boolean waitForResult) throws DataAdapterException;
    }

    static private class InstanceHolder {
        static private final EliteBgsAdapter INSTANCE = EliteBgsAdapter.create();
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
