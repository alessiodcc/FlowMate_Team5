package flowmate_team5;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.control.ListView;

/**
 * FXML Controller class
 *
 * @author Principale
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
    // Uses the Singleton pattern now
    private RuleEngine ruleEngine;
    private ObservableList<Rule> ruleObservableList;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ObservableList<String> triggerOptions = FXCollections.observableArrayList(
                "Temporal Trigger"
        );

        ObservableList<String> actionOptions = FXCollections.observableArrayList(
                "Message Action",
                "Audio Action"
        );

        triggerDropDownMenu.setItems(triggerOptions);
        actionDropDownMenu.setItems(actionOptions);

        // Get the single instance of the RuleEngine
        ruleEngine = RuleEngine.getInstance();

        ruleObservableList = FXCollections.observableArrayList();
        RuleList.setItems(ruleObservableList);
    }

    /**
     * Helper method to open a new modal window and return its controller.
     * @param fxmlPath The path to the FXML file.
     * @param title The title for the new stage.
     * @return The controller of the new window, or null on error.
     */
    private <T> T openNewWindow(String fxmlPath, String title) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath)); // Loading the GUI we want to switch to
            Parent root = loader.load();

            Stage stage = new Stage(); // Creating a new stage
            stage.setTitle(title);

            stage.initModality(Modality.APPLICATION_MODAL); // So the previous GUI doesn't automatically close

            stage.setScene(new Scene(root));
            stage.showAndWait(); // Show the new GUI
            return loader.getController();

        } catch (IOException e) {
            System.err.println("Errors in the GUI load: " + fxmlPath);
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Handles the button push to create and configure a new Rule.
     */
    @FXML
    public void confirmButtonPushed() {
        String selectedTrigger = triggerDropDownMenu.getValue();
        System.out.println(selectedTrigger);
        String selectedAction = actionDropDownMenu.getValue();
        String ruleName = RuleNameTextArea.getText();

        // Input validation for selection and name.
        if (selectedTrigger == null || selectedAction == null || ruleName.trim().isEmpty()) {
            System.err.println("ERROR: You must select a Trigger, an Action, and enter a name.");
            return;
        }

        // Configuration of the selected Action
        switch(selectedAction) {
            case("Message Action"):
                WriteAMessageController wamcController = openNewWindow("WriteAMessage.fxml", "Select the message to show!");
                if(wamcController != null) {
                    this.chosenAction = wamcController.getFinalAction();
                }
                break;
            case("Audio Action"):
                SelectAudioPathController sapcController = openNewWindow("SelectAudioPath.fxml", "Select the audio file path!");
                if(sapcController != null) {
                    this.chosenAction = sapcController.getFinalAction();
                }
                break;
        }

        // Configuration of the selected Trigger
        switch(selectedTrigger) {
            case("Temporal Trigger"):
                SelectTimeController stcController = openNewWindow("SelectTime.fxml", "Select the time for the trigger!");
                if(stcController != null) {
                    this.chosenTrigger = stcController.getFinalTrigger();
                }
                break;
        }

        // Validation after modal configuration
        if (chosenTrigger == null || chosenAction == null) {
            System.err.println("ERROR: The Trigger or Action configuration was cancelled or not completed.");
            return;
        }

        // Rule creation and registration with the engine
        Rule createdRule = new Rule(ruleName, chosenTrigger, chosenAction);
        ruleEngine.addRule(createdRule);
        ruleObservableList.add(createdRule);

        // Reset state for the next rule creation
        this.chosenAction = null;
        this.chosenTrigger = null;
        RuleNameTextArea.clear();
    }
}