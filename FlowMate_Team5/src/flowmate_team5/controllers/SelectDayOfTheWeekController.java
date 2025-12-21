package flowmate_team5.controllers;

// CORRECTED IMPORT TO MATCH YOUR CREATOR
import flowmate_team5.models.triggers.DayOfTheWeekTrigger;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.stage.Stage;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;

public class SelectDayOfWeekController {

    @FXML private CheckBox cbMonday;
    @FXML private CheckBox cbTuesday;
    @FXML private CheckBox cbWednesday;
    @FXML private CheckBox cbThursday;
    @FXML private CheckBox cbFriday;
    @FXML private CheckBox cbSaturday;
    @FXML private CheckBox cbSunday;

    // Corrected Type
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
            showAlert("No Days Selected", "Please select at least one day.");
            return;
        }

        if (trigger != null) {
            // Ensure your DayOfTheWeekTrigger.java has this method!
            trigger.setDays(selectedDays);
        }

        Stage stage = (Stage) cbMonday.getScene().getWindow();
        stage.close();
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
}