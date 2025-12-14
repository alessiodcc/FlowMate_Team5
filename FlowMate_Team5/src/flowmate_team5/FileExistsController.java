package flowmate_team5;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;
import java.nio.file.Paths;
import java.util.logging.Logger;

/**
 * Task 12.3: Controller for setting up the File Exists Trigger.
 * It connects the GUI to the backend logic (Task 12.1).
 */
public class FileExistsController {

    private static final Logger LOGGER = Logger.getLogger(FileExistsController.class.getName());

    @FXML private TextField folderPathField;
    @FXML private TextField fileNameField;

    private FileExistsTrigger finalTrigger;

    // Returns the created trigger to the Main Page
    public FileExistsTrigger getFinalTrigger() {
        return finalTrigger;
    }

    // Opens a popup to select a folder from the computer
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

    // Validates input and creates the Trigger object
    @FXML
    public void handleConfirm() {
        String folder = folderPathField.getText();
        String file = fileNameField.getText();

        // 1. Check if fields are empty
        if (folder.isEmpty() || file.trim().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Missing Information");
            alert.setContentText("Please select a folder and enter a filename.");
            alert.showAndWait();
            return;
        }

        // 2. Create the Trigger (Connecting to Task 12.1)
        // We must convert the folder String to a Path object to match the constructor
        this.finalTrigger = new FileExistsTrigger(file.trim(), Paths.get(folder));

        LOGGER.info("Trigger configured for file: " + file);

        // 3. Close the window
        Stage stage = (Stage) folderPathField.getScene().getWindow();
        stage.close();
    }
}