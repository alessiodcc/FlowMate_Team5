package flowmate_team5;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.io.File;
import java.util.Optional;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 * Handles GUI interactions for the Main Page.
 */
public class MainPageController implements Initializable {

    @FXML
    private TextArea Title;
    @FXML
    private TextArea Introduction;
    @FXML
    private ComboBox<String> triggerDropDownMenu;
    @FXML
    private ComboBox<String> actionDropDownMenu;
    @FXML
    private Button createRuleButton;
    @FXML
    private TextField RuleNameTextArea;
    @FXML
    private ListView<Rule> RuleList;

    private Action chosenAction;
    private Trigger chosenTrigger;
    private RuleEngine ruleEngine;
    private ObservableList<Rule> ruleObservableList;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Initialize the available options for the ComboBoxes
        ObservableList<String> triggerOptions = FXCollections.observableArrayList("Temporal Trigger", "Location Trigger");
        triggerDropDownMenu.setItems(triggerOptions);

        ObservableList<String> actionOptions = FXCollections.observableArrayList("Message Action", "Play Audio Action", "Write to Text File Action");
        actionDropDownMenu.setItems(actionOptions);

        // Initialize Backend
        ruleEngine = RuleEngine.getInstance();
        ruleObservableList = FXCollections.observableArrayList(ruleEngine.getRules());

        RuleList.setCellFactory(lv -> new ListCell<Rule>() {
            private final HBox hbox = new HBox(10);
            private final Label label = new Label();
            private final Pane pane = new Pane();
            private final Button configSleepButton = new Button("Set Sleep");
            private final Button deleteButton = new Button("X");

            {
                // Configuration of the Sleep Button
                configSleepButton.setOnAction(event -> {
                    Rule rule = getItem();
                    if (rule != null) {
                        openSleepConfigurationWindow(rule);
                        updateItem(rule, false);
                    }
                });

                // Configuration of the Delete button
                deleteButton.setOnAction(event -> {
                    Rule rule = getItem();
                    if (rule != null) {
                        deleteSpecificRule(rule);
                    }
                });
                deleteButton.setStyle("-fx-background-color: red; -fx-text-fill: white;");

                HBox.setHgrow(pane, Priority.ALWAYS);
                hbox.setAlignment(Pos.CENTER_LEFT);
                hbox.getChildren().addAll(label, pane, configSleepButton, deleteButton);
            }

            @Override
            protected void updateItem(Rule rule, boolean empty) {
                super.updateItem(rule, empty);

                if (empty || rule == null) {
                    setGraphic(null);
                    setText(null);
                } else{
                    String status = rule.getSleepDurationMillis() == 0 ? " (One-Shot)" : String.format(" (Cooldown: %ds)", rule.getSleepDurationMillis() / 1000);
                    label.setText(rule.getName() + status);
                    setGraphic(hbox);
                }
            }
        });
        // --- FINE CellFactory ---

        RuleList.setItems(ruleObservableList);
    }

    /**
     * Helper function to open a new window (modal stage) for Trigger/Action configuration.
     * @param fxmlName The FXML file name of the window to open.
     * @param title The title of the modal window.
     * @return The controller instance of the newly opened window.
     */
    private <T> T openNewWindow(String fxmlName, String title) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlName));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle(title);
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(createRuleButton.getScene().getWindow());
            stage.showAndWait();

            return loader.getController();

        } catch (IOException e) {
            System.err.println("Errore nel caricamento della finestra modale: " + fxmlName);
            e.printStackTrace();
            return null;
        }
    }


    private void openSleepConfigurationWindow(Rule ruleToConfigure) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("SleepConfiguration.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Set Sleep Period for: " + ruleToConfigure.getName());
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(RuleList.getScene().getWindow());
            stage.showAndWait();

            //Refresh the UI
            RuleList.refresh();

        } catch (IOException e) {
            System.err.println("Error in loading the Sleep Window");
            e.printStackTrace();
        }
    }

    /**
     * Handles the creation of a new rule when the confirm button is pushed.
     * It initiates the configuration process for the chosen Action and Trigger.
     */
    @FXML
    public void confirmButtonPushed() {
        String selectedTrigger = triggerDropDownMenu.getValue();
        String selectedAction = actionDropDownMenu.getValue();
        String ruleName = RuleNameTextArea.getText();

        if (selectedTrigger == null || selectedAction == null || ruleName.trim().isEmpty()) {
            System.err.println("ERRORE: Devi selezionare Trigger, Action e inserire un nome.");
            return;
        }

        switch(selectedAction) {
            case("Message Action"):
                WriteAMessageController wamController = openNewWindow("WriteAMessage.fxml", "Write a message for the Message Action!");
                if(wamController != null) {
                    this.chosenAction = wamController.getFinalAction();
                }
                break;
            case("Play Audio Action"):
                SelectAudioPathController sapController = openNewWindow("SelectAudioPath.fxml", "Select the audio file for the Play Audio Action!");
                if(sapController != null) {
                    this.chosenAction = sapController.getFinalAction();
                }
                break;
            case("Write to Text File Action"):
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Select the Text File for the Write to Text File Action!");
                File selectedFile = fileChooser.showSaveDialog(createRuleButton.getScene().getWindow());
                break;
        }

        switch(selectedTrigger) {
            case("Temporal Trigger"):
                SelectTimeController stcController = openNewWindow("SelectTime.fxml", "Select the time for the trigger!");
                if(stcController != null) {
                    this.chosenTrigger = stcController.getFinalTrigger();
                }
                break;
        }

        if (chosenTrigger == null || chosenAction == null) {
            System.err.println("ERRORE: La configurazione del Trigger o dell'Action è stata annullata.");
            return;
        }

        Rule createdRule = new Rule(ruleName, chosenTrigger, chosenAction);

        ruleEngine.addRule(createdRule);
        ruleObservableList.add(createdRule);

        this.chosenAction = null;
        this.chosenTrigger = null;
        RuleNameTextArea.clear();
    }

    // --- LOGIC FOR RED X BUTTON ---
    private void deleteSpecificRule(Rule ruleToDelete) {
        if (ruleToDelete == null) return;

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Rule");
        alert.setHeaderText("Are you sure you want to delete this rule?");
        alert.setContentText("Rule: " + ruleToDelete.getName());

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            ruleEngine.deleteRule(ruleToDelete);
            ruleObservableList.remove(ruleToDelete);
            System.out.println("GUI: Deleted " + ruleToDelete.getName());
        }
    }
}