package flowmate_team5.controllers;

import flowmate_team5.models.triggers.FileExistsTrigger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalTime;
import java.util.ResourceBundle;
import java.util.logging.Logger;

public class FileExistsController implements Initializable {

    private static final Logger LOGGER =
            Logger.getLogger(FileExistsController.class.getName());

    @FXML
    private TextField folderPathField;

    @FXML
    private TextField fileNameField;

    @FXML
    private ComboBox<Integer> HourDropDownMenu;

    @FXML
    private ComboBox<Integer> MinutesDropDownMenu;

    // Trigger instance received from the main controller (NOT created here)
    private FileExistsTrigger trigger;

    public void setTrigger(FileExistsTrigger trigger) {
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
    }

    @FXML
    public void handleBrowseFolder() {

        DirectoryChooser dirChooser = new DirectoryChooser();
        dirChooser.setTitle("Select Folder to Watch");

        Stage stage = (Stage) folderPathField.getScene().getWindow();
        File selectedDir = dirChooser.showDialog(stage);

        if (selectedDir != null) {
            folderPathField.setText(selectedDir.getAbsolutePath());
        }
    }

    @FXML
    public void handleConfirm() {
        Integer selectedHour = HourDropDownMenu.getValue();
        Integer selectedMinutes = MinutesDropDownMenu.getValue();

        // Validate input
        if (selectedHour == null || selectedMinutes == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ATTENTION!");
            alert.setHeaderText(
                    "To continue, it's necessary that you select both the hour and the minute!"
            );
            alert.showAndWait();
            return;
        }

        LocalTime selectedTime = LocalTime.of(selectedHour, selectedMinutes);
        trigger.setTimeToTrigger(selectedTime);

        String folder = folderPathField.getText();
        String file = fileNameField.getText();

        // Validate inputs
        if (folder == null || folder.isEmpty() ||
                file == null || file.trim().isEmpty()) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Missing Information");
            alert.setContentText(
                    "Please select a folder and enter a filename."
            );
            alert.showAndWait();
            return;
        }

        // ONLY configuration logic
        Path folderPath = Paths.get(folder);
        trigger.setFolderPath(folderPath);
        trigger.setFileName(file.trim());

        LOGGER.info("Trigger configured for file: " + file);

        Stage stage = (Stage) folderPathField.getScene().getWindow();
        stage.close();
    }
}