package flowmate_team5.controllers;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import flowmate_team5.models.actions.CopyFileAction;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class CopyFileController implements Initializable {

    @FXML
    private TextField sourcePathField;

    @FXML
    private TextField destDirField;

    // Action instance received from the main controller (NOT created here)
    private CopyFileAction action;

    public void setAction(CopyFileAction action) {
        this.action = action;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Set fields to read-only to ensure valid paths are selected via buttons
        sourcePathField.setEditable(false);
        destDirField.setEditable(false);
    }

    @FXML
    public void browseSource() {
        FileChooser fc = new FileChooser();
        fc.setTitle("Select File to Copy");
        File f = fc.showOpenDialog(null);
        if (f != null) {
            sourcePathField.setText(f.getAbsolutePath());
        }
    }

    @FXML
    public void browseDest() {
        DirectoryChooser dc = new DirectoryChooser();
        dc.setTitle("Select Destination Directory");
        File f = dc.showDialog(null);
        if (f != null) {
            destDirField.setText(f.getAbsolutePath());
        }
    }

    @FXML
    public void confirm(ActionEvent event) {

        // Validate that both fields are populated
        if (sourcePathField.getText().isEmpty() ||
                destDirField.getText().isEmpty()) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Incomplete Configuration");
            alert.setContentText(
                    "Please select both a source file and a destination folder."
            );
            alert.showAndWait();
            return;
        }

        // ONLY configuration logic
        action.setSourcePath(sourcePathField.getText());
        action.setDestinationDir(destDirField.getText());

        // Close the window
        Stage stage = (Stage) ((Node) event.getSource())
                .getScene()
                .getWindow();
        stage.close();
    }
}