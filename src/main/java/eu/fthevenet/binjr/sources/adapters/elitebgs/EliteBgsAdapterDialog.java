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

import eu.binjr.common.preferences.MostRecentlyUsedList;
import eu.binjr.core.data.adapters.DataAdapter;
import eu.binjr.core.data.adapters.DataAdapterFactory;
import eu.binjr.core.data.exceptions.CannotInitializeDataAdapterException;
import eu.binjr.core.data.exceptions.DataAdapterException;
import eu.binjr.core.data.exceptions.NoAdapterFoundException;
import eu.binjr.core.dialogs.Dialogs;
import eu.binjr.core.preferences.UserHistory;
import eu.fthevenet.binjr.sources.adapters.elitebgs.api.*;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.Property;
import javafx.beans.property.ReadOnlyProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import org.apache.hc.core5.http.NameValuePair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * A dialog box that returns a {@link EliteBgsAdapter} built according to user inputs.
 *
 * @author Frederic Thevenet
 */
public class EliteBgsAdapterDialog extends Dialog<Collection<DataAdapter>> {
    private static final Logger logger = LogManager.getLogger(EliteBgsAdapterDialog.class);
    private static final String BINJR_SOURCES = "binjr/sources";
    private final ComboBox<String> factionNameField;
    private final MostRecentlyUsedList<String> mostRecentFactions =
            UserHistory.getInstance().stringMostRecentlyUsedList("mostRecentFactions", 20);
    private final ComboBox<String> systemNameField;
    private final MostRecentlyUsedList<String> mostRecentSystems =
            UserHistory.getInstance().stringMostRecentlyUsedList("mostRecentSystems", 20);
    private Collection<DataAdapter> result = null;
    private List<ReadOnlyProperty<QueryParameters>> filters = new ArrayList<>();
    private Property<FactionBrowsingMode> browsingMode = new SimpleObjectProperty<>();
    private ColorPicker factionColor = new ColorPicker();
    private final EliteBgsAdapterPreferences prefs;

    /**
     * Initializes a new instance of the {@link EliteBgsAdapterDialog} class.
     *
     * @param owner the owner window for the dialog
     */
    public EliteBgsAdapterDialog(Node owner) {
        try {
            this.prefs = (EliteBgsAdapterPreferences) DataAdapterFactory.getInstance().getAdapterPreferences(EliteBgsAdapter.class.getName());
        } catch (NoAdapterFoundException e) {
            logger.error("Failed to recover preferences: " + e.getMessage());
            throw new RuntimeException(e);
        }
        if (owner != null) {
            this.initOwner(Dialogs.getStage(owner));
        }
        this.setTitle("Elite Dangerous BGS");
        VBox vBox = new VBox();
        vBox.setPrefWidth(350);
        vBox.setFillWidth(true);
        vBox.setSpacing(10);
        vBox.setAlignment(Pos.TOP_CENTER);
        var browsingModeChoiceBox = new ChoiceBox<>(FXCollections.observableArrayList(FactionBrowsingMode.values()));
        browsingModeChoiceBox.valueProperty().bindBidirectional(
               prefs.lastSelectedBrowsingMode.property());
        browsingMode.bind(browsingModeChoiceBox.valueProperty());
        browsingModeChoiceBox.setMaxWidth(Double.MAX_VALUE);
        var isLookupSystemBinding = (Bindings.createBooleanBinding(
                () -> browsingModeChoiceBox.getSelectionModel().getSelectedItem() == FactionBrowsingMode.SYSTEM_LOOKUP,
                browsingModeChoiceBox.getSelectionModel().selectedItemProperty()));
        var isLookupFactionBinding = (Bindings.createBooleanBinding(
                () -> browsingModeChoiceBox.getSelectionModel().getSelectedItem() == FactionBrowsingMode.FACTION_LOOKUP,
                browsingModeChoiceBox.getSelectionModel().selectedItemProperty()));
        var isFactionBinding = (Bindings.createBooleanBinding(
                () -> browsingModeChoiceBox.getSelectionModel().getSelectedItem() == FactionBrowsingMode.BROWSE_BY_FACTIONS,
                browsingModeChoiceBox.getSelectionModel().selectedItemProperty()));
        var isSystemBinding = Bindings.createBooleanBinding(
                () -> browsingModeChoiceBox.getSelectionModel().getSelectedItem() == FactionBrowsingMode.BROWSE_BY_SYSTEM,
                browsingModeChoiceBox.getSelectionModel().selectedItemProperty());
        var stateChoiceBox = initChoiceBox("State: ", StateTypes.values(), isFactionBinding.or(isSystemBinding));
        var economyChoiceBox = initChoiceBox("Economy: ", EconomyTypes.values(), isSystemBinding);
        var allegianceChoiceBox = initChoiceBox("Allegiance: ", Allegiances.values(), isFactionBinding.or(isSystemBinding));
        var governmentChoiceBox = initChoiceBox("Government: ", SystemGovernmentTypes.values(), isSystemBinding);
        var factionGovernmentChoiceBox = initChoiceBox("Government: ", FactionGovernmentTypes.values(), isFactionBinding);
        var securityChoiceBox = initChoiceBox("Security: ", SecurityLevels.values(), isSystemBinding);
        vBox.getChildren().addAll(
                browsingModeChoiceBox,
                allegianceChoiceBox,
                governmentChoiceBox,
                factionGovernmentChoiceBox,
                securityChoiceBox,
                economyChoiceBox,
                stateChoiceBox);
        this.factionNameField = makeLookupField(vBox, isLookupFactionBinding, "Faction Name:", mostRecentFactions,
                param -> {
                    if (param.getUserText() != null && !param.getUserText().isBlank()) {
                        try {
                            return EliteBgsAdapter.getHelper().suggestFactionName(param.getUserText());
                        } catch (DataAdapterException e) {
                            Dialogs.notifyException("Error retrieving faction name suggestions", e, vBox);
                        }
                    }
                    return Collections.emptyList();
                });

        this.systemNameField = makeLookupField(vBox, isLookupSystemBinding, "System Name:", mostRecentSystems,
                param -> {
                    if (param.getUserText() != null && !param.getUserText().isBlank()) {
                        try {
                            return EliteBgsAdapter.getHelper().suggestSystemName(param.getUserText());
                        } catch (DataAdapterException e) {
                            Dialogs.notifyException("Error retrieving system name suggestions", e, vBox);
                        }
                    }
                    return Collections.emptyList();
                });
        factionColor.getStyleClass().clear();
        factionColor.getStyleClass().addAll("choice-box", "color-picker");
        factionColor.valueProperty().bindBidirectional(prefs.lastFactionColor.property());
        var colorSelectionPane = layoutControl("Faction Color", factionColor, isLookupFactionBinding);
        vBox.getChildren().add(colorSelectionPane);


        VBox.setVgrow(vBox, Priority.ALWAYS);
        DialogPane dialogPane = new DialogPane();
        dialogPane.setHeaderText("Minor Factions Influence");
        dialogPane.getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        dialogPane.setGraphic(new Region());
        dialogPane.getGraphic().getStyleClass().addAll("elite-icon", "dialog-icon");
        dialogPane.setContent(vBox);
        this.setDialogPane(dialogPane);
        Button okButton = (Button) dialogPane.lookupButton(ButtonType.OK);
        Platform.runLater(browsingModeChoiceBox::requestFocus);
        okButton.addEventFilter(ActionEvent.ACTION, ae -> {
            try {
                result = getDataAdapter();
            } catch (NoSuchFactionException e) {
                Dialogs.notifyError(e.getMessage(), e, Pos.CENTER, browsingModeChoiceBox);
                ae.consume();
            } catch (CannotInitializeDataAdapterException e) {
                Dialogs.notifyError("Error initializing adapter to source", e, Pos.CENTER, browsingModeChoiceBox);
                ae.consume();
            } catch (DataAdapterException e) {
                Dialogs.notifyError("Error with the adapter to source", e, Pos.CENTER, browsingModeChoiceBox);
                ae.consume();
            } catch (Throwable e) {
                Dialogs.notifyError("Unexpected error while retrieving data adapter", e, Pos.CENTER, browsingModeChoiceBox);
                ae.consume();
            }
        });
        this.setResultConverter(dialogButton -> {
                    ButtonBar.ButtonData data = dialogButton == null ? null : dialogButton.getButtonData();
                    if (data == ButtonBar.ButtonData.OK_DONE) {
                        return result;
                    }
                    return null;
                }
        );
        browsingModeChoiceBox.valueProperty().addListener(observable -> vBox.autosize());
        vBox.heightProperty().addListener((observable, oldValue, newValue) -> {
            var offset = newValue.doubleValue() - oldValue.doubleValue();
            this.setHeight(this.getHeight() + offset);
        });
    }

    private ComboBox<String> makeLookupField(VBox parent,
                                             BooleanBinding isLookupBinding,
                                             String title,
                                             MostRecentlyUsedList<String> mru,
                                             Callback<AutoCompletionBinding.ISuggestionRequest,
                                                     Collection<String>> suggestCallback) {
        ComboBox<String> comboBox = new ComboBox<>();
        comboBox.setEditable(true);
        comboBox.setItems(FXCollections.observableArrayList(mru.getAll()));
        HBox.setHgrow(comboBox, Priority.SOMETIMES);
        comboBox.setMaxWidth(Double.MAX_VALUE);
        var hBox = layoutControl(title, comboBox, isLookupBinding);
        TextFields.bindAutoCompletion(comboBox.getEditor(), suggestCallback);
        parent.getChildren().add(hBox);
        return comboBox;
    }

    private HBox initChoiceBox(String title, QueryParameters[] values, BooleanBinding binding) {
        var cb = new ChoiceBox<>(FXCollections.observableArrayList(values));
        filters.add(cb.getSelectionModel().selectedItemProperty());
        cb.getSelectionModel().select(0);
        return layoutControl(title, cb, binding);

    }

    private HBox layoutControl(String title, Control control, BooleanBinding binding) {
        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER_LEFT);
        var label = new Label(title);
        HBox.setMargin(label, new Insets(0, 0, 0, 2));
        label.setPrefWidth(100);
        label.setMinWidth(100);
        label.setMaxWidth(100);
        hBox.getChildren().addAll(label, control);
        HBox.setHgrow(control, Priority.ALWAYS);
        hBox.setMaxWidth(Double.MAX_VALUE);
        control.setMaxWidth(Double.MAX_VALUE);
        control.setPrefWidth(-1);
        hBox.managedProperty().bind(hBox.visibleProperty());
        hBox.visibleProperty().bind(binding);
        return hBox;
    }

    /**
     * Returns an instance of {@link EliteBgsAdapter}
     *
     * @return an instance of {@link EliteBgsAdapter}
     * @throws DataAdapterException if the provided parameters are invalid
     */
    private Collection<DataAdapter> getDataAdapter() throws DataAdapterException {
        List<NameValuePair> list = filters.stream().map(ObservableValue::getValue).collect(Collectors.toList());
        EliteBgsAdapterParameters parameters = new EliteBgsAdapterParameters();
        parameters.setBrowsingMode(browsingMode.getValue());
        parameters.setSelectedColor(factionColor.getValue());
        if (browsingMode.getValue() == FactionBrowsingMode.FACTION_LOOKUP) {
            var factionName = factionNameField.getSelectionModel().getSelectedItem();
            if (!EliteBgsAdapter.getHelper().factionExists(factionName)) {
                throw new NoSuchFactionException(factionName);
            }
            parameters.setLookupValue(factionName);
            mostRecentFactions.push(factionName);
        }
        if (browsingMode.getValue() == FactionBrowsingMode.SYSTEM_LOOKUP) {
            var systemName = systemNameField.getSelectionModel().getSelectedItem();
            if (!EliteBgsAdapter.getHelper().systemExists(systemName)) {
                throw new NoSuchSystemException(systemName);
            }
            parameters.setLookupValue(systemName);
            mostRecentSystems.push(systemName);
        }
        return List.of(new EliteBgsAdapter(parameters, list));
    }
}
