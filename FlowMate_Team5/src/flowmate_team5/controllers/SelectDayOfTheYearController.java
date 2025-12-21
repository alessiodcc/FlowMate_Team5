package flowmate_team5.controllers;

import flowmate_team5.models.triggers.DayOfTheYearTrigger;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.time.Month;
import java.time.Year;
import java.util.ResourceBundle;

/**
 * Controller responsible for handling the UI that allows the user
 * to select a specific date (year + month + day).
 */
public class SelectDayOfTheYearController implements Initializable {

    @FXML private ComboBox<Month> monthComboBox;
    @FXML private Spinner<Integer> daySpinner;
    @FXML private Spinner<Integer> yearSpinner;

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

        // Months
        monthComboBox.setItems(
                FXCollections.observableArrayList(Month.values())
        );

        // Day (1–31, validated logically later)
        daySpinner.setValueFactory(
                new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 31, 1)
        );

        // Year (current year ± 20, very reasonable UX)
        int currentYear = Year.now().getValue();
        yearSpinner.setValueFactory(
                new SpinnerValueFactory.IntegerSpinnerValueFactory(
                        currentYear - 20,
                        currentYear + 20,
                        currentYear
                )
        );
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
        Integer year = yearSpinner.getValue();

        if (selectedMonth == null || day == null || year == null) {
            showError("Please select year, month and day.");
            return;
        }

        // Basic validation (avoid Feb 30 etc.)
        if (!isValidDate(year, selectedMonth, day)) {
            showError("The selected date is not valid.");
            return;
        }

        // Apply configuration to trigger
        trigger.setYear(year);
        trigger.setMonth(selectedMonth);
        trigger.setDayOfMonth(day);

        closeWindow();
    }

    /**
     * Called when the user presses the cancel button.
     */
    @FXML
    private void cancelButtonPushed() {
        closeWindow();
    }

    // -------------------- UTILITIES --------------------

    private boolean isValidDate(int year, Month month, int day) {
        try {
            java.time.LocalDate.of(year, month, day);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Validation Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void closeWindow() {
        Stage stage = (Stage) monthComboBox.getScene().getWindow();
        stage.close();
    }
}
