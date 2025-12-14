package flowmate_team5;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.stage.Stage;

import java.net.URL;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAmount;
import java.util.ResourceBundle;

public class SleepingStateController implements Initializable {
    @FXML
    private Spinner<Integer> daysSpinner;
    @FXML
    private Spinner<Integer> hoursSpinner;
    @FXML
    private Spinner<Integer> minutesSpinner;
    @FXML
    private Button confirmButton;

    private Duration sleepDuration;

    public Duration getSleepDuration() {
        return sleepDuration;
    }


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        SpinnerValueFactory<Integer> daysFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 365, 0);
        daysSpinner.setValueFactory(daysFactory);
        SpinnerValueFactory<Integer> hoursFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 23, 0);
        hoursSpinner.setValueFactory(hoursFactory);
        SpinnerValueFactory<Integer> minutesFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59, 0);
        minutesSpinner.setValueFactory(minutesFactory);
    }

    @FXML
    private void confirmButtonPushed(ActionEvent event) {
        Duration d = Duration.ofDays(daysSpinner.getValue()).plusHours(hoursSpinner.getValue()).plusMinutes(minutesSpinner.getValue());
        this.sleepDuration = d;
        Stage stage = (Stage) confirmButton.getScene().getWindow();
        stage.close();
    }
}