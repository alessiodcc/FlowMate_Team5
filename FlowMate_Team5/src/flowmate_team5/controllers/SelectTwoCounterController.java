package flowmate_team5.controllers;

import flowmate_team5.models.Counter;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;

public class SelectTwoCounterController {

    @FXML
    private ComboBox<Counter> counterACombo;

    @FXML
    private ComboBox<Counter> counterBCombo;

    @FXML
    public void initialize() {
        // TODO: Populate lists when CounterManager is available
    }

    @FXML
    private void handleConfirm() {
        Counter c1 = counterACombo.getValue();
        Counter c2 = counterBCombo.getValue();

        if (c1 != null && c2 != null) {
            Stage stage = (Stage) counterACombo.getScene().getWindow();
            stage.close();
        }
    }
}