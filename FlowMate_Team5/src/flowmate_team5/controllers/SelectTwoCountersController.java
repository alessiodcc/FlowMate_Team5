package flowmate_team5.controllers;

import flowmate_team5.core.RuleEngine;
import flowmate_team5.models.Action;
import flowmate_team5.models.Counter;
import flowmate_team5.models.actions.AddCounterToCounterAction;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;

/**
 * Controller for SelectTwoCountersView.fxml (US21).
 * NOTE: The file name MUST be SelectTwoCountersController.java.
 */
public class SelectTwoCountersController {

    @FXML private ComboBox<Counter> sourceComboBox;
    @FXML private ComboBox<Counter> targetComboBox;

    private Action currentAction;

    @FXML
    public void initialize() {
        // 1. Get real data from RuleEngine
        if (RuleEngine.getInstance().getCounters() != null) {
            var list = FXCollections.observableArrayList(RuleEngine.getInstance().getCounters());
            sourceComboBox.setItems(list);
            targetComboBox.setItems(list);
        }
    }

    public void setAction(Action action) {
        this.currentAction = action;
    }

    @FXML
    private void handleConfirm() {
        Counter source = sourceComboBox.getValue();
        Counter target = targetComboBox.getValue();

        // 2. Validate input
        if (source == null || target == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Please select both counters.");
            alert.showAndWait();
            return;
        }

        // 3. Save data into the Action object
        if (currentAction instanceof AddCounterToCounterAction) {
            AddCounterToCounterAction specificAction = (AddCounterToCounterAction) currentAction;
            specificAction.setSourceCounter(source);
            specificAction.setTargetCounter(target);

            System.out.println("Saved Action: " + source.getName() + " -> " + target.getName());
        }

        // 4. Close window
        Stage stage = (Stage) sourceComboBox.getScene().getWindow();
        stage.close();
    }
}