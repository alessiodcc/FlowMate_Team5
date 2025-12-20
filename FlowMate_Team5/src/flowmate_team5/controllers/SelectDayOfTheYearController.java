package flowmate_team5.controllers;

import flowmate_team5.models.triggers.DayOfTheYearTrigger;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.time.Month;
import java.util.ResourceBundle;

public class SelectDayOfTheYearController implements Initializable {

    @FXML private ComboBox<Month> monthComboBox;
    @FXML private Spinner<Integer> daySpinner;

    private DayOfTheYearTrigger trigger;

    // 🔑 injection dal MainPageController
    public void setTrigger(DayOfTheYearTrigger trigger) {
        this.trigger = trigger;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        monthComboBox.setItems(
                FXCollections.observableArrayList(Month.values())
        );

        SpinnerValueFactory<Integer> dayFactory =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 31, 1);

        daySpinner.setValueFactory(dayFactory);
    }

    @FXML
    private void confirmButtonPushed() {

        if (trigger == null) {
            throw new IllegalStateException("DayOfTheYearTrigger not injected");
        }

        Month selectedMonth = monthComboBox.getValue();
        Integer day = daySpinner.getValue();

        if (selectedMonth == null || day == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Validation Error");
            alert.setContentText("Please select both month and day.");
            alert.showAndWait();
            return;
        }

        // ✅ CONFIGURAZIONE, NON CREAZIONE
        trigger.setMonth(selectedMonth);
        trigger.setDayOfMonth(day);

        closeWindow();
    }

    @FXML
    private void cancelButtonPushed() {
        closeWindow();
    }

    private void closeWindow() {
        Stage stage = (Stage) monthComboBox.getScene().getWindow();
        stage.close();
    }
}
