package flowmate_team5.controllers;

import flowmate_team5.models.actions.ExternalProgramAction;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

/* Controller responsible for configuring the External Program Action. */
public class SelectExternalProgramActionController {

    @FXML private TextField programField;
    @FXML private TextField argsField;
    @FXML private TextField workDirField;

    // The action instance currently being configured
    private ExternalProgramAction pendingAction;

    /* Injects the action instance and pre-fills the UI if data exists. */
    public void setAction(ExternalProgramAction action) {
        this.pendingAction = action;

        if (action.getCommandLine() != null) {
            programField.setText(action.getCommandLine());
        }
        if (action.getWorkingDirectory() != null) {
            workDirField.setText(action.getWorkingDirectory());
        }
    }

    /* Opens a FileChooser to allow the user to select an executable file. */
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

    /* Opens a DirectoryChooser to select the working directory for the process. */
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

    /* Validates input, constructs the command, and saves it to the action model. */
    @FXML
    private void handleConfirm() {
        String program = programField.getText();
        String args = argsField.getText();
        String workDir = workDirField.getText();

        // Ensure a program has been selected
        if (program == null || program.trim().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Validation Error");
            alert.setContentText("Please select an executable file or enter a command.");
            alert.showAndWait();
            return;
        }

        // Construct the full command string by appending arguments if present
        String fullCommand = program;
        if (args != null && !args.trim().isEmpty()) {
            fullCommand += " " + args.trim();
        }

        // Update the action model with the new configuration
        if (pendingAction != null) {
            pendingAction.setCommandLine(fullCommand);
            if (workDir != null && !workDir.trim().isEmpty()) {
                pendingAction.setWorkingDirectory(workDir);
            }
        }

        // Close the configuration window
        Stage stage = (Stage) programField.getScene().getWindow();
        stage.close();
    }
}