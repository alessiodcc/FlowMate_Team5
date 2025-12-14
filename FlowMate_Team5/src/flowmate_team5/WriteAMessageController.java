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

public class WriteAMessageController implements Initializable {

    @FXML
    private TextArea Title;

    @FXML
    private TextArea Introduction;

    @FXML
    private Button ConfirmButton;

    @FXML
    private TextField inputText;

    // Action instance received from the main controller (NOT created here)
    private MessageAction action;

    public void setAction(MessageAction action) {
        this.action = action;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Initialization logic
    }

    @FXML
    public void confirmButtonPushed(ActionEvent event) {

        String writtenMessage = inputText.getText();

        // Validate message content
        if (!MessageAction.isValidMessage(writtenMessage)) {
            Alert emptyAlert = new Alert(AlertType.ERROR);
            emptyAlert.setTitle("ATTENTION!");
            emptyAlert.setHeaderText(
                    "Invalid message! It must not be empty and under 500 characters."
            );
            emptyAlert.showAndWait();
            return;
        }

        // ONLY configuration logic
        action.setName("MessageAction1");
        action.setMessageToShow(writtenMessage);

        // Close window
        Stage stage = (Stage) ((Node) event.getSource())
                .getScene()
                .getWindow();
        stage.close();
    }
}