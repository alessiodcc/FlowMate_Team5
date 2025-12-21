package flowmate_team5.controllers;

import flowmate_team5.models.triggers.ExternalProgramTrigger;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;

/* Controller responsible for the configuration view of the External Program Trigger (US25). */
public class SelectExternalProgramTriggerController {

    @FXML private TextField programField;

    // The trigger instance being configured
    private ExternalProgramTrigger currentTrigger;

    /* Injects the trigger instance and populates the field if data exists. */
    public void setTrigger(ExternalProgramTrigger trigger) {
        this.currentTrigger = trigger;
        if (trigger.getCommandLine() != null) {
            programField.setText(trigger.getCommandLine());
        }
    }

    /* Opens a FileChooser to select the application executable to monitor. */
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
            // Store the absolute path in the text field for later use
            programField.setText(selectedFile.getAbsolutePath());
        }
    }

    /* Validates the input and saves the configuration to the trigger model. */
    @FXML
    private void handleConfirm() {
        String program = programField.getText();

        // Ensure that a program path has been specified
        if (program == null || program.trim().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Please select an application to monitor.");
            alert.showAndWait();
            return;
        }

        // Update the trigger configuration with the specified command line
        if (currentTrigger != null) {
            currentTrigger.setCommandLine(program);
        }

        // Close the configuration window
        ((Stage) programField.getScene().getWindow()).close();
    }
}