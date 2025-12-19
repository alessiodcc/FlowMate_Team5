package flowmate_team5.controllers;

import flowmate_team5.models.triggers.ExternalProgramTrigger;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;

/**
 * Controller for configuring the External Program Trigger (US25).
 * Handles user input for selecting a process to monitor.
 */
public class SelectExternalProgramTriggerController {

    @FXML private TextField programField;

    private ExternalProgramTrigger currentTrigger;

    /**
     * Injects the trigger instance to be configured.
     * Pre-fills the text field if a value exists.
     */
    public void setTrigger(ExternalProgramTrigger trigger) {
        this.currentTrigger = trigger;
        if (trigger.getCommandLine() != null) {
            programField.setText(trigger.getCommandLine());
        }
    }

    /**
     * Opens a file chooser to select the application executable.
     */
    @FXML
    private void handleBrowseProgram() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Application");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Applications", "*.exe"),
                new FileChooser.ExtensionFilter("All Files", "*.*")
        );

        Stage stage = (Stage) programField.getScene().getWindow();
        File selectedFile = fileChooser.showOpenDialog(stage);

        if (selectedFile != null) {
            // We store the full path, but the logic will checks for the process name
            programField.setText(selectedFile.getAbsolutePath());
        }
    }

    /**
     * Validates input and saves the configuration to the trigger object.
     */
    @FXML
    private void handleConfirm() {
        String program = programField.getText();

        // Validate that input is not empty
        if (program == null || program.trim().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Please select an application to monitor.");
            alert.showAndWait();
            return;
        }

        // Save the command line to the trigger model
        if (currentTrigger != null) {
            currentTrigger.setCommandLine(program);
        }

        // Close the modal window
        ((Stage) programField.getScene().getWindow()).close();
    }
}