package flowmate_team5;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * Controller for the "Write a Message" GUI.
 * Handles the creation of a MessageAction based on user input.
 */
public class WriteAMessageController implements Initializable {

    @FXML
    private TextArea Title;
    @FXML
    private TextArea Introduction;
    @FXML
    private Button ConfirmButton;
    @FXML
    private TextField inputText;

    private MessageAction finalAction; // Stores the created action to return it to the main controller

    public Action getFinalAction() {
        return finalAction;
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Initialization logic if needed
    }

    /**
     * Triggered when the "Confirm" button is clicked.
     * Validates input and creates the MessageAction.
     */
    @FXML
    public void confirmButtonPushed(ActionEvent event) {
        String writtenMessage = inputText.getText();

        // Validate input using the centralized validation logic from the Model
        if (!MessageAction.isValidMessage(writtenMessage)) {
            Alert emptyAlert = new Alert(AlertType.ERROR);
            emptyAlert.setTitle("ATTENTION!");
            emptyAlert.setHeaderText("Invalid message! It must not be empty and under 500 characters.");
            emptyAlert.showAndWait();
            return;
        }

        // Instantiate the concrete Action object
        this.finalAction = new MessageAction("MessageAction1", writtenMessage);

        // Close the current window to return control to the Main Page
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }
}