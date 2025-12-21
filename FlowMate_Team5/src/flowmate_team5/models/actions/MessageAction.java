package flowmate_team5.models.actions;

import flowmate_team5.models.Action;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import java.io.Serializable;

/* Represents an action that displays a pop-up alert with a custom message. */
public class MessageAction implements Action, Serializable {

    private String name;
    private String messageToShow;

    /* Default constructor required for factory instantiation. */
    public MessageAction() {
        // Empty constructor (Factory Method)
    }

    /* Retrieves the name of the action. */
    public String getName() {
        return name;
    }

    /* Sets the name of the action. */
    public void setName(String name) {
        this.name = name;
    }

    /* Retrieves the message text to be displayed. */
    public String getMessageToShow() {
        return messageToShow;
    }

    /* Sets the message text to be displayed in the alert. */
    public void setMessageToShow(String messageToShow) {
        this.messageToShow = messageToShow;
    }

    /* Executes the action by showing an information alert on the UI thread. */
    @Override
    public void execute() {
        // JavaFX UI updates must run on the JavaFX Application Thread
        Platform.runLater(() -> {
            Alert alert = new Alert(AlertType.INFORMATION);

            alert.setTitle("Reminder!");
            alert.setHeaderText("Here is your reminder!");

            // Determine content to display, defaulting if empty
            String content =
                    (messageToShow != null && !messageToShow.isBlank())
                            ? messageToShow
                            : "No message configured!";

            alert.setContentText(content);
            alert.showAndWait();

            System.out.println("[MessageAction] Alert displayed.");
        });
    }

    /* Validates the message content to ensure it meets length and format constraints. */
    public static boolean isValidMessage(String message) {
        // Ensure message is not null, not empty, and within the 500 character limit
        return message != null &&
                !message.trim().isEmpty() &&
                message.length() <= 500;
    }

    /* Returns the display name of this action type. */
    @Override
    public String toString() {
        return "Message Action";
    }
}