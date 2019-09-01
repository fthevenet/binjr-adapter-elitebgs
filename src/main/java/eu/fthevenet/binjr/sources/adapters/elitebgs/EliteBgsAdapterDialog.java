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

import eu.binjr.core.data.adapters.DataAdapter;
import eu.binjr.core.data.exceptions.CannotInitializeDataAdapterException;
import eu.binjr.core.data.exceptions.DataAdapterException;
import eu.binjr.core.dialogs.Dialogs;
import eu.binjr.core.preferences.AppEnvironment;
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
import javafx.scene.layout.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * A dialog box that returns a {@link EliteBgsAdapter} built according to user inputs.
 *
 * @author Frederic Thevenet
 */
public class EliteBgsAdapterDialog extends Dialog<DataAdapter> {
    private static final Logger logger = LogManager.getLogger(EliteBgsAdapterDialog.class);
    private static final String BINJR_SOURCES = "binjr/sources";
    private DataAdapter result = null;
    private List<ReadOnlyProperty<QueryParameters>> filters = new ArrayList<>();
    private Property<FactionBrowsingMode> browsingMode = new SimpleObjectProperty<>();


    /**
     * Initializes a new instance of the {@link EliteBgsAdapterDialog} class.
     *
     * @param owner the owner window for the dialog
     */
    public EliteBgsAdapterDialog(Node owner) {
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
        browsingModeChoiceBox.getSelectionModel().select(0);
        browsingMode.bind(browsingModeChoiceBox.valueProperty());
       // VBox.setVgrow(browsingModeChoiceBox, Priority.SOMETIMES);
        browsingModeChoiceBox.setMaxWidth(Double.MAX_VALUE);

        var isLookupBinding = (Bindings.createBooleanBinding(() -> browsingModeChoiceBox.getSelectionModel().getSelectedItem() == FactionBrowsingMode.LOOKUP,
                browsingModeChoiceBox.getSelectionModel().selectedItemProperty()));
        var isSystemBinding =  Bindings.createBooleanBinding(() -> browsingModeChoiceBox.getSelectionModel().getSelectedItem() == FactionBrowsingMode.BROWSE_BY_SYSTEM,
                browsingModeChoiceBox.getSelectionModel().selectedItemProperty());

        var factionName = new TextField();
        factionName.setPromptText("Enter a faction's name");
        HBox.setHgrow(factionName, Priority.SOMETIMES);

        factionName.setMaxWidth(Double.MAX_VALUE);
        factionName.visibleProperty().bind(isLookupBinding);
        factionName.managedProperty().bind(factionName.visibleProperty());

        var stateChoiceBox = initChoiceBox("State: ", StateTypes.values());
        stateChoiceBox.visibleProperty().bind(isLookupBinding.not());
        var economyChoiceBox = initChoiceBox("Economy: ", EconomyTypes.values());
        economyChoiceBox.visibleProperty().bind(isSystemBinding);
        var allegianceChoiceBox = initChoiceBox("Allegiance: ", Allegiances.values());
        allegianceChoiceBox.visibleProperty().bind(isLookupBinding.not());
        var governmentChoiceBox = initChoiceBox("Government: ", GovernmentTypes.values());
        governmentChoiceBox.visibleProperty().bind(isLookupBinding.not());
        var securityChoiceBox = initChoiceBox("Security: ", SecurityLevels.values());
        securityChoiceBox.visibleProperty().bind(isSystemBinding);


        vBox.getChildren().addAll(
                browsingModeChoiceBox,
                factionName,
                allegianceChoiceBox,
                governmentChoiceBox,
                securityChoiceBox,
                economyChoiceBox,
                stateChoiceBox);
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
        // Workaround JDK-8179073 (ref: https://bugs.openjdk.java.net/browse/JDK-8179073)
        this.setResizable(AppEnvironment.getInstance().isResizableDialogs());
    }

    private HBox initChoiceBox(String title, QueryParameters[] values) {
        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER_LEFT);
        var label = new Label(title);
        HBox.setMargin(label, new Insets(0,0,0,2));
        label.setPrefWidth(120);
        label.setMinWidth(120);
        label.setMaxWidth(120);
        var cb = new ChoiceBox<>(FXCollections.observableArrayList(values));
        filters.add(cb.getSelectionModel().selectedItemProperty());
        hBox.getChildren().addAll(label, cb);
        cb.getSelectionModel().select(0);
        HBox.setHgrow(cb, Priority.ALWAYS);
        hBox.setMaxWidth(Double.MAX_VALUE);
        cb.setMaxWidth(Double.MAX_VALUE);
        cb.setPrefWidth(-1);
        hBox.managedProperty().bind(hBox.visibleProperty());
        return hBox;
    }

    /**
     * Returns an instance of {@link EliteBgsAdapter}
     *
     * @return an instance of {@link EliteBgsAdapter}
     * @throws DataAdapterException if the provided parameters are invalid
     */
    private DataAdapter getDataAdapter() throws DataAdapterException {
        return new EliteBgsAdapter(browsingMode.getValue(), filters.stream().map(ObservableValue::getValue).collect(Collectors.toList()));
    }

}
