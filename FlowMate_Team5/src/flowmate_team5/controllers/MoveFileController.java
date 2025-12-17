package flowmate_team5.controllers;

import flowmate_team5.models.actions.MoveFileAction;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.logging.Logger;

public class MoveFileController {

    private static final Logger LOGGER =
            Logger.getLogger(MoveFileController.class.getName());

    @FXML
    private TextField sourceFileField;

    @FXML
    private TextField destFolderField;

    // Action instance received from the main controller (NOT created here)
    private MoveFileAction action;

    public void setAction(MoveFileAction action) {
        this.action = action;
    }

    @FXML
    public void handleBrowseSource() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Source File");

        Stage stage = (Stage) sourceFileField.getScene().getWindow();
        File selectedFile = fileChooser.showOpenDialog(stage);

        if (selectedFile != null) {
            sourceFileField.setText(selectedFile.getAbsolutePath());
        }
    }

    @FXML
    public void handleBrowseDest() {
        DirectoryChooser dirChooser = new DirectoryChooser();
        dirChooser.setTitle("Select Destination Directory");

        Stage stage = (Stage) destFolderField.getScene().getWindow();
        File selectedDir = dirChooser.showDialog(stage);

        if (selectedDir != null) {
            destFolderField.setText(selectedDir.getAbsolutePath());
        }
    }

    @FXML
    public void handleConfirm() {

        String sourcePath = sourceFileField.getText();
        String destPath = destFolderField.getText();

        // Validate inputs
        if (sourcePath == null || sourcePath.isEmpty() ||
                destPath == null || destPath.isEmpty()) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Missing Information");
            alert.setHeaderText(null);
            alert.setContentText(
                    "Please select both a file and a destination folder."
            );
            alert.showAndWait();

            LOGGER.warning(
                    "User attempted to confirm without selecting paths."
            );
            return;
        }

        // ONLY configuration logic
        action.setSourcePathString(sourcePath);
        action.setDestinationDirectoryString(destPath);

        LOGGER.info("MoveFileAction configured successfully.");

        // Close the window
        Stage stage = (Stage) sourceFileField.getScene().getWindow();
        stage.close();
    }
}