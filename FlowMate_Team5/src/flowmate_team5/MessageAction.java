/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package flowmate_team5;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/**
 *
 * @author Sara
 */
public class MessageAction implements Action{
    private String name; // Name of the action
    private String messageToShow; // Message that the user wants to show

    // Constructor
    public MessageAction(String name) {
        this.name = name; 
        this.messageToShow = messageToShow;
    }

    // Getter and Setter
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
    
    // Method that shows graphically the message through an Alert
    @Override
    public void execute() {
        Alert alert = new Alert(AlertType.INFORMATION);
        
        alert.setTitle("Reminder!");
        alert.setHeaderText("Here is your reminder!");
        
        alert.setContentText(this.messageToShow.toString());
        
        alert.showAndWait();
    }
    
}