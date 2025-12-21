package flowmate_team5.controllers;

import flowmate_team5.models.Action;
import flowmate_team5.models.Counter;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;

/**
 * Controller for SelectTwoCountersView.fxml (US21).
 * NOTE: The file name MUST be SelectTwoCountersController.java (Plural).
 */
public class SelectTwoCountersController {

    @FXML private ComboBox<Counter> sourceComboBox;
    @FXML private ComboBox<Counter> targetComboBox;

    private Action currentAction;

    @FXML
    public void initialize() {
        // TODO: Populate lists with counters from RuleEngine
        // Example: sourceComboBox.getItems().addAll(RuleEngine.getInstance().getCounters());
    }

    public void setAction(Action action) {
        this.currentAction = action;
    }

    @FXML
    private void handleConfirm() {
        Counter source = sourceComboBox.getValue();
        Counter target = targetComboBox.getValue();

        if (source == null || target == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Please select both counters.");
            alert.showAndWait();
            return;
        }

        // TODO: Cast currentAction to AddCounterToCounterAction and save data
        // if (currentAction instanceof AddCounterToCounterAction) { ... }

        Stage stage = (Stage) sourceComboBox.getScene().getWindow();
        stage.close();
    }
}