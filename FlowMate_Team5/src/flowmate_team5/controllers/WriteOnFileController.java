package flowmate_team5.controllers;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import flowmate_team5.models.actions.TextAction;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/* Controller responsible for handling the configuration of the Write to File Action. */
public class WriteOnFileController implements Initializable {

    @FXML
    private TextField pathTextField;

    @FXML
    private TextField contentTextField;

    // The action instance to be configured
    private TextAction action;

    /* Injects the specific TextAction instance to be modified. */
    public void setAction(TextAction action) {
        this.action = action;
    }

    /* Initializes the view and sets specific properties for UI components. */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Set path field to read-only to ensure valid file selection via the browse button
        pathTextField.setEditable(false);
    }

    /* Opens a FileChooser dialog to select the target text file. */
    @FXML
    public void browseButtonPushed() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Text File");
        // Limit selection to text files to ensure compatibility
        fileChooser.getExtensionFilters()
                .add(new FileChooser.ExtensionFilter("Text Files", "*.txt"));

        Stage stage = (Stage) pathTextField.getScene().getWindow();
        File selectedFile = fileChooser.showOpenDialog(stage);

        if (selectedFile != null) {
            pathTextField.setText(selectedFile.getAbsolutePath());
        }
    }

    /* Validates inputs, saves the configuration, and closes the window. */
    @FXML
    public void confirmButtonPushed(ActionEvent event) {
        String path = pathTextField.getText();
        String content = contentTextField.getText();

        // Ensure that both the file path and the content message are provided
        if (path == null || path.isEmpty() || content == null || content.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Missing Information");
            alert.setContentText("Please select a file and enter text.");
            alert.showAndWait();
            return;
        }

        // Update the action model with the user's input
        action.setFilePath(path);
        action.setMessage(content);

        // Close the configuration window
        ((Stage) ((Node) event.getSource())
                .getScene()
                .getWindow())
                .close();
    }
}