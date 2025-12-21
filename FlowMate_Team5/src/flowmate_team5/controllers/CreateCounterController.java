package flowmate_team5.controllers;

import flowmate_team5.models.Counter;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class CreateCounterController {
    @FXML
    private TextArea Introduction;
    @FXML
    private TextField counterNameField;
    @FXML
    private TextField initialValueField;
    @FXML
    private Button ConfirmButton;

    private Counter counter;
    private String counterName;
    private Integer counterValue;

    public Counter getCounter() {
        return counter;
    }

    /**
     * When the confirm button is pushed, this method checks
     * if the user typed all the necessary fields.
     * If he did, a new counter is created.
     */
    @FXML
    void confirmButtonPushed(ActionEvent event) {
        if(counterNameField.getText().isEmpty() || !initialValueField.getText().matches("\\d+") || initialValueField.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Missing Information / Incorrect Information");
            alert.setContentText(
                    "In order to go on, you must insert both a name and an INTEGER value!"
            );
            alert.showAndWait();
        }
        else {
            this.counterName = counterNameField.getText();
            this.counterValue = Integer.parseInt(initialValueField.getText());
            counter = new Counter(counterName, counterValue);

            ((Stage)((Node)event.getSource()).getScene().getWindow()).close();
        }
    }

}
