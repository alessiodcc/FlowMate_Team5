package flowmate_team5;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
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
    private TextField pathTextField; // FXML field to display the selected audio file path
    private PlayAudioAction finalAction;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        pathTextField.setEditable(false); // Prevent the user from manually typing the path; selection via FileChooser is mandatory.
    }

    public PlayAudioAction getFinalAction() {
        return finalAction;
    }

    /**
     * Handles the 'Browse' button push. Opens a native file dialog (FileChooser)
     * for the user to select an audio file.
     */
    @FXML
    public void selectFileButtonPushed() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Seleziona File Audio (.wav)");

        // Set extension filters to guide the user towards compatible formats (WAV, AIFF).
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("File Audio (WAV, AIFF)", "*.wav", "*.aiff", "*.aif")
        );

        Stage stage = (Stage) pathTextField.getScene().getWindow();
        File selectedFile = fileChooser.showOpenDialog(stage);

        if (selectedFile != null) {
            // Display the absolute path of the selected file in the TextField.
            pathTextField.setText(selectedFile.getAbsolutePath());
        }
    }

    /**
     * Handles the 'Confirm' button push. Validates the selected path,
     * creates the final PlayAudioAction, and closes the modal window.
     * * @param event The ActionEvent from the button press.
     */
    @FXML
    public void confirmButtonPushed(ActionEvent event) {
        String audioPath = pathTextField.getText();

        // Validation: Check if a file path was selected.
        if (audioPath == null || audioPath.trim().isEmpty()) {
            Alert emptyAlert = new Alert(AlertType.ERROR);
            emptyAlert.setTitle("ATTENZIONE!");
            emptyAlert.setHeaderText("Per continuare, devi selezionare il percorso del file audio tramite il pulsante 'Sfoglia'.");
            emptyAlert.showAndWait();
            return;
        }

        this.finalAction = new PlayAudioAction(audioPath.trim());

        // Close the modal stage
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }
}