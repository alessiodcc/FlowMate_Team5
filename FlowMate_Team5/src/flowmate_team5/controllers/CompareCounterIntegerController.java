package flowmate_team5.controllers;

import flowmate_team5.core.RuleEngine;
import flowmate_team5.models.Counter;
import flowmate_team5.models.triggers.CounterIntegerComparisonTrigger;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class CompareCounterIntegerController implements Initializable {

    @FXML private ComboBox<Counter> counterComboBox;
    @FXML private ComboBox<String> operatorComboBox;
    @FXML private TextField valueField;

    // The trigger logic object (Alessio's task)
    private CounterIntegerComparisonTrigger trigger;

    public void setTrigger(CounterIntegerComparisonTrigger trigger) {
        this.trigger = trigger;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // 1. Setup Operators
        operatorComboBox.setItems(FXCollections.observableArrayList(
                ">", "<", ">=", "<=", "==", "!="
        ));

        // 2. Setup Counters (Using the list you added to RuleEngine)
        if (RuleEngine.getInstance().getCounters() != null) {
            counterComboBox.setItems(FXCollections.observableArrayList(
                    RuleEngine.getInstance().getCounters()
            ));
        }
    }

    @FXML
    public void handleConfirm() {
        Counter selectedCounter = counterComboBox.getValue();
        String selectedOp = operatorComboBox.getValue();
        String valueText = valueField.getText();

        // Validation
        if (selectedCounter == null || selectedOp == null || valueText.isBlank()) {
            showAlert("Missing Info", "Please fill in all fields.");
            return;
        }

        try {
            int value = Integer.parseInt(valueText);

            // Configure Trigger
            if (trigger != null) {
                trigger.setCounter(selectedCounter);
                trigger.setComparator(selectedOp);
                trigger.setIntValue(value);
            }

            // Close Window
            Stage stage = (Stage) valueField.getScene().getWindow();
            stage.close();

        } catch (NumberFormatException e) {
            showAlert("Invalid Input", "Please enter a valid integer number (e.g. 10, 500).");
        }
    }

    private void showAlert(String header, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
}