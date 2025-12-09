/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package flowmate_team5;

import java.net.URL;
import java.time.LocalTime;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Alessio
 */
public class SelectTimeController implements Initializable {

    @FXML
    private TextArea Title;
    @FXML
    private TextArea Introduction;
    @FXML
    private ComboBox<Integer> HourDropDownMenu;
    @FXML
    private ComboBox<Integer> MinutesDropDownMenu;
    @FXML
    private Button confirmButton;
    private TemporalTrigger finalTrigger;

    public TemporalTrigger getFinalTrigger() {
        return finalTrigger;
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Population of the ComboBoxes using a auxiliary method
        populateTimeComboBoxes(HourDropDownMenu, 0, 23);
        populateTimeComboBoxes(MinutesDropDownMenu, 0, 59);
    }

    // Auxiliar method to customize the ComboBoxes for both the hours and the minutes
    private void populateTimeComboBoxes(ComboBox<Integer> comboBox, int min, int max) {
        ObservableList<Integer> options = FXCollections.observableArrayList();
        for (int i = min; i <= max; i++) {
            options.add(i);
        }
        comboBox.setItems(options);

        comboBox.setCellFactory(lv -> new javafx.scene.control.ListCell<Integer>() {
            @Override
            protected void updateItem(Integer item, boolean empty) {
                super.updateItem(item, empty);
                setText(item == null || empty ? null : String.format("%02d", item));
            }
        });

        comboBox.setButtonCell(comboBox.getCellFactory().call(null));
    }

    @FXML
    public void confirmButtonPushed(ActionEvent event) {
        Integer selectedHour = HourDropDownMenu.getValue();
        Integer selectedMinutes = MinutesDropDownMenu.getValue();

        if (selectedHour == null || selectedMinutes == null) {
            Alert emptyAlert = new Alert(AlertType.ERROR);
            emptyAlert.setTitle("ATTENTION!");
            emptyAlert.setHeaderText("To continue, it's necessary that you select both the hour and the minute!");

            emptyAlert.showAndWait();
            return;
        }


        LocalTime selectedTime = LocalTime.of(selectedHour, selectedMinutes);

        this.finalTrigger = new TemporalTrigger("TemporalTrigger1", selectedTime);

        Node source = (Node) event.getSource();

        Stage stage = (Stage) source.getScene().getWindow();

        stage.close();

    }
}
