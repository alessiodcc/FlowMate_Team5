package flowmate_team5.controllers;

import flowmate_team5.models.triggers.ExternalProgramTrigger;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;

public class SelectExternalProgramTriggerController {

    @FXML private TextField programField;
    @FXML private TextField argsField;

    private ExternalProgramTrigger currentTrigger;

    /**
     * Called by MainPageController to pass the trigger object.
     */
    public void setTrigger(ExternalProgramTrigger trigger) {
        this.currentTrigger = trigger;
        if (trigger.getCommandLine() != null) {
            programField.setText(trigger.getCommandLine());
        }
    }

    @FXML
    private void handleBrowseProgram() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Executable to Check");
        // Filters for common scripts/executables
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Executables/Scripts", "*.exe", "*.bat", "*.sh", "*.cmd", "*.py"),
                new FileChooser.ExtensionFilter("All Files", "*.*")
        );

        Stage stage = (Stage) programField.getScene().getWindow();
        File selectedFile = fileChooser.showOpenDialog(stage);
        if (selectedFile != null) {
            programField.setText(selectedFile.getAbsolutePath());
        }
    }

    @FXML
    private void handleConfirm() {
        String program = programField.getText();
        String args = argsField.getText();

        if (program == null || program.trim().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Validation Error");
            alert.setContentText("Please select a program to monitor.");
            alert.showAndWait();
            return;
        }

        String fullCommand = program;
        if (args != null && !args.trim().isEmpty()) {
            fullCommand += " " + args.trim();
        }

        if (currentTrigger != null) {
            currentTrigger.setCommandLine(fullCommand);
        }

        ((Stage) programField.getScene().getWindow()).close();
    }
}