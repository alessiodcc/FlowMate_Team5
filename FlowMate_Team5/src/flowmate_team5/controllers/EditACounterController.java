package flowmate_team5.controllers;

import flowmate_team5.models.Counter;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class EditACounterController {

    @FXML private TextField nameField;
    @FXML private TextField valueField;

    private Counter counter;

    // 🔑 chiamato dalla MainPageController
    public void setCounter(Counter counter) {
        this.counter = counter;

        // Pre-carica i dati
        nameField.setText(counter.getName());
        valueField.setText(String.valueOf(counter.getValue()));
    }

    // 🔑 QUESTO METODO MANCAVA
    @FXML
    private void confirmButtonPushed() {

        String newName = nameField.getText();
        String valueText = valueField.getText();

        if (newName == null || newName.isBlank()) {
            showError("Counter name cannot be empty");
            return;
        }

        double newValue;
        try {
            newValue = Double.parseDouble(valueText);
        } catch (NumberFormatException e) {
            showError("Counter value must be a number");
            return;
        }

        // Aggiorna il counter ESISTENTE
        counter.setName(newName.trim());
        counter.setValue(newValue);

        // Chiudi finestra
        Stage stage = (Stage) nameField.getScene().getWindow();
        stage.close();
    }

    private void showError(String msg) {
        Alert a = new Alert(Alert.AlertType.ERROR);
        a.setTitle("Invalid Input");
        a.setHeaderText(null);
        a.setContentText(msg);
        a.showAndWait();
    }
}
