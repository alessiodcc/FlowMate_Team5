package flowmate_team5.controllers;

import flowmate_team5.models.triggers.DayOfTheWeekTrigger;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.stage.Stage;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;

// Class name now includes "OfThe"
public class SelectDayOfTheWeekController {

    @FXML private CheckBox cbMonday;
    @FXML private CheckBox cbTuesday;
    @FXML private CheckBox cbWednesday;
    @FXML private CheckBox cbThursday;
    @FXML private CheckBox cbFriday;
    @FXML private CheckBox cbSaturday;
    @FXML private CheckBox cbSunday;

    private DayOfTheWeekTrigger trigger;

    public void setTrigger(DayOfTheWeekTrigger trigger) {
        this.trigger = trigger;
    }

    @FXML
    private void handleConfirm() {
        List<DayOfWeek> selectedDays = new ArrayList<>();

        if (cbMonday.isSelected()) selectedDays.add(DayOfWeek.MONDAY);
        if (cbTuesday.isSelected()) selectedDays.add(DayOfWeek.TUESDAY);
        if (cbWednesday.isSelected()) selectedDays.add(DayOfWeek.WEDNESDAY);
        if (cbThursday.isSelected()) selectedDays.add(DayOfWeek.THURSDAY);
        if (cbFriday.isSelected()) selectedDays.add(DayOfWeek.FRIDAY);
        if (cbSaturday.isSelected()) selectedDays.add(DayOfWeek.SATURDAY);
        if (cbSunday.isSelected()) selectedDays.add(DayOfWeek.SUNDAY);

        if (selectedDays.isEmpty()) {
            showAlert();
            return;
        }

        if (trigger != null) {
            trigger.setDays(selectedDays);
        }

        Stage stage = (Stage) cbMonday.getScene().getWindow();
        stage.close();
    }

    private void showAlert() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("No Days Selected");
        alert.setContentText("Please select at least one day.");
        alert.showAndWait();
    }
}