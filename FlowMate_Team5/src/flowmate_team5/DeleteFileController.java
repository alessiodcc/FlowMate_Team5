package flowmate_team5;

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

/**
 * FXML Controller class
 *
 * @author Alessio
 */
public class DeleteFileController implements Initializable {
    @FXML
    private TextField filePathField;
    @FXML
    private Button browseButton;
    @FXML
    private Button confirmButton;

    private DeleteFileAction finalAction;

    public DeleteFileAction getFinalAction() {
        return finalAction;
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        confirmButton.setDisable(false);
    }

    /**
     * When the Browse button is pushed, a FileChooser is opened.
     * If the user selects a specific file, the confirm button is enabled
     * and the path of the file is shown.
     */
    @FXML
    private void browseFilePushed(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select the file you want to delete");

        Stage stage = (Stage) browseButton.getScene().getWindow();
        File selectedFile = fileChooser.showOpenDialog(stage);

        if(selectedFile != null) {
            Path selectedPath = selectedFile.toPath();
            filePathField.setText(selectedPath.toString());
        }
    }

    /**
     * When the Confirm button is pushed, if the user has not selected a specific file
     * an alert is shown, inviting the user to select one.
     * If the user has selected a specific file, a new object of the
     * DeleteFileAction is created, and the graphical window is closed.
     */
    @FXML
    private void confirmButtonPushed(ActionEvent event) {
        String selectedFilePathS = filePathField.getText();
        if(selectedFilePathS == null || selectedFilePathS.trim().isEmpty()) {
            Alert emptyAlert = new Alert(Alert.AlertType.ERROR);
            emptyAlert.setTitle("ATTENTION!");
            emptyAlert.setHeaderText("To go on, it is necessary to select a file!");
            emptyAlert.showAndWait();
            return;
        }

        Path selectedFilePathP = Path.of(selectedFilePathS);
        this.finalAction = new DeleteFileAction(selectedFilePathP);

        // Close the current window to return control to the Main Page
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

}