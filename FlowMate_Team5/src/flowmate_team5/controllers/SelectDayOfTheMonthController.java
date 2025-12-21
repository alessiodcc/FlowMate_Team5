package flowmate_team5.controllers;



import flowmate_team5.models.triggers.DayOfTheMonthTrigger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class SelectDayOfTheMonthController {

    @FXML
    private TextArea Introduction;
    @FXML
    private TextField dayField;
    @FXML
    private Button ConfirmButton;

    private DayOfTheMonthTrigger trigger;
    private int dayOfTheMonth;
    public void setTrigger(DayOfTheMonthTrigger trigger) {
        this.trigger = trigger;
    }

    @FXML
    void confirmButtonPushed(ActionEvent event) {
        // Validation of the input, showing an alert when it's not acceptable
        if(dayField.getText().isBlank() || !dayField.getText().matches("\\d+") || Integer.parseInt(dayField.getText()) > 31 ||  Integer.parseInt(dayField.getText()) < 1 ) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Missing Information / Incorrect Information");
            alert.setContentText(
                    "In order to go on, you must insert in the apposit text area" + "\n" +  "a day of the month"
            );
            alert.showAndWait();
        }
        else {
            dayOfTheMonth = Integer.parseInt(dayField.getText());
            trigger.setDayOfMonth(dayOfTheMonth);

            ((javafx.stage.Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow()).close();
        }

    }

}