package flowmate_team5;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.logging.Logger;

/**
 * Task 13.7: Controller for the MoveFileView.
 * Handles user interactions for selecting source files and destination directories.
 *
 * @author Ayesha
 */
public class MoveFileController {

    private static final Logger LOGGER = Logger.getLogger(MoveFileController.class.getName());

    @FXML private TextField sourceFileField;
    @FXML private TextField destFolderField;

    private MoveFileAction finalAction;

    /**
     * Retrieves the configured action after the window closes.
     * @return The created MoveFileAction, or null if cancelled.
     */
    public MoveFileAction getFinalAction() {
        return finalAction;
    }

    /**
     * Opens a FileChooser to let the user pick the file to move.
     */
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

    /**
     * Opens a DirectoryChooser to let the user pick the target folder.
     */
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

    /**
     * Validates inputs and creates the MoveFileAction object.
     */
    @FXML
    public void handleConfirm() {
        String sourcePath = sourceFileField.getText();
        String destPath = destFolderField.getText();

        // Validation
        if (sourcePath.isEmpty() || destPath.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Missing Information");
            alert.setHeaderText(null);
            alert.setContentText("Please select both a file and a destination folder.");
            alert.showAndWait();
            LOGGER.warning("User attempted to confirm without selecting paths.");
            return;
        }

        // Create the Action (Using the backend class you made in Task 13.5)
        this.finalAction = new MoveFileAction(sourcePath, destPath);
        LOGGER.info("MoveFileAction created successfully.");

        // Close the window
        Stage stage = (Stage) sourceFileField.getScene().getWindow();
        stage.close();
    }
}