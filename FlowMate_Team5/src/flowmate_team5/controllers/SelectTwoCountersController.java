package flowmate_team5.controllers;

import flowmate_team5.models.Action;
import flowmate_team5.models.Counter;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;

/* Controller class responsible for handling the selection of two counters for an operation. */
public class SelectTwoCountersController {

    @FXML private ComboBox<Counter> sourceComboBox;
    @FXML private ComboBox<Counter> targetComboBox;

    // The action instance to be configured
    private Action currentAction;

    /* Initializes the view components. */
    @FXML
    public void initialize() {
        // TODO: Populate lists with counters from RuleEngine
        // Example: sourceComboBox.getItems().addAll(RuleEngine.getInstance().getCounters());
    }

    /* Injects the action instance to be configured by the user. */
    public void setAction(Action action) {
        this.currentAction = action;
    }

    /* Validates the selection and initiates the saving process. */
    @FXML
    private void handleConfirm() {
        Counter source = sourceComboBox.getValue();
        Counter target = targetComboBox.getValue();

        // Ensure both counters are selected before proceeding
        if (source == null || target == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Please select both counters.");
            alert.showAndWait();
            return;
        }

        // TODO: Cast currentAction to AddCounterToCounterAction and save data
        // if (currentAction instanceof AddCounterToCounterAction) { ... }

        // Close the current window
        Stage stage = (Stage) sourceComboBox.getScene().getWindow();
        stage.close();
    }
}