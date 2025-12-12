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

        // Opzioni di Action aggiornate per includere Text Action
        ObservableList<String> actionOptions = FXCollections.observableArrayList(
                "Message Action",
                "Play Audio Action",
                "Write to Text File Action",
                "Copy File Action",
                "Delete File Action"
        );
        actionDropDownMenu.setItems(actionOptions);

        // Initialize Backend
        ruleEngine = RuleEngine.getInstance();
        ruleObservableList = FXCollections.observableArrayList(ruleEngine.getRules());

        // --- CUSTOM CELL FACTORY PER LA LISTA (Implementazione Active/Inactive) ---
        RuleList.setCellFactory(lv -> new ListCell<Rule>() {
            private final HBox hbox = new HBox(10);
            private final Label label = new Label();
            private final Pane pane = new Pane();
            private final CheckBox activeToggle = new CheckBox("Active"); // ELEMENTO PER LO STATO
            private final Button deleteButton = new Button("X");

            {
                // Gestione del click sulla CheckBox per attivare/disattivare la regola
                activeToggle.setOnAction(event -> {
                    Rule rule = getItem();
                    if (rule != null) {
                        rule.setActive(activeToggle.isSelected()); // Aggiorna lo stato nel Rule Object
                        // Log e aggiornamento visivo
                        System.out.println("[GUI] Rule '" + rule.getName() + "' set to " + (rule.isActive() ? "ACTIVE" : "INACTIVE"));
                        // Ricarica la cella per mostrare il nuovo indicatore (✅ / ❌)
                        updateItem(rule, false);
                    }
                });

                // Configurazione del pulsante Elimina (Delete)
                deleteButton.setOnAction(event -> {
                    Rule rule = getItem();
                    if (rule != null) {
                        deleteSpecificRule(rule);
                    }
                });
                deleteButton.setStyle("-fx-background-color: red; -fx-text-fill: white;");

                // Configurazione dell'HBox (layout)
                HBox.setHgrow(pane, Priority.ALWAYS); // Spinge gli elementi a destra
                hbox.setAlignment(Pos.CENTER_LEFT);
                hbox.getChildren().addAll(label, pane, activeToggle, deleteButton);
            }

            @Override
            protected void updateItem(Rule rule, boolean empty) {
                super.updateItem(rule, empty);

                if (empty || rule == null) {
                    setGraphic(null);
                    setText(null);
                } else{
                    // Aggiorna lo stato visivo della CheckBox in base allo stato della Rule
                    activeToggle.setSelected(rule.isActive());

                    // Aggiunge un'indicazione visiva dello stato al nome
                    String statusIndicator = rule.isActive() ? "✅" : "❌";
                    label.setText(statusIndicator + " " + rule.getName());

                    setGraphic(hbox);
                }
            }
        });
        // --- FINE CellFactory ---

        RuleList.setItems(ruleObservableList);
    }

    // --- LOGICA DI CREAZIONE REGOLA ---

    /**
     * Handles the creation of a new rule when the confirm button is pushed.
     * @param event The ActionEvent from the button press.
     */
    @FXML
    public void confirmButtonPushed(javafx.event.ActionEvent event) {
        String ruleName = RuleNameTextArea.getText();
        String selectedTrigger = triggerDropDownMenu.getValue();
        String selectedAction = actionDropDownMenu.getValue();

        if (ruleName == null || ruleName.trim().isEmpty()) {
            showAlert("ATTENTION!", "You must enter a name for the rule.", Alert.AlertType.ERROR);
            return;
        }

        if (selectedTrigger == null || selectedAction == null) {
            showAlert("ATTENTION!", "You must select both a Trigger and an Action.", Alert.AlertType.ERROR);
            return;
        }

        // 1. Configurazione del Trigger
        switch(selectedTrigger) {
            case("Temporal Trigger"):
                SelectTimeController stcController = openNewWindow("SelectTime.fxml", "Select the time for the trigger!");
                if(stcController != null) {
                    this.chosenTrigger = stcController.getFinalTrigger();
                }
                break;
            // Aggiungere altri Trigger qui (es. Location Trigger)
            case("Location Trigger"):
                showAlert("WIP", "Location Trigger is not yet implemented.", Alert.AlertType.INFORMATION);
                return; // Impedisce la creazione se non implementato
        }

        // 2. Configurazione dell'Action
        switch(selectedAction) {
            case("Message Action"):
                // Assumendo WriteAMessage.fxml esista
                Object wamcController = openNewWindow("WriteAMessage.fxml", "Select the message to show!");
                if(wamcController instanceof WriteAMessageController) {
                    this.chosenAction = ((WriteAMessageController)wamcController).getFinalAction();
                }
                break;
            case("Play Audio Action"):
                SelectAudioPathController sapcController = openNewWindow("SelectAudioPath.fxml", "Select the audio file path!");
                if(sapcController != null) {
                    this.chosenAction = sapcController.getFinalAction();
                }
                break;
            case("Write to Text File Action"):
                // Assumendo WriteToText.fxml e TextActionController esistano
                Object wttcController = openNewWindow("WriteToText.fxml", "Specify file path and message!");
                if(wttcController instanceof WriteOnFileController) {
                    this.chosenAction = ((WriteOnFileController)wttcController).getFinalAction();
                }
                break;
            case("Copy File Action"):
                CopyFileController cfc = openNewWindow("CopyFile.fxml", "Copy File Configuration");
                if (cfc != null) {
                    this.chosenAction = cfc.getFinalAction();
                }
                break;
            case("Delete File Action"):
                DeleteFileController dfcController = openNewWindow("DeleteFileView.fxml", "Select the file you want to delete!");
                if(dfcController != null)
                    this.chosenAction = dfcController.getFinalAction();
                break;
        }

        if (chosenTrigger == null || chosenAction == null) {
            System.err.println("ERRORE: La configurazione del Trigger o dell'Action è stata annullata.");
            return;
        }

        // 3. Creazione e Aggiunta della Regola
        Rule createdRule = new Rule(ruleName, chosenTrigger, chosenAction);
        ruleEngine.addRule(createdRule);
        ruleObservableList.add(createdRule);
        System.out.println("[GUI] Rule added: " + createdRule.getName() + " (Active: " + createdRule.isActive() + ")");

        // Reset
        this.chosenAction = null;
        this.chosenTrigger = null;
        RuleNameTextArea.clear();
    }

    // --- LOGICA DI CANCELLAZIONE REGOLA ---

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
            System.out.println("GUI: Deleted Rule: " + ruleToDelete.getName());
        }
    }

    // --- UTILITY METHODS ---

    /**
     * Utility method to open a new modal window for configuration.
     * @param fxmlPath Path to the FXML file.
     * @param title Title of the new window.
     * @return The controller of the new window, or null if loading fails or cancelled.
     */
    private <T> T openNewWindow(String fxmlPath, String title) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent parent = fxmlLoader.load();
            T controller = fxmlLoader.getController();

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle(title);
            stage.setScene(new Scene(parent));
            stage.showAndWait(); // Blocca l'esecuzione finché la finestra non viene chiusa

            return controller;

        } catch (IOException e) {
            showAlert("ERROR", "Failed to load configuration window: " + fxmlPath, Alert.AlertType.ERROR);
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Utility method to display alerts.
     */
    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}