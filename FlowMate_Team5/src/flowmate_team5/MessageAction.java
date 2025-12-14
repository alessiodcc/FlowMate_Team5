package flowmate_team5;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import java.io.Serializable;

public class MessageAction implements Action, Serializable {

    private String name;
    private String messageToShow;

    public MessageAction() {
        // Empty constructor (Factory Method)
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMessageToShow() {
        return messageToShow;
    }

    public void setMessageToShow(String messageToShow) {
        this.messageToShow = messageToShow;
    }

    @Override
    public void execute() {
        // JavaFX UI updates must run on the JavaFX Application Thread
        Platform.runLater(() -> {
            Alert alert = new Alert(AlertType.INFORMATION);

            alert.setTitle("Reminder!");
            alert.setHeaderText("Here is your reminder!");

            // Determine content to display
            String content =
                    (messageToShow != null && !messageToShow.isBlank())
                            ? messageToShow
                            : "No message configured!";

            alert.setContentText(content);
            alert.showAndWait();

            System.out.println("[MessageAction] Alert displayed.");
        });
    }

    public static boolean isValidMessage(String message) {
        // Validation: not null, not empty, max length 500
        return message != null &&
                !message.trim().isEmpty() &&
                message.length() <= 500;
    }

    @Override
    public String toString() {
        return "Message Action";
    }
}