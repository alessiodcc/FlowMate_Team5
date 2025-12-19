package flowmate_team5.controllers;

import flowmate_team5.models.actions.ExternalProgramAction;
import flowmate_team5.models.triggers.ExternalProgramTrigger;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class SelectExternalProgramController {

    @FXML private TextField programField;
    @FXML private TextField argsField;
    @FXML private TextField workDirField;

    // References to the object currently being configured (only one will be active)
    private ExternalProgramAction currentAction;
    private ExternalProgramTrigger currentTrigger;

    /**
     * Injects the Action object to be configured.
     * Called by MainPageController for US14.
     */
    public void setAction(ExternalProgramAction action) {
        this.currentAction = action;
        this.currentTrigger = null;

        // Pre-fill fields if editing an existing action
        if (action.getCommandLine() != null) {
            programField.setText(action.getCommandLine());
        }
        if (action.getWorkingDirectory() != null) {
            workDirField.setText(action.getWorkingDirectory());
        }
    }

    /**
     * Injects the Trigger object to be configured.
     * Called by MainPageController for US25.
     */
    public void setTrigger(ExternalProgramTrigger trigger) {
        this.currentTrigger = trigger;
        this.currentAction = null;

        if (trigger.getCommandLine() != null) {
            programField.setText(trigger.getCommandLine());
        }
    }

    @FXML
    private void handleBrowseProgram() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Executable");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Executables", "*.exe", "*.bat", "*.cmd", "*.sh", "*.app"),
                new FileChooser.ExtensionFilter("All Files", "*.*")
        );

        Stage stage = (Stage) programField.getScene().getWindow();
        File selectedFile = fileChooser.showOpenDialog(stage);

        if (selectedFile != null) {
            programField.setText(selectedFile.getAbsolutePath());
        }
    }

    @FXML
    private void handleBrowseDir() {
        DirectoryChooser dirChooser = new DirectoryChooser();
        dirChooser.setTitle("Select Working Directory");

        Stage stage = (Stage) workDirField.getScene().getWindow();
        File selectedDir = dirChooser.showDialog(stage);

        if (selectedDir != null) {
            workDirField.setText(selectedDir.getAbsolutePath());
        }
    }

    @FXML
    private void handleConfirm() {
        String program = programField.getText();
        String args = argsField.getText();
        String workDir = workDirField.getText();

        if (program == null || program.trim().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Validation Error");
            alert.setContentText("Please select an executable file or enter a command.");
            alert.showAndWait();
            return;
        }

        // Construct full command string
        String fullCommand = program;
        if (args != null && !args.trim().isEmpty()) {
            fullCommand += " " + args.trim();
        }

        // Save data to the active object (Action OR Trigger)
        if (currentAction != null) {
            currentAction.setCommandLine(fullCommand);
            if (workDir != null && !workDir.trim().isEmpty()) {
                currentAction.setWorkingDirectory(workDir);
            }
        }
        else if (currentTrigger != null) {
            currentTrigger.setCommandLine(fullCommand);
        }

        Stage stage = (Stage) programField.getScene().getWindow();
        stage.close();
    }
}