package flowmate_team5;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class WriteOnFileController implements Initializable {

    @FXML
    private TextField pathTextField;

    @FXML
    private TextField contentTextField;

    private TextAction action;

    public void setAction(TextAction action) {
        this.action = action;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Set path field to read-only to ensure valid file selection
        pathTextField.setEditable(false);
    }

    @FXML
    public void browseButtonPushed() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Text File");
        // Filter to allow only text files
        fileChooser.getExtensionFilters()
                .add(new FileChooser.ExtensionFilter("Text Files", "*.txt"));

        Stage stage = (Stage) pathTextField.getScene().getWindow();
        File selectedFile = fileChooser.showOpenDialog(stage);

        if (selectedFile != null) {
            pathTextField.setText(selectedFile.getAbsolutePath());
        }
    }

    @FXML
    public void confirmButtonPushed(ActionEvent event) {
        String path = pathTextField.getText();
        String content = contentTextField.getText();

        // Validate that both fields are populated
        if (path == null || path.isEmpty() || content == null || content.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Missing Information");
            alert.setContentText("Please select a file and enter text.");
            alert.showAndWait();
            return;
        }

        // Configure the action object
        action.setFilePath(path);
        action.setMessage(content);

        // Close the window
        ((Stage) ((Node) event.getSource())
                .getScene()
                .getWindow())
                .close();
    }
}