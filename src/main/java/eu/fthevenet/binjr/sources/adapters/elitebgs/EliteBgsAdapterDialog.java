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
import javafx.beans.property.ReadOnlyProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

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


        var browsingModeChoiceBox = new ChoiceBox<>(FXCollections.observableArrayList(FactionBrowsingMode.values()));
        // filters.add(cb.getSelectionModel().selectedItemProperty());
        //  hBox.getChildren().addAll(label, cb);
        browsingModeChoiceBox.getSelectionModel().select(0);
        VBox.setVgrow(browsingModeChoiceBox, Priority.ALWAYS);
        browsingModeChoiceBox.setMaxWidth(Double.MAX_VALUE);
        var stateChoiceBox = initChoiceBox("State: ", StateTypes.values());
        var economyChoiceBox = initChoiceBox("Economy: ", EconomyTypes.values());
        var allegianceChoiceBox = initChoiceBox("Allegiance: ", Allegiances.values());
        var governmentChoiceBox = initChoiceBox("Government: ", GovernmentTypes.values());
        var securityChoiceBox = initChoiceBox("Security: ", SecurityLevels.values());


        VBox vBox = new VBox();
        vBox.setFillWidth(true);
        vBox.setSpacing(10);
        vBox.setAlignment(Pos.CENTER);
        vBox.getChildren().addAll(
                browsingModeChoiceBox,
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
        label.setPrefWidth(120);
        label.setMinWidth(120);
        label.setMaxWidth(120);
        var cb = new ChoiceBox<>(FXCollections.observableArrayList(values));
        filters.add(cb.getSelectionModel().selectedItemProperty());
        hBox.getChildren().addAll(label, cb);
        cb.getSelectionModel().select(0);
        VBox.setVgrow(cb, Priority.ALWAYS);
        VBox.setVgrow(hBox, Priority.ALWAYS);
        hBox.setMaxWidth(Double.MAX_VALUE);
        cb.setMaxWidth(Double.MAX_VALUE);
        cb.setPrefWidth(-1);
        return hBox;
    }

    /**
     * Returns an instance of {@link EliteBgsAdapter}
     *
     * @return an instance of {@link EliteBgsAdapter}
     * @throws DataAdapterException if the provided parameters are invalid
     */
    private DataAdapter getDataAdapter() throws DataAdapterException {
        return new EliteBgsAdapter();
    }

}
