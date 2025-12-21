package flowmate_team5.controllers;

import flowmate_team5.models.Counter;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * Controller responsible for editing an existing Counter.
 * It allows the user to modify the counter name and value.
 */
public class EditACounterController {

    @FXML private TextField nameField;

    @FXML private TextField valueField;

    private Counter counter;

    /**
     * Injects the Counter from the MainPageController
     * and initializes the UI fields with its current values.
     *
     * @param counter the counter to be edited
     */
    public void setCounter(Counter counter) {
        this.counter = counter;

        nameField.setText(counter.getName());
        valueField.setText(String.valueOf(counter.getValue()));
    }

    /**
     * Called when the user presses the confirm button.
     * Validates input and updates the existing counter.
     */
    @FXML
    private void confirmButtonPushed() {

        if (counter == null) {
            throw new IllegalStateException("Counter not injected");
        }

        String name = nameField.getText();
        String valueText = valueField.getText();

        // Validate counter name
        if (name == null || name.isBlank()) {
            showError("Counter name cannot be empty");
            return;
        }

        // Validate and parse counter value
        int value;
        try {
            value = Integer.parseInt(valueText);
        } catch (NumberFormatException e) {
            showError("Counter value must be an integer number");
            return;
        }

        // Update the existing counter
        counter.setName(name);
        counter.setValue(value);

        // Close the window after successful update
        closeWindow();
    }

    /**
     * Utility method to close the current window.
     */
    private void closeWindow() {
        Stage stage = (Stage) nameField.getScene().getWindow();
        stage.close();
    }

    /**
     * Displays a validation error message in an alert dialog.
     *
     * @param msg the error message to be shown
     */
    private void showError(String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Validation Error");
        alert.setContentText(msg);
        alert.showAndWait();
    }
}
