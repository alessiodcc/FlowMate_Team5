package flowmate_team5.controllers;

import flowmate_team5.models.actions.ExternalProgramAction;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class SelectExternalProgramActionController {

    @FXML private TextField programField;
    @FXML private TextField argsField;
    @FXML private TextField workDirField;

    private ExternalProgramAction pendingAction;

    /**
     * Initializes the controller with the specific action instance.
     * Pre-fills fields if the action was already configured.
     */
    public void setAction(ExternalProgramAction action) {
        this.pendingAction = action;

        if (action.getCommandLine() != null) {
            programField.setText(action.getCommandLine());
        }
        if (action.getWorkingDirectory() != null) {
            workDirField.setText(action.getWorkingDirectory());
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

        // Combine program and arguments
        String fullCommand = program;
        if (args != null && !args.trim().isEmpty()) {
            fullCommand += " " + args.trim();
        }

        // Save configuration to the action model
        if (pendingAction != null) {
            pendingAction.setCommandLine(fullCommand);
            if (workDir != null && !workDir.trim().isEmpty()) {
                pendingAction.setWorkingDirectory(workDir);
            }
        }

        // Close the window
        Stage stage = (Stage) programField.getScene().getWindow();
        stage.close();
    }
}