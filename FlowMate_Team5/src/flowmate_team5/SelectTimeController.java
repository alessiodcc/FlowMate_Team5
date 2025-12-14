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

    // Trigger instance received from the main controller (NOT created here)
    private TemporalTrigger trigger;

    public void setTrigger(TemporalTrigger trigger) {
        this.trigger = trigger;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Initialize dropdowns for hours (0-23) and minutes (0-59)
        populateTimeComboBoxes(HourDropDownMenu, 0, 23);
        populateTimeComboBoxes(MinutesDropDownMenu, 0, 59);
    }

    private void populateTimeComboBoxes(ComboBox<Integer> comboBox, int min, int max) {
        ObservableList<Integer> options = FXCollections.observableArrayList();
        for (int i = min; i <= max; i++) {
            options.add(i);
        }
        comboBox.setItems(options);

        // Set cell factory to format numbers with leading zeros (e.g., 05)
        comboBox.setCellFactory(lv -> new javafx.scene.control.ListCell<>() {
            @Override
            protected void updateItem(Integer item, boolean empty) {
                super.updateItem(item, empty);
                setText(item == null || empty ? null : String.format("%02d", item));
            }
        });

        // Ensure the selected value button also uses the formatted view
        comboBox.setButtonCell(comboBox.getCellFactory().call(null));
    }

    @FXML
    public void confirmButtonPushed(ActionEvent event) {

        Integer selectedHour = HourDropDownMenu.getValue();
        Integer selectedMinutes = MinutesDropDownMenu.getValue();

        // Validate input
        if (selectedHour == null || selectedMinutes == null) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("ATTENTION!");
            alert.setHeaderText(
                    "To continue, it's necessary that you select both the hour and the minute!"
            );
            alert.showAndWait();
            return;
        }

        LocalTime selectedTime = LocalTime.of(selectedHour, selectedMinutes);

        // ONLY configuration logic
        trigger.setTriggerName("TemporalTrigger1");
        trigger.setTimeToTrigger(selectedTime);

        // Close the window
        Stage stage = (Stage) ((Node) event.getSource())
                .getScene()
                .getWindow();
        stage.close();
    }
}