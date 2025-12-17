package flowmate_team5.controllers;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import flowmate_team5.models.actions.PlayAudioAction;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class SelectAudioPathController implements Initializable {

    @FXML
    private TextField pathTextField;

    // Action instance received from the main controller (NOT created here)
    private PlayAudioAction action;

    public void setAction(PlayAudioAction action) {
        this.action = action;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Set path field to read-only to ensure valid file selection via the button
        pathTextField.setEditable(false);
    }

    @FXML
    public void selectFileButtonPushed() {

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Audio File (.wav)");

        // Filter for supported audio formats (standard Java Sound API supports WAV, AIFF)
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter(
                        "Audio Files (WAV, AIFF)", "*.wav", "*.aiff", "*.aif"
                )
        );

        Stage stage = (Stage) pathTextField.getScene().getWindow();
        File selectedFile = fileChooser.showOpenDialog(stage);

        if (selectedFile != null) {
            pathTextField.setText(selectedFile.getAbsolutePath());
        }
    }

    @FXML
    public void confirmButtonPushed(ActionEvent event) {

        String audioPath = pathTextField.getText();

        // Validate that a file has been selected
        if (audioPath == null || audioPath.trim().isEmpty()) {
            Alert emptyAlert = new Alert(AlertType.ERROR);
            emptyAlert.setTitle("ATTENTION!");
            emptyAlert.setHeaderText(
                    "To continue, you must select the audio file path using the 'Browse' button."
            );
            emptyAlert.showAndWait();
            return;
        }

        // ONLY configuration logic
        action.setFilePath(audioPath.trim());

        // Close window
        Stage stage = (Stage) ((Node) event.getSource())
                .getScene()
                .getWindow();
        stage.close();
    }
}