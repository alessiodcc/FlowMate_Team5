package flowmate_team5.controllers;

import flowmate_team5.models.triggers.DayOfTheYearTrigger;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.time.Month;
import java.util.ResourceBundle;

/**
 * Controller responsible for handling the UI that allows the user
 * to select a specific day of the year (month + day).
 */
public class SelectDayOfTheYearController implements Initializable {

    @FXML private ComboBox<Month> monthComboBox;

    @FXML private Spinner<Integer> daySpinner;

    private DayOfTheYearTrigger trigger;

    /**
     * Injects the DayOfTheYearTrigger from the MainPageController.
     * This controller does not create the trigger, it only configures it.
     */
    public void setTrigger(DayOfTheYearTrigger trigger) {
        this.trigger = trigger;
    }

    /**
     * Initializes the UI components after the FXML has been loaded.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        monthComboBox.setItems(
                FXCollections.observableArrayList(Month.values())
        );

        SpinnerValueFactory<Integer> dayFactory =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 31, 1);

        daySpinner.setValueFactory(dayFactory);
    }

    /**
     * Called when the user presses the confirm button.
     * Validates input and applies the selected values to the trigger.
     */
    @FXML
    private void confirmButtonPushed() {

        if (trigger == null) {
            throw new IllegalStateException("DayOfTheYearTrigger not injected");
        }

        Month selectedMonth = monthComboBox.getValue();
        Integer day = daySpinner.getValue();

        // Validate that both month and day are selected
        if (selectedMonth == null || day == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Validation Error");
            alert.setContentText("Please select both month and day.");
            alert.showAndWait();
            return;
        }

        trigger.setMonth(selectedMonth);
        trigger.setDayOfMonth(day);

        closeWindow();
    }

    /**
     * Called when the user presses the cancel button.
     * Simply closes the window without applying changes.
     */
    @FXML
    private void cancelButtonPushed() {
        Stage stage = (Stage) monthComboBox.getScene().getWindow();
        stage.close();
    }

    /**
     * Utility method to close the current window.
     */
    private void closeWindow() {
        Stage stage = (Stage) monthComboBox.getScene().getWindow();
        stage.close();
    }
}
