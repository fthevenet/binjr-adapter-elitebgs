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
import eu.binjr.common.javafx.controls.TimeRange;
import eu.binjr.common.logging.Profiler;
import eu.binjr.core.data.adapters.HttpDataAdapter;
import eu.binjr.core.data.adapters.NameValuePair;
import eu.binjr.core.data.adapters.SourceBinding;
import eu.binjr.core.data.adapters.TimeSeriesBinding;
import eu.binjr.core.data.async.AsyncTaskManager;
import eu.binjr.core.data.codec.Decoder;
import eu.binjr.core.data.exceptions.CannotInitializeDataAdapterException;
import eu.binjr.core.data.exceptions.DataAdapterException;
import eu.binjr.core.data.workspace.ChartType;
import eu.binjr.core.data.workspace.TimeSeriesInfo;
import eu.binjr.core.data.workspace.UnitPrefixes;
import eu.binjr.core.dialogs.Dialogs;
import eu.fthevenet.binjr.sources.adapters.elitebgs.api.QueryParameters;
import eu.fthevenet.binjr.sources.adapters.elitebgs.api.v5.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.paint.Color;
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
import java.time.ZonedDateTime;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

public class EliteBgsAdapter extends HttpDataAdapter<Double> implements EdbgsApiHelper {
    private static final Logger logger = LogManager.getLogger(EliteBgsAdapter.class);
    private static final String SITE = "https://elitebgs.app";
    private static final String API_VERSION = "v5";
    private static final String API_FACTIONS = "/api/ebgs/" + API_VERSION + "/factions";
    private static final String API_SYSTEMS = "/api/ebgs/" + API_VERSION + "/systems";
    private static final String TITLE = "ED BGS";
    private final EliteBgsDecoder eliteBgsDecoder;
    private final Gson gson;
    private final List<NameValuePair> queryFilters = new ArrayList<>();
    private final EliteBgsAdapterPreferences prefs;
    private final EliteBgsAdapterParameters parameters;

    public EliteBgsAdapter() throws CannotInitializeDataAdapterException {
        this(new EliteBgsAdapterParameters());
    }

    public EliteBgsAdapter(EliteBgsAdapterParameters parameters, Collection<NameValuePair> queryFilters) throws CannotInitializeDataAdapterException {
        this(parameters);
        this.addQueryFilters(queryFilters);
    }

    public EliteBgsAdapter(EliteBgsAdapterParameters browsingMode) throws CannotInitializeDataAdapterException {
        super(getURL());
        this.eliteBgsDecoder = new EliteBgsDecoder();
        gson = new Gson();
        this.parameters = browsingMode;
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
                doHttpGetJson(craftRequestUri(API_FACTIONS, QueryParameters.beginsWith(beginsWith))),
                EBGSFactionsPageV5.class);
        var factionNames = new ArrayList<String>();
        for (EBGSFactionsV5 f : pages.getDocs()) {
            factionNames.add(f.getName());
        }

        return factionNames;
    }

    @Override
    public Collection<String> suggestSystemName(String beginsWith) throws DataAdapterException {
        if (beginsWith == null || beginsWith.isBlank()) {
            throw new DataAdapterException("Please provide a system name");
        }
        var pages = gson.fromJson(
                doHttpGetJson(craftRequestUri(API_SYSTEMS, QueryParameters.beginsWith(beginsWith))),
                EBGSSystemsPageV5.class);
        var systemNames = new ArrayList<String>();
        for (EBGSSystemsV5 s : pages.getDocs()) {
            systemNames.add(s.getName());
        }

        return systemNames;
    }

    @Override
    public boolean factionExists(String factionName) throws DataAdapterException {
        if (factionName == null || factionName.isBlank()) {
            throw new DataAdapterException("Please provide a faction name");
        }
        var pages = gson.fromJson(
                doHttpGetJson(craftRequestUri(API_FACTIONS, QueryParameters.name(factionName))),
                EBGSFactionsPageV5.class);
        return pages.getDocs().size() > 0;
    }

    @Override
    public boolean systemExists(String systemName) throws DataAdapterException {
        if (systemName == null || systemName.isBlank()) {
            throw new DataAdapterException("Please provide a system name");
        }
        var pages = gson.fromJson(
                doHttpGetJson(craftRequestUri(API_SYSTEMS, QueryParameters.name(systemName))),
                EBGSSystemsPageV5.class);
        return pages.getDocs().size() > 0;
    }

    @Override
    protected URI craftFetchUri(String path, Instant begin, Instant end) throws DataAdapterException {
        return craftRequestUri(API_FACTIONS,
                QueryParameters.name(path),
                QueryParameters.timeMin(begin.minus(Duration.ofDays(prefs.fetchReadBehindDays.get().longValue()))),
                QueryParameters.timeMax(end.plus(Duration.ofDays(prefs.fetchReadAheadDays.get().longValue()))));
    }

    @Override
    public Decoder<Double> getDecoder() {
        return eliteBgsDecoder;
    }

    @Override
    public FilterableTreeItem<SourceBinding> getBindingTree() throws DataAdapterException {
        var root = makeBranch(getSourceName(), TITLE, "");
        switch (parameters.getBrowsingMode()) {
            case BROWSE_BY_SYSTEM -> addNodesGroupedByName(root, this::addSystemsPage);
            case BROWSE_BY_FACTIONS -> addNodesGroupedByName(root, this::addFactionsPage);
            case FACTION_LOOKUP -> getSystemsByFactions(root, parameters.getLookupValue());
            case SYSTEM_LOOKUP -> getSystems(root, parameters.getLookupValue());
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
        parameters.setBrowsingMode(FactionBrowsingMode.valueOf(params.get("browsingMode")));
        parameters.setLookupValue(params.get("lookupValue"));
        var colorString = params.get("selectedColor");
        if (colorString != null && !colorString.isBlank()) {
            parameters.setSelectedColor(Color.valueOf(colorString));
        }
        addQueryFilters(params.entrySet()
                .stream()
                .filter(e -> QueryParameters.isParameterNameKnown(e.getKey()))
                .map(e -> QueryParameters.of(e.getKey(), e.getValue()))
                .collect(Collectors.toList()));
    }

    @Override
    public Map<String, String> getParams() {
        var params = super.getParams();
        params.put("browsingMode", parameters.getBrowsingMode().name());
        params.put("lookupValue", parameters.getLookupValue());
        if (parameters.getSelectedColor() != null) {
            params.put("selectedColor", parameters.getSelectedColor().toString());
        }
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
        switch (parameters.getBrowsingMode()) {
            case BROWSE_BY_SYSTEM -> sourceName.append("By Systems - ").append(filterString);
            case BROWSE_BY_FACTIONS -> sourceName.append("By Factions - ").append(filterString);
            case FACTION_LOOKUP, SYSTEM_LOOKUP -> sourceName.append(parameters.getLookupValue());
        }
        return sourceName.toString();
    }

    @Override
    public TimeRange getInitialTimeRange(String path, List<TimeSeriesInfo<Double>> seriesInfo) throws DataAdapterException {
        var end = ZonedDateTime.now(getTimeZoneId());
        return TimeRange.of(end.minusHours(prefs.defaultTimeRangeHours.get().intValue()), end);
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

    private void getSystems(FilterableTreeItem<SourceBinding> parent, String... systemNames) throws DataAdapterException {
        for (var systemName : systemNames) {
            List<NameValuePair> params = new ArrayList<>(queryFilters);
            params.add(QueryParameters.name(systemName));
            AsyncTaskManager.getInstance().submit(() -> {
                        List<FilterableTreeItem<SourceBinding>> nodes = new ArrayList<>();
                        var systemsPages = gson.fromJson(
                                doHttpGetJson(craftRequestUri(API_SYSTEMS, params)),
                                EBGSSystemsPageV5.class);
                        for (EBGSSystemsV5 s : systemsPages.getDocs()) {
                            var branch = makeBranch(s.getName(), s.getId(), parent.getValue().getTreeHierarchy());
                            for (var sp : s.getFactions()) {
                                branch.getInternalChildren().add(makeBranch(sp.getName(), sp.getName(), branch.getValue().getTreeHierarchy()));
                            }
                            nodes.add(branch);
                        }
                        return nodes;
                    },
                    event -> parent.getInternalChildren().addAll((List<FilterableTreeItem<SourceBinding>>) event.getSource().getValue()),
                    event -> Dialogs.notifyException("An error occurred while retrieving tree view from source", event.getSource().getException())
            );
        }
    }

    private void getSystemsByFactions(FilterableTreeItem<SourceBinding> parent, String factionName) throws DataAdapterException {
        List<NameValuePair> factionParams = new ArrayList<>(queryFilters);
        factionParams.add(QueryParameters.name(factionName));
        // factionParams.add(QueryParameters.minimal(true));
        var pages = gson.fromJson(
                doHttpGetJson(craftRequestUri(API_FACTIONS, factionParams)),
                EBGSFactionsPageV5.class);
        for (EBGSFactionsV5 f : pages.getDocs()) {
            int nbFactions = f.getFactionPresence().size();
            List<List<NameValuePair>> paramPages = new ArrayList<>();
            var currentList = new ArrayList<NameValuePair>();
            paramPages.add(currentList);
            for (int i = 0; i < nbFactions; i++) {
                if ((i + 1) % 10 == 0) {
                    currentList = new ArrayList<>();
                    paramPages.add(currentList);
                }
                currentList.add(QueryParameters.id(f.getFactionPresence().get(i).getSystemId()));
            }
            for (var params : paramPages) {
                params.addAll(queryFilters);
                AsyncTaskManager.getInstance().submit(() -> {
                            List<FilterableTreeItem<SourceBinding>> nodes = new ArrayList<>();
                            var systemsPages = gson.fromJson(
                                    doHttpGetJson(craftRequestUri(API_SYSTEMS, params)),
                                    EBGSSystemsPageV5.class);
                            for (EBGSSystemsV5 s : systemsPages.getDocs()) {
                                var branch = makeBranch(s.getName(), s.getId(), parent.getValue().getTreeHierarchy());
                                for (var sp : s.getFactions()) {
                                    Color color = null;
                                    if (sp.getName().equalsIgnoreCase(parameters.getLookupValue())) {
                                        color = parameters.getSelectedColor();
                                    }
                                    branch.getInternalChildren().add(makeBranch(sp.getName(), sp.getName(), branch.getValue().getTreeHierarchy(), color));
                                }
                                nodes.add(branch);
                            }
                            return nodes;
                        },
                        event -> parent.getInternalChildren().addAll((List<FilterableTreeItem<SourceBinding>>) event.getSource().getValue()),
                        event -> Dialogs.notifyException("An error occurred while retrieving tree view from source", event.getSource().getException())
                );
            }
        }
    }

    private void addNodesGroupedByName(FilterableTreeItem<SourceBinding> parent, AddPageDelegate addPageDelegate) {
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

    private void addAllPages(FilterableTreeItem<SourceBinding> tree, String beginWith, AddPageDelegate
            pageDelegate) throws DataAdapterException {
        AtomicInteger nbPages = new AtomicInteger(0);
        AtomicInteger nbHits = new AtomicInteger(0);
        try (var p = Profiler.start(() -> "Retrieving " + nbHits.get() + " elements starting with '"
                + beginWith + "' (" + nbPages.get() + " pages)", logger::trace)) {
            EBGSPageV5<?> res = pageDelegate.addSinglePage(tree, beginWith, 1, true);
            if (res != null) {
                logger.info("pages=" + res.getPages());
                nbPages.addAndGet(res.getPages());
                nbHits.addAndGet(res.getTotal());
                for (int page = 2; page <= nbPages.get(); page++) {
                    pageDelegate.addSinglePage(tree, beginWith, page, false);
                }
            } else {
                logger.warn("No results from synchronous call to addSystemPage");
            }
        }
    }

    private EBGSSystemsPageV5 addSystemsPage(FilterableTreeItem<SourceBinding> tree, String beginWith,
                                             int page, boolean waitForResult) throws DataAdapterException {
        AtomicReference<EBGSSystemsPageV5> returnValue = new AtomicReference<>(null);
        var res = AsyncTaskManager.getInstance().submit(() -> {
                    var pages = gson.fromJson(getRawPageData(API_SYSTEMS, beginWith, page), EBGSSystemsPageV5.class);
                    returnValue.set(pages);
                    return pages;
                },
                event -> {
                    EBGSSystemsPageV5 t = (EBGSSystemsPageV5) event.getSource().getValue();
                    for (EBGSSystemsV5 s : t.getDocs()) {
                        var branch = makeBranch(s.getName(), s.getId(), tree.getValue().getTreeHierarchy());
                        for (var f : s.getFactions()) {
                            branch.getInternalChildren().add(makeBranch(f.getName(), f.getName(), branch.getValue().getTreeHierarchy()));
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

    private EBGSFactionsPageV5 addFactionsPage(FilterableTreeItem<SourceBinding> tree, String beginWith,
                                               int page, boolean waitForResult) throws DataAdapterException {
        AtomicReference<EBGSFactionsPageV5> returnValue = new AtomicReference<>(null);
        var res = AsyncTaskManager.getInstance().submit(() -> {
                    var pages = gson.fromJson(getRawPageData(API_FACTIONS, beginWith, page), EBGSFactionsPageV5.class);
                    returnValue.set(pages);

                    return pages;
                },
                event -> {
                    EBGSFactionsPageV5 t = (EBGSFactionsPageV5) event.getSource().getValue();
                    Map<String, EBGSFactionsV5> m = t.getDocs().stream().collect(Collectors.toMap(EBGSFactionsV5::getId, (o -> o)));
                    for (EBGSFactionsV5 f : m.values()) {
                        var branch = makeBranch(f.getName(), f.getId(), tree.getValue().getTreeHierarchy());
                        for (var s : f.getFactionPresence()) {
                            branch.getInternalChildren().add(makeBranch(s.getSystemName(), f.getName(), branch.getValue().getTreeHierarchy()));
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

    private FilterableTreeItem<SourceBinding> makeBranch(String name, String id, String parentHierarchy) {
        return makeBranch(name, id, parentHierarchy, null);
    }

    private FilterableTreeItem<SourceBinding> makeBranch(String name, String id, String parentHierarchy, Color color) {
        return new FilterableTreeItem<>(new TimeSeriesBinding(
                name,
                id,
                color,
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
        String entityString = doHttpGetJson(craftRequestUri(uriPath, params));
        logger.trace(entityString);
        return entityString;
    }

    @FunctionalInterface
    private interface AddPageDelegate {
        EBGSPageV5<?> addSinglePage(FilterableTreeItem<SourceBinding> tree, String beginWith, int page, boolean waitForResult)
                throws DataAdapterException;
    }

    static private class InstanceHolder {
        static private final EliteBgsAdapter INSTANCE = EliteBgsAdapter.create();
    }

    private class ExpandListener implements ChangeListener<Boolean> {
        private final FilterableTreeItem<SourceBinding> newBranch;
        private final String beginWith;
        private final AddPageDelegate addPageDelegate;

        public ExpandListener(FilterableTreeItem<SourceBinding> newBranch,
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
