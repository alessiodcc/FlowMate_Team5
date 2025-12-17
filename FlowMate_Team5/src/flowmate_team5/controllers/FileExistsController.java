package flowmate_team5.controllers;

import flowmate_team5.models.triggers.FileExistsTrigger;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Logger;

public class FileExistsController {

    private static final Logger LOGGER =
            Logger.getLogger(FileExistsController.class.getName());

    @FXML
    private TextField folderPathField;

    @FXML
    private TextField fileNameField;

    // Trigger instance received from the main controller (NOT created here)
    private FileExistsTrigger trigger;

    public void setTrigger(FileExistsTrigger trigger) {
        this.trigger = trigger;
    }

    @FXML
    public void handleBrowseFolder() {

        DirectoryChooser dirChooser = new DirectoryChooser();
        dirChooser.setTitle("Select Folder to Watch");

        Stage stage = (Stage) folderPathField.getScene().getWindow();
        File selectedDir = dirChooser.showDialog(stage);

        if (selectedDir != null) {
            folderPathField.setText(selectedDir.getAbsolutePath());
        }
    }

    @FXML
    public void handleConfirm() {

        String folder = folderPathField.getText();
        String file = fileNameField.getText();

        // Validate inputs
        if (folder == null || folder.isEmpty() ||
                file == null || file.trim().isEmpty()) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Missing Information");
            alert.setContentText(
                    "Please select a folder and enter a filename."
            );
            alert.showAndWait();
            return;
        }

        // ONLY configuration logic
        Path folderPath = Paths.get(folder);
        trigger.setFolderPath(folderPath);
        trigger.setFileName(file.trim());

        LOGGER.info("Trigger configured for file: " + file);

        Stage stage = (Stage) folderPathField.getScene().getWindow();
        stage.close();
    }
}