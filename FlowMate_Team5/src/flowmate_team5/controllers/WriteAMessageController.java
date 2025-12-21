package flowmate_team5.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import flowmate_team5.models.actions.MessageAction;
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
 * Controller responsible for configuring a MessageAction.
 * It allows the user to write a custom message that will be
 * displayed when the action is executed.
 */
public class WriteAMessageController implements Initializable {

    // UI elements defined in the FXML file
    @FXML
    private TextArea Title;

    @FXML
    private TextArea Introduction;

    @FXML
    private Button ConfirmButton;

    @FXML
    private TextField inputText;

    // Action instance injected from the main controller
    private MessageAction action;

    /**
     * Injects the MessageAction to be configured.
     *
     * @param action the MessageAction instance
     */
    public void setAction(MessageAction action) {
        this.action = action;
    }

    /**
     * Initializes the controller after the FXML has been loaded.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    /**
     * Called when the confirm button is pressed.
     * Validates user input and configures the MessageAction.
     *
     * @param event the action event triggered by the button
     */
    @FXML
    public void confirmButtonPushed(ActionEvent event) {

        // Retrieve the message written by the user
        String writtenMessage = inputText.getText();

        // Validate message content using MessageAction utility method
        if (!MessageAction.isValidMessage(writtenMessage)) {
            Alert emptyAlert = new Alert(AlertType.ERROR);
            emptyAlert.setTitle("ATTENTION!");
            emptyAlert.setHeaderText(
                    "Invalid message! It must not be empty and under 500 characters."
            );
            emptyAlert.showAndWait();
            return;
        }

        // Configure the action
        action.setName("MessageAction1");
        action.setMessageToShow(writtenMessage);

        // Close the window after confirmation
        Stage stage = (Stage) ((Node) event.getSource())
                .getScene()
                .getWindow();
        stage.close();
    }
}
