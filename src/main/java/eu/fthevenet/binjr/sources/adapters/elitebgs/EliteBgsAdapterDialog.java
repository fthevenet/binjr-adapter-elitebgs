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
import eu.binjr.core.preferences.GlobalPreferences;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.stage.FileChooser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
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
        var browsingMode = new ChoiceBox<FactionBrowsingMode>();
        browsingMode.getItems().addAll(FactionBrowsingMode.values());

        HBox pathHBox = new HBox();
        pathHBox.setSpacing(10);
        pathHBox.setAlignment(Pos.CENTER);
        pathHBox.getChildren().addAll(browsingMode);
        browsingMode.setPrefWidth(-1);
        HBox.setHgrow(pathHBox, Priority.ALWAYS);

        DialogPane dialogPane = new DialogPane();
        dialogPane.setHeaderText("Minor Factions Influence");
        dialogPane.getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        dialogPane.setGraphic(new Region());
        dialogPane.getGraphic().getStyleClass().addAll("elite-icon", "dialog-icon");
        dialogPane.setContent(pathHBox);
        this.setDialogPane(dialogPane);


        Button okButton = (Button) dialogPane.lookupButton(ButtonType.OK);
        Platform.runLater(browsingMode::requestFocus);

        okButton.addEventFilter(ActionEvent.ACTION, ae -> {
            try {
                result = getDataAdapter();
            } catch (CannotInitializeDataAdapterException e) {
                Dialogs.notifyError("Error initializing adapter to source", e, Pos.CENTER, browsingMode);
                ae.consume();
            } catch (DataAdapterException e) {
                Dialogs.notifyError("Error with the adapter to source", e, Pos.CENTER, browsingMode);
                ae.consume();
            } catch (Throwable e) {
                Dialogs.notifyError("Unexpected error while retrieving data adapter", e, Pos.CENTER, browsingMode);
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
