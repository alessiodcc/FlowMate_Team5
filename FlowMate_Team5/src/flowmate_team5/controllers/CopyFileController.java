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

/* Controller responsible for the Copy File Action UI logic. */
public class CopyFileController implements Initializable {

    @FXML
    private TextField sourcePathField;

    @FXML
    private TextField destDirField;

    // The action instance to be configured
    private CopyFileAction action;

    /* Injects the CopyFileAction instance to be modified. */
    public void setAction(CopyFileAction action) {
        this.action = action;
    }

    /* Initializes the view and sets fields to read-only. */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Prevent manual editing to ensure valid paths
        sourcePathField.setEditable(false);
        destDirField.setEditable(false);
    }

    /* Opens a FileChooser to select the source file. */
    @FXML
    public void browseSource() {
        FileChooser fc = new FileChooser();
        fc.setTitle("Select File to Copy");
        File f = fc.showOpenDialog(null);
        if (f != null) {
            sourcePathField.setText(f.getAbsolutePath());
        }
    }

    /* Opens a DirectoryChooser to select the destination folder. */
    @FXML
    public void browseDest() {
        DirectoryChooser dc = new DirectoryChooser();
        dc.setTitle("Select Destination Directory");
        File f = dc.showDialog(null);
        if (f != null) {
            destDirField.setText(f.getAbsolutePath());
        }
    }

    /* Validates input and saves the configuration to the action. */
    @FXML
    public void confirm(ActionEvent event) {

        // Check if both paths are selected
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

        // Save the paths to the action model
        action.setSourcePath(sourcePathField.getText());
        action.setDestinationDir(destDirField.getText());

        // Close the current window
        Stage stage = (Stage) ((Node) event.getSource())
                .getScene()
                .getWindow();
        stage.close();
    }
}