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
     * This is where the Red X Button logic lives.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ObservableList<String> triggerOptions = FXCollections.observableArrayList("Temporal Trigger");
        ObservableList<String> actionOptions = FXCollections.observableArrayList("Message Action", "Audio Action");

        triggerDropDownMenu.setItems(triggerOptions);
        actionDropDownMenu.setItems(actionOptions);

        // Initialize Backend
        ruleEngine = new RuleEngine();

        // Initialize Frontend List
        ruleObservableList = FXCollections.observableArrayList();
        RuleList.setItems(ruleObservableList);

        // Custom Row Factory for Red X Button
        RuleList.setCellFactory(param -> new ListCell<Rule>() {
            @Override
            protected void updateItem(Rule item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    // 1. Create Layout Container
                    HBox hbox = new HBox(10);
                    hbox.setAlignment(Pos.CENTER_LEFT);

                    // 2. Create Rule Name Label
                    Label label = new Label(item.getName());
                    label.setStyle("-fx-text-fill: #333333; -fx-font-size: 13px;");

                    // 3. Create Spacer (pushes button to the right)
                    Pane spacer = new Pane();
                    HBox.setHgrow(spacer, Priority.ALWAYS);

                    // 4. Create the Red X Button
                    Button deleteBtn = new Button("X");
                    deleteBtn.setStyle(
                            "-fx-background-color: #E53935;" +
                                    "-fx-text-fill: white;" +
                                    "-fx-font-weight: bold;" +
                                    "-fx-cursor: hand;" +
                                    "-fx-background-radius: 15;" +
                                    "-fx-padding: 2 8 2 8;" +
                                    "-fx-font-size: 10px;"
                    );

                    // 5. Connect Button to Delete Logic
                    deleteBtn.setOnAction(event -> deleteSpecificRule(item));

                    // 5. Connect Button to Delete Logic
                    deleteBtn.setOnAction(event -> deleteSpecificRule(item));

                    // 6. Add all to row
                    hbox.getChildren().addAll(label, spacer, deleteBtn);
                    setText(null); // Clear default text
                    setGraphic(hbox); // Show custom layout
                }
            }
        });
        // ----------------------------------------------------
    }

    private <T> T openNewWindow(String fxmlPath, String title) {
        try {
            // Safety Check for FXML file
            URL fxmlLocation = getClass().getResource(fxmlPath);
            if (fxmlLocation == null) {
                // If in a different package, try adding a slash
                fxmlLocation = getClass().getResource("/" + fxmlPath);
            }
            if (fxmlLocation == null) {
                System.err.println("CRITICAL ERROR: Could not find FXML: " + fxmlPath);
                return null;
            }

            FXMLLoader loader = new FXMLLoader(fxmlLocation);
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle(title);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root));
            stage.showAndWait();
            return loader.getController();

        } catch (IOException e) {
            System.err.println("Errors in the GUI load: " + fxmlPath);
            e.printStackTrace();
            return null;
        }
    }

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
                WriteAMessageController wamcController = openNewWindow("WriteAMessage.fxml", "Select the message to show!");
                if(wamcController != null) {
                    this.chosenAction = wamcController.getFinalAction();
                }
                break;
            case("Audio Action"):
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Select Audio File");
                fileChooser.getExtensionFilters().addAll(
                        new FileChooser.ExtensionFilter("Audio Files", "*.wav", "*.mp3", "*.aac")
                );
                Stage stage = (Stage) createRuleButton.getScene().getWindow();
                File selectedFile = fileChooser.showOpenDialog(stage);

                if (selectedFile != null) {
                    this.chosenAction = new PlayAudioAction(selectedFile.getAbsolutePath());
                    System.out.println("Audio Action Configured: " + selectedFile.getName());
                } else {
                    System.err.println("No audio file selected!");
                    return;
                }
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