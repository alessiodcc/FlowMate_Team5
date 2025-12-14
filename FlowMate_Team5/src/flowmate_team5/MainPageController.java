package flowmate_team5;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

public class MainPageController implements Initializable {

    // ---------- UI ----------
    @FXML private Label Title;
    @FXML private Label Introduction;
    @FXML private ComboBox<String> triggerDropDownMenu;
    @FXML private ComboBox<String> actionDropDownMenu;
    @FXML private Button createRuleButton;
    @FXML private TextField RuleNameTextArea;
    @FXML private ListView<Rule> RuleList;

    // ---------- SIDEBAR ----------
    @FXML private AnchorPane sidebar;

    // ---------- BACKEND ----------
    private RuleEngine ruleEngine;
    private ObservableList<Rule> ruleObservableList;
    private Action chosenAction;
    private Trigger chosenTrigger;

    // ======================================================
    // INITIALIZE
    // ======================================================
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        triggerDropDownMenu.setItems(FXCollections.observableArrayList(
                "Temporal Trigger",
                "Location Trigger"
        ));

        actionDropDownMenu.setItems(FXCollections.observableArrayList(
                "Message Action",
                "Play Audio Action",
                "Write to Text File Action",
                "Copy File Action",
                "Delete File Action"
        ));

        ruleEngine = RuleEngine.getInstance();
        ruleObservableList =
                FXCollections.observableArrayList(ruleEngine.getRules());

        RuleList.setItems(ruleObservableList);
        RuleList.setCellFactory(lv -> new RuleCell());

        sidebar.setVisible(false);
        sidebar.setManaged(false);
        sidebar.setTranslateX(-320);
    }

    // ======================================================
    // SIDEBAR TOGGLE
    // ======================================================
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

    // ======================================================
    // CREATE RULE (IDENTICA ALLA TUA)
    // ======================================================
    @FXML
    public void confirmButtonPushed(javafx.event.ActionEvent event) {

        String ruleName = RuleNameTextArea.getText();
        String selectedTrigger = triggerDropDownMenu.getValue();
        String selectedAction = actionDropDownMenu.getValue();

        if (ruleName == null || ruleName.isBlank()) {
            showAlert("Error", "Rule name required", Alert.AlertType.ERROR);
            return;
        }

        if (selectedTrigger == null || selectedAction == null) {
            showAlert("Error", "Select trigger and action", Alert.AlertType.ERROR);
            return;
        }

        switch (selectedTrigger) {
            case "Temporal Trigger":
                SelectTimeController stc =
                        openNewWindow("SelectTime.fxml", "Select time");
                if (stc != null) chosenTrigger = stc.getFinalTrigger();
                break;

            case "Location Trigger":
                showAlert("WIP", "Location trigger not implemented",
                        Alert.AlertType.INFORMATION);
                return;
        }

        switch (selectedAction) {
            case "Message Action":
                WriteAMessageController wamc =
                        openNewWindow("WriteAMessage.fxml", "Message");
                if (wamc != null) chosenAction = wamc.getFinalAction();
                break;

            case "Play Audio Action":
                SelectAudioPathController sapc =
                        openNewWindow("SelectAudioPath.fxml", "Audio");
                if (sapc != null) chosenAction = sapc.getFinalAction();
                break;

            case "Write to Text File Action":
                WriteOnFileController wofc =
                        openNewWindow("WriteOnFile.fxml", "Write to file");
                if (wofc != null) chosenAction = wofc.getFinalAction();
                break;

            case "Delete File Action":
                DeleteFileController dfc =
                        openNewWindow("DeleteFileView.fxml", "Delete file");
                if (dfc != null) chosenAction = dfc.getFinalAction();
                break;

            default:
                showAlert("WIP", "Action not implemented",
                        Alert.AlertType.INFORMATION);
                return;
        }

        if (chosenTrigger == null || chosenAction == null) return;

        Rule rule = new Rule(ruleName, chosenTrigger, chosenAction);
        ruleEngine.addRule(rule);
        ruleObservableList.add(rule);

        RuleNameTextArea.clear();
        chosenTrigger = null;
        chosenAction = null;
    }

    // ======================================================
    // CUSTOM RULE CELL (SIDEBAR)
    // ======================================================
    private class RuleCell extends ListCell<Rule> {

        private final VBox root = new VBox(6);

        // titolo
        private final Circle statusDot = new Circle(6);
        private final Label nameLabel = new Label();
        private final HBox titleBox = new HBox(8);

        // dettagli
        private final Label descriptionLabel = new Label();
        private final Button toggleActiveBtn = new Button();
        private final Button sleepBtn = new Button("Sleep");
        private final Button deleteBtn = new Button("Delete");
        private final VBox detailsBox = new VBox(6);

        private boolean expanded = false;

        RuleCell() {
            root.setPadding(new Insets(8));

            titleBox.setAlignment(Pos.CENTER_LEFT);
            nameLabel.setStyle("-fx-font-weight: bold;");
            titleBox.getChildren().addAll(statusDot, nameLabel);

            descriptionLabel.setStyle("-fx-text-fill: #555;");
            deleteBtn.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white;");

            detailsBox.getChildren().addAll(
                    descriptionLabel,
                    toggleActiveBtn,
                    sleepBtn,
                    deleteBtn
            );
            detailsBox.setVisible(false);
            detailsBox.setManaged(false);

            root.getChildren().addAll(titleBox, detailsBox);

            root.setOnMouseClicked(e -> {
                expanded = !expanded;
                detailsBox.setVisible(expanded);
                detailsBox.setManaged(expanded);
            });

            toggleActiveBtn.setOnAction(e -> {
                Rule r = getItem();
                if (r != null) {
                    r.setActive(!r.isActive());
                    updateItem(r, false);
                }
            });

            deleteBtn.setOnAction(e -> {
                Rule r = getItem();
                if (r != null) deleteSpecificRule(r);
            });

            sleepBtn.setOnAction(e -> openSleepDialog());
        }

        @Override
        protected void updateItem(Rule rule, boolean empty) {
            super.updateItem(rule, empty);

            if (empty || rule == null) {
                setGraphic(null);
                return;
            }

            nameLabel.setText(rule.getName());

            // ---- STATO VISIVO SEMPLICE ----
            if (!rule.isActive()) {
                statusDot.setFill(Color.RED);
                statusDot.setStroke(null);
            } else if (rule.getSleepDurationMillis() > 0) {
                // Regola con sleep configurata
                statusDot.setFill(Color.YELLOW);
                statusDot.setStrokeWidth(2);
            } else {
                statusDot.setFill(Color.LIMEGREEN);
                statusDot.setStroke(null);
            }

            descriptionLabel.setText(
                    rule.getTrigger().getClass().getSimpleName()
                            + " → "
                            + rule.getAction().getClass().getSimpleName()
            );

            toggleActiveBtn.setText(
                    rule.isActive() ? "Deactivate" : "Activate"
            );

            setGraphic(root);
        }

        private void openSleepDialog() {
            Rule rule = getItem();
            if (rule == null) return;

            Dialog<Void> dialog = new Dialog<>();
            dialog.setTitle("Sleep configuration");
            dialog.setHeaderText("Set sleep duration");

            ButtonType confirmBtn =
                    new ButtonType("Confirm", ButtonBar.ButtonData.OK_DONE);
            dialog.getDialogPane().getButtonTypes()
                    .addAll(confirmBtn, ButtonType.CANCEL);

            TextField valueField = new TextField();
            valueField.setPromptText("Value");

            ComboBox<String> unitBox = new ComboBox<>();
            unitBox.getItems().addAll("Seconds", "Minutes", "Hours");
            unitBox.setValue("Seconds");

            VBox content = new VBox(10,
                    new Label("Duration:"),
                    valueField,
                    unitBox
            );
            dialog.getDialogPane().setContent(content);

            dialog.setResultConverter(btn -> {
                if (btn == confirmBtn) {
                    try {
                        long value = Long.parseLong(valueField.getText());
                        if (value < 0) return null;

                        long millis;
                        switch (unitBox.getValue()) {
                            case "Minutes":
                                millis = value * 60_000;
                                break;
                            case "Hours":
                                millis = value * 3_600_000;
                                break;
                            default:
                                millis = value * 1_000;
                        }

                        rule.setSleepDuration(millis);
                    } catch (NumberFormatException ignored) {
                    }
                }
                return null;
            });

            dialog.showAndWait();
        }
    }

        // ======================================================
    // UTILITIES
    // ======================================================
    private void deleteSpecificRule(Rule rule) {
        Alert a = new Alert(Alert.AlertType.CONFIRMATION,
                "Delete rule " + rule.getName() + "?",
                ButtonType.OK, ButtonType.CANCEL);
        a.showAndWait().ifPresent(r -> {
            if (r == ButtonType.OK) {
                ruleEngine.deleteRule(rule);
                ruleObservableList.remove(rule);
            }
        });
    }

    private <T> T openNewWindow(String fxml, String title) {
        try {
            FXMLLoader l = new FXMLLoader(getClass().getResource(fxml));
            Parent p = l.load();
            Stage s = new Stage();
            s.initModality(Modality.APPLICATION_MODAL);
            s.setTitle(title);
            s.setScene(new Scene(p));
            s.showAndWait();
            return l.getController();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void showAlert(String title, String msg, Alert.AlertType type) {
        Alert a = new Alert(type, msg, ButtonType.OK);
        a.setTitle(title);
        a.setHeaderText(null);
        a.showAndWait();
    }
}
