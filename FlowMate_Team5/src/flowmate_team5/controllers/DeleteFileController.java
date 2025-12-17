package flowmate_team5.controllers;

import flowmate_team5.models.actions.DeleteFileAction;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.nio.file.Path;
import java.util.ResourceBundle;

public class DeleteFileController implements Initializable {

    @FXML
    private TextField filePathField;

    @FXML
    private Button browseButton;

    @FXML
    private Button confirmButton;

    // Action instance received from the main controller (NOT created here)
    private DeleteFileAction action;

    public void setAction(DeleteFileAction action) {
        this.action = action;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        confirmButton.setDisable(false);
    }

    @FXML
    private void browseFilePushed(ActionEvent event) {

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select the file you want to delete");

        Stage stage = (Stage) browseButton.getScene().getWindow();
        File selectedFile = fileChooser.showOpenDialog(stage);

        if (selectedFile != null) {
            filePathField.setText(selectedFile.toPath().toString());
        }
    }

    @FXML
    private void confirmButtonPushed(ActionEvent event) {

        String selectedFilePathS = filePathField.getText();

        // Validate that the file path is not empty
        if (selectedFilePathS == null || selectedFilePathS.trim().isEmpty()) {
            Alert emptyAlert = new Alert(Alert.AlertType.ERROR);
            emptyAlert.setTitle("ATTENTION!");
            emptyAlert.setHeaderText("To go on, it is necessary to select a file!");
            emptyAlert.showAndWait();
            return;
        }

        // ONLY configuration logic
        Path selectedFilePathP = Path.of(selectedFilePathS);
        action.setFilePath(selectedFilePathP);

        // Close window
        Stage stage = (Stage) ((Node) event.getSource())
                .getScene()
                .getWindow();
        stage.close();
    }
}