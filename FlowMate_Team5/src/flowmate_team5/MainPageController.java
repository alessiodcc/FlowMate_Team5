package flowmate_team5;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Optional;

import javafx.animation.TranslateTransition;
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
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

public class MainPageController implements Initializable {

    // --- UI ---
    @FXML private Label Title;
    @FXML private Label Introduction;
    @FXML private ComboBox<String> triggerDropDownMenu;
    @FXML private ComboBox<String> actionDropDownMenu;
    @FXML private Button createRuleButton;
    @FXML private TextField RuleNameTextArea;
    @FXML private ListView<Rule> RuleList;

    // --- SIDEBAR ---
    @FXML private AnchorPane sidebar;

    // --- BACKEND ---
    private Action chosenAction;
    private Trigger chosenTrigger;
    private RuleEngine ruleEngine;
    private ObservableList<Rule> ruleObservableList;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        triggerDropDownMenu.setItems(FXCollections.observableArrayList(
                "Temporal Trigger",
                "Location Trigger",
                "File Exists Trigger"
        ));

        actionDropDownMenu.setItems(FXCollections.observableArrayList(
                "Message Action",
                "Play Audio Action",
                "Write to Text File Action",
                "Copy File Action",
                "Delete File Action",
                "Move File Action"
        ));

        ruleEngine = RuleEngine.getInstance();
        ruleObservableList = FXCollections.observableArrayList(ruleEngine.getRules());

        RuleList.setCellFactory(lv -> new ListCell<>() {

            private final HBox hbox = new HBox(10);
            private final Label label = new Label();
            private final Pane pane = new Pane();
            private final CheckBox activeToggle = new CheckBox("Active");
            private final Button deleteButton = new Button("X");

            {
                activeToggle.setOnAction(e -> {
                    Rule r = getItem();
                    if (r != null) {
                        r.setActive(activeToggle.isSelected());
                        updateItem(r, false);
                    }
                });

                deleteButton.setOnAction(e -> deleteSpecificRule(getItem()));
                deleteButton.setStyle("-fx-background-color:red; -fx-text-fill:white;");

                HBox.setHgrow(pane, Priority.ALWAYS);
                hbox.setAlignment(Pos.CENTER_LEFT);
                hbox.getChildren().addAll(label, pane, activeToggle, deleteButton);
            }

            @Override
            protected void updateItem(Rule rule, boolean empty) {
                super.updateItem(rule, empty);
                if (empty || rule == null) {
                    setGraphic(null);
                } else {
                    activeToggle.setSelected(rule.isActive());
                    label.setText((rule.isActive() ? "✅ " : "❌ ") + rule.getName());
                    setGraphic(hbox);
                }
            }
        });

        RuleList.setItems(ruleObservableList);

        // Sidebar nascosta
        sidebar.setVisible(false);
        sidebar.setManaged(false);
        sidebar.setTranslateX(-320);
    }

    // -------- SIDEBAR --------
    @FXML
    private void toggleSidebar() {
        boolean visible = sidebar.isVisible();

        TranslateTransition tt =
                new TranslateTransition(Duration.millis(250), sidebar);

        if (!visible) {
            sidebar.setManaged(true);
            sidebar.setVisible(true);
            tt.setFromX(-320);
            tt.setToX(0);
        } else {
            tt.setFromX(0);
            tt.setToX(-320);
            tt.setOnFinished(e -> {
                sidebar.setVisible(false);
                sidebar.setManaged(false);
            });
        }
        tt.play();
    }

    // -------- CREAZIONE REGOLA --------
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

        switch (selectedTrigger) {
            case "Temporal Trigger":
                SelectTimeController stc =
                        openNewWindow("SelectTime.fxml", "Select the time for the trigger!");
                if (stc != null) chosenTrigger = stc.getFinalTrigger();
                break;
            case("File Exists Trigger"):
                FileExistsController fileController = openNewWindow("FileExistsView.fxml", "Configure File Trigger");
                if (fileController != null) {this.chosenTrigger = fileController.getFinalTrigger();}
                break;
            case "Location Trigger":
                showAlert("WIP", "Location Trigger is not yet implemented.", Alert.AlertType.INFORMATION);
                return;

        }

        switch (selectedAction) {
            case "Message Action":
                WriteAMessageController wamc =
                        openNewWindow("WriteAMessage.fxml", "Select the message to show!");
                if (wamc != null) chosenAction = wamc.getFinalAction();
                break;
            case "Play Audio Action":
                SelectAudioPathController sapc =
                        openNewWindow("SelectAudioPath.fxml", "Select the audio file path!");
                if (sapc != null) chosenAction = sapc.getFinalAction();
                break;
            case "Write to Text File Action":
                WriteOnFileController wofc =
                        openNewWindow("WriteOnFile.fxml", "Specify file path and message!");
                if (wofc != null) chosenAction = wofc.getFinalAction();
                break;
            case "Copy File Action":
                showAlert("WIP", "Copy File Action is not yet implemented.", Alert.AlertType.INFORMATION);
                return;
            case "Delete File Action":
                DeleteFileController dfc =
                        openNewWindow("DeleteFileView.fxml", "Select the file you want to delete!");
                if (dfc != null) chosenAction = dfc.getFinalAction();
                break;
            case("Move File Action"):
                MoveFileController moveController = openNewWindow("MoveFileView.fxml", "Configure Move File");
                if(moveController != null) {this.chosenAction = moveController.getFinalAction();}
                break;
        }

        if (chosenTrigger == null || chosenAction == null) return;

        Rule createdRule = new Rule(ruleName, chosenTrigger, chosenAction);
        ruleEngine.addRule(createdRule);
        ruleObservableList.add(createdRule);

        chosenTrigger = null;
        chosenAction = null;
        RuleNameTextArea.clear();
    }

    // -------- DELETE --------
    private void deleteSpecificRule(Rule ruleToDelete) {
        if (ruleToDelete == null) return;

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Rule");
        alert.setHeaderText("Are you sure?");
        alert.setContentText(ruleToDelete.getName());

        Optional<ButtonType> res = alert.showAndWait();
        if (res.isPresent() && res.get() == ButtonType.OK) {
            ruleEngine.deleteRule(ruleToDelete);
            ruleObservableList.remove(ruleToDelete);
        }
    }

    // -------- WINDOW UTILITY --------
    private <T> T openNewWindow(String fxmlPath, String title) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();
            T controller = loader.getController();

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle(title);
            stage.setScene(new Scene(root));
            stage.showAndWait();

            return controller;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
