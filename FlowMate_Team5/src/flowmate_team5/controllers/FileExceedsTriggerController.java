package flowmate_team5.controllers;

import flowmate_team5.models.triggers.FileExceedsTrigger;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.net.URL;
import java.nio.file.Path;
import java.util.ResourceBundle;

/**
 * Controller responsible for configuring a FileExceedsTrigger.
 * It allows the user to select a folder, specify a file name,
 * and define a maximum file size threshold.
 */
public class FileExceedsTriggerController implements Initializable {

    // TextField used to display and edit the selected folder path
    @FXML private TextField folderField;

    // TextField used to specify the file name to monitor
    @FXML private TextField fileNameField;

    // Spinner used to select the maximum allowed file size
    @FXML private Spinner<Integer> sizeSpinner;

    // ComboBox used to select the size unit (KB or MB)
    @FXML private ComboBox<String> unitComboBox;

    // Trigger instance to be configured by this controller
    private FileExceedsTrigger trigger;

    /**
     * Injects the FileExceedsTrigger from the MainPageController.
     * This controller does not create the trigger, it only configures it.
     *
     * @param trigger the trigger to configure
     */
    public void setTrigger(FileExceedsTrigger trigger) {
        this.trigger = trigger;
    }

    /**
     * Initializes UI components after the FXML has been loaded.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        // Configure the size spinner
        SpinnerValueFactory<Integer> sizeFactory =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 1_000_000, 1);
        sizeSpinner.setValueFactory(sizeFactory);

        // Populate the unit ComboBox with supported units
        unitComboBox.setItems(
                FXCollections.observableArrayList("KB", "MB")
        );

        // Set default unit to MB
        unitComboBox.getSelectionModel().select("MB");
    }

    /**
     * Handles the folder browsing action.
     * Opens a DirectoryChooser and updates the folder field
     * with the selected directory path.
     */
    @FXML
    private void handleBrowseFolder() {
        DirectoryChooser chooser = new DirectoryChooser();
        chooser.setTitle("Select Folder");

        Stage stage = (Stage) folderField.getScene().getWindow();
        var dir = chooser.showDialog(stage);

        if (dir != null) {
            folderField.setText(dir.getAbsolutePath());
        }
    }

    /**
     * Called when the user presses the confirm button.
     * Validates input, converts the size to bytes,
     * and applies the configuration to the trigger.
     */
    @FXML
    private void confirmButtonPushed() {

        // Ensure the trigger has been injected before use
        if (trigger == null) {
            throw new IllegalStateException("FileExceedsTrigger not injected");
        }

        // Retrieve user input
        String folderPath = folderField.getText();
        String fileName = fileNameField.getText();
        Integer size = sizeSpinner.getValue();
        String unit = unitComboBox.getValue();

        // Validate that all required fields are filled
        if (folderPath == null || folderPath.isBlank()
                || fileName == null || fileName.isBlank()
                || size == null || unit == null) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Validation Error");
            alert.setContentText("Please fill in all fields.");
            alert.showAndWait();
            return;
        }

        // Convert the selected size to bytes
        long sizeInBytes = switch (unit) {
            case "KB" -> size * 1024L;
            case "MB" -> size * 1024L * 1024L;
            default -> size;
        };

        // Configure the trigger with the provided values
        trigger.setFolderPath(Path.of(folderPath));
        trigger.setFileName(fileName);
        trigger.setMaxSizeInBytes(sizeInBytes);

        // Close the window after confirmation
        Stage stage = (Stage) folderField.getScene().getWindow();
        stage.close();
    }
}