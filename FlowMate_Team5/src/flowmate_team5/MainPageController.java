/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
import javafx.stage.FileChooser;
import java.io.File;

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
        ruleEngine = new RuleEngine();

        ruleObservableList = FXCollections.observableArrayList();
        RuleList.setItems(ruleObservableList);
    }

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

    @FXML
    public void confirmButtonPushed() {
        String selectedTrigger = triggerDropDownMenu.getValue();
        System.out.println(selectedTrigger);
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
                // 1. Create a File Chooser to let user pick a song
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Select Audio File");

                // 2. Filter for audio files only
                fileChooser.getExtensionFilters().addAll(
                        new FileChooser.ExtensionFilter("Audio Files", "*.wav", "*.mp3", "*.aac")
                );

                // 3. Show the window (Get the stage from the button)
                Stage stage = (Stage) createRuleButton.getScene().getWindow();
                File selectedFile = fileChooser.showOpenDialog(stage);

                // 4. If user picked a file, CONNECT it (Task 4.4)
                if (selectedFile != null) {
                    this.chosenAction = new PlayAudioAction(selectedFile.getAbsolutePath());
                    System.out.println("Audio Action Configured: " + selectedFile.getName());
                } else {
                    System.err.println("No audio file selected!");
                    return; // Stop creation if no file is picked
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
            System.err.println("ERRORE: La configurazione del Trigger o dell'Action è stata annullata o non è stata completata.");
            return;
        }

        Rule createdRule = new Rule(ruleName, chosenTrigger, chosenAction);
        ruleEngine.addRule(createdRule);
        ruleObservableList.add(createdRule);

        this.chosenAction = null;
        this.chosenTrigger = null;
        RuleNameTextArea.clear();
    }

}