package flowmate_team5.controllers;

import flowmate_team5.models.actions.ExternalProgramAction;
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

    private ExternalProgramAction pendingAction;

    // Triggered by "Browse File" button
    @FXML
    private void handleBrowseProgram() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Executable");
        // Filter for common executables (Windows/Mac/Linux)
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

    // Triggered by "Browse Folder" button
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

        // Combine program and args if necessary, or set them individually
        // Here we construct the command line string expected by the model
        String fullCommand = program;
        if (args != null && !args.trim().isEmpty()) {
            fullCommand += " " + args.trim();
        }

        this.pendingAction = new ExternalProgramAction();
        this.pendingAction.setCommandLine(fullCommand);

        if (workDir != null && !workDir.trim().isEmpty()) {
            this.pendingAction.setWorkingDirectory(workDir);
        }

        Stage stage = (Stage) programField.getScene().getWindow();
        stage.close();
    }

    public ExternalProgramAction getAction() {
        return this.pendingAction;
    }
}