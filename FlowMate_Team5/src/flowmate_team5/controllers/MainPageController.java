package flowmate_team5.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Consumer;

import flowmate_team5.core.Rule;
import flowmate_team5.core.RuleEngine;
import flowmate_team5.core.RulePersistenceManager;
import flowmate_team5.factory.RuleFactoryManager;
import flowmate_team5.models.Action;
import flowmate_team5.models.Counter;
import flowmate_team5.models.Trigger;
import flowmate_team5.models.actions.*;
import flowmate_team5.models.actions.AddCounterToCounterAction;
import flowmate_team5.models.triggers.*;
import flowmate_team5.models.actions.ExternalProgramAction;

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

    @FXML private ComboBox<String> triggerDropDownMenu;
    @FXML private ComboBox<String> actionDropDownMenu;
    @FXML private TextField RuleNameTextArea;
    @FXML private ListView<Rule> RuleList;
    @FXML private AnchorPane sidebar;
    @FXML private ListView<Counter> CounterListView;
    @FXML private Button createRuleButton;

    private RuleEngine ruleEngine;
    private ObservableList<Rule> ruleObservableList;
    private Trigger chosenTrigger;
    private Action chosenAction;
    private final ObservableList<Counter> availableCounters = FXCollections.observableArrayList();
    private Rule ruleBeingEdited;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        triggerDropDownMenu.setItems(FXCollections.observableArrayList(
                "Temporal Trigger",
                "File Exists Trigger",
                "Day of Month Trigger",
                "Day of Year Trigger",
                "External Program Trigger",
                "Counter Comparison Trigger"
        ));

        actionDropDownMenu.setItems(FXCollections.observableArrayList(
                "Message Action",
                "Play Audio Action",
                "Write to Text File Action",
                "Copy File Action",
                "Move File Action",
                "Delete File Action",
                "External Program Action",
                "Counter Operation Action",
                "Add Counter to Counter Action"
        ));

        ruleEngine = RuleEngine.getInstance();
        ruleEngine.getRules().addAll(RulePersistenceManager.loadRules());

        ruleObservableList = FXCollections.observableArrayList(ruleEngine.getRules());
        RuleList.setItems(ruleObservableList);
        RuleList.setCellFactory(lv -> new RuleCell());

        // ================= COUNTERS =================
        CounterListView.setItems(availableCounters);

        CounterListView.setCellFactory(lv -> {

            ListCell<Counter> cell = new ListCell<>() {
                @Override
                protected void updateItem(Counter item, boolean empty) {
                    super.updateItem(item, empty);
                    setText(empty || item == null ? null : item.toString());
                }
            };

            // Tooltip VISIBILE
            cell.setTooltip(new Tooltip("Double click to edit this counter"));

            // Double click FUNZIONANTE
            cell.setOnMouseClicked(event -> {
                if (!cell.isEmpty() && event.getClickCount() == 2) {
                    CounterListView.getSelectionModel().select(cell.getItem());
                    handleEditCounter();
                }
            });

            return cell;
        });

        // ================= SIDEBAR =================
        sidebar.setVisible(false);
        sidebar.setManaged(false);
        sidebar.setTranslateX(-320);
    }


    @FXML
    private void handleCreateCounter() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/flowmate_team5/view/CreateCounterView.fxml"));
            Parent root = loader.load();

            CreateCounterController controller = loader.getController();

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Create Counter");
            stage.setScene(new Scene(root));
            stage.showAndWait();

            Counter c = controller.getCounter();
            if (c != null) {
                availableCounters.add(c);
                ruleEngine.addCounter(c);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleEditCounter() {

        Counter selected =
                CounterListView.getSelectionModel().getSelectedItem();

        if (selected == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Counter Selected");
            alert.setContentText("Please select a counter to edit.");
            alert.showAndWait();
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/flowmate_team5/view/EditACounterView.fxml")
            );
            Parent root = loader.load();

            EditACounterController controller = loader.getController();
            controller.setCounter(selected); // 🔑 injection

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Edit Counter");
            stage.setScene(new Scene(root));
            stage.showAndWait();

            // Forza refresh grafico
            CounterListView.refresh();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    @FXML
    private void toggleSidebar() {
        TranslateTransition tt = new TranslateTransition(Duration.millis(250), sidebar);
        if (!sidebar.isVisible()) {
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

    @FXML
    public void confirmButtonPushed() {

        String ruleName = RuleNameTextArea.getText();
        if (ruleName == null || ruleName.isBlank()) {
            showAlert("Error", "Rule name required", Alert.AlertType.ERROR);
            return;
        }

        if (ruleBeingEdited != null) {
            chosenTrigger = ruleBeingEdited.getTrigger();
            chosenAction = ruleBeingEdited.getAction();
        } else {
            String triggerType = triggerDropDownMenu.getValue();
            String actionType = actionDropDownMenu.getValue();

            if (triggerType == null || actionType == null) {
                showAlert("Error", "Select both trigger and action", Alert.AlertType.ERROR);
                return;
            }

            try {
                chosenTrigger = RuleFactoryManager.createTrigger(triggerType);
                chosenAction = RuleFactoryManager.createAction(actionType);
            } catch (IllegalArgumentException e) {
                showAlert("Factory Error", e.getMessage(), Alert.AlertType.ERROR);
                return;
            }
        }

        try {
            String tType = "";
            if (ruleBeingEdited != null) {
                if (chosenTrigger instanceof TemporalTrigger) tType = "Temporal Trigger";
                else if (chosenTrigger instanceof FileExistsTrigger) tType = "File Exists Trigger";
                else if (chosenTrigger instanceof DayOfTheMonthTrigger) tType = "Day of Month Trigger";
                else if (chosenTrigger instanceof DayOfTheYearTrigger) tType = "Day of Year Trigger";
                else if (chosenTrigger instanceof ExternalProgramTrigger) tType = "External Program Trigger";
                else if (chosenTrigger instanceof CounterIntegerComparisonTrigger) tType = "Counter Comparison Trigger";
            } else {
                tType = triggerDropDownMenu.getValue();
            }

            switch (tType) {
                case "Temporal Trigger" -> openNewWindowWithInjection(
                        "/flowmate_team5/view/SelectTime.fxml",
                        "Select Time",
                        (SelectTimeController c) -> c.setTrigger((TemporalTrigger) chosenTrigger)
                );
                case "File Exists Trigger" -> openNewWindowWithInjection(
                        "/flowmate_team5/view/FileExistsView.fxml",
                        "Configure File Trigger",
                        (FileExistsController c) -> c.setTrigger((FileExistsTrigger) chosenTrigger)
                );
                case "Day of Week Trigger" -> openNewWindowWithInjection(
                        "/flowmate_team5/view/SelectDayOfWeekView.fxml", // Check if inside /view/ folder!
                        "Select Days",
                        (flowmate_team5.controllers.SelectDayOfWeekController c) ->
                                c.setTrigger((DayOfTheWeekTrigger) chosenTrigger)
                );
                case "Day of Month Trigger" -> openNewWindowWithInjection(
                        "/flowmate_team5/view/SelectDayOfTheMonthView.fxml",
                        "Configure Day of the Month Trigger",
                        (SelectDayOfTheMonthController c) -> c.setTrigger((DayOfTheMonthTrigger) chosenTrigger)
                );
                case "Day of Year Trigger" -> openNewWindowWithInjection(
                        "/flowmate_team5/view/SelectDayOfAYear.fxml",
                        "Configure Day of the Year Trigger",
                        (SelectDayOfTheYearController c) -> c.setTrigger((DayOfTheYearTrigger) chosenTrigger)
                );
                case "External Program Trigger" -> openNewWindowWithInjection(
                        "/flowmate_team5/view/SelectExternalProgramTriggerView.fxml",
                        "Configure Program Trigger",
                        (SelectExternalProgramTriggerController c) ->
                                c.setTrigger((ExternalProgramTrigger) chosenTrigger)
                );
                case "Counter Comparison Trigger" -> openNewWindowWithInjection(
                        "/flowmate_team5/view/CompareCounterIntegerView.fxml",
                        "Compare Counter",
                        (flowmate_team5.controllers.CompareCounterIntegerController c) ->
                                c.setTrigger((CounterIntegerComparisonTrigger) chosenTrigger)
                );
            }

            String aType = "";
            if (ruleBeingEdited != null) {
                if (chosenAction instanceof MessageAction) aType = "Message Action";
                else if (chosenAction instanceof PlayAudioAction) aType = "Play Audio Action";
                else if (chosenAction instanceof TextAction) aType = "Write to Text File Action";
                else if (chosenAction instanceof CopyFileAction) aType = "Copy File Action";
                else if (chosenAction instanceof MoveFileAction) aType = "Move File Action";
                else if (chosenAction instanceof DeleteFileAction) aType = "Delete File Action";
                else if (chosenAction instanceof ExternalProgramAction) aType = "External Program Action";
            } else {
                aType = actionDropDownMenu.getValue();
            }

            switch (aType) {
                case "Message Action" -> openNewWindowWithInjection(
                        "/flowmate_team5/view/WriteAMessage.fxml",
                        "Message",
                        (WriteAMessageController c) -> c.setAction((MessageAction) chosenAction)
                );
                case "Play Audio Action" -> openNewWindowWithInjection(
                        "/flowmate_team5/view/SelectAudioPath.fxml",
                        "Audio",
                        (SelectAudioPathController c) -> c.setAction((PlayAudioAction) chosenAction)
                );
                case "Write to Text File Action" -> openNewWindowWithInjection(
                        "/flowmate_team5/view/WriteOnFile.fxml",
                        "Write to File",
                        (WriteOnFileController c) -> c.setAction((TextAction) chosenAction)
                );
                case "Copy File Action" -> openNewWindowWithInjection(
                        "/flowmate_team5/view/CopyFile.fxml",
                        "Copy File",
                        (CopyFileController c) -> c.setAction((CopyFileAction) chosenAction)
                );
                case "Move File Action" -> openNewWindowWithInjection(
                        "/flowmate_team5/view/MoveFileView.fxml",
                        "Move File",
                        (MoveFileController c) -> c.setAction((MoveFileAction) chosenAction)
                );
                case "Delete File Action" -> openNewWindowWithInjection(
                        "/flowmate_team5/view/DeleteFileView.fxml",
                        "Delete File",
                        (DeleteFileController c) -> c.setAction((DeleteFileAction) chosenAction)
                );
                case "External Program Action" -> openNewWindowWithInjection(
                        "/flowmate_team5/view/SelectExternalProgramActionView.fxml",
                        "Run Program",
                        (SelectExternalProgramActionController c) ->
                                c.setAction((ExternalProgramAction) chosenAction)
                );
            }

            if (ruleBeingEdited != null) {
                ruleBeingEdited.setName(ruleName);
                ruleBeingEdited = null;
            } else {
                Rule rule = new Rule(ruleName, chosenTrigger, chosenAction);
                ruleEngine.addRule(rule);
                ruleObservableList.add(rule);
            }

            RulePersistenceManager.saveRules(ruleEngine.getRules());

            RuleNameTextArea.clear();
            triggerDropDownMenu.getSelectionModel().clearSelection();
            actionDropDownMenu.getSelectionModel().clearSelection();
            RuleList.refresh();

        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error", "An unexpected error occurred", Alert.AlertType.ERROR);
        }
    }

    private <T> void openNewWindowWithInjection(String fxmlPath, String title, Consumer<T> injector) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();
            T controller = loader.getController();
            injector.accept(controller);
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle(title);
            stage.setScene(new Scene(root));
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "Could not load view: " + fxmlPath, Alert.AlertType.ERROR);
        }
    }

    private class RuleCell extends ListCell<Rule> {
        private final VBox root = new VBox(8);
        private final Circle statusDot = new Circle(6);
        private final Label nameLabel = new Label();
        private final Label descriptionLabel = new Label();
        private final Label counterInfoLabel = new Label();
        private final Button toggleActiveBtn = new Button();
        private final Button sleepBtn = new Button("Sleep");
        private final Button deleteBtn = new Button("Delete");
        private final Button editBtn = new Button("Edit");
        private final VBox detailsBox = new VBox(6);
        private boolean expanded = false;

        RuleCell() {
            root.setPadding(new Insets(10));
            nameLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");
            counterInfoLabel.setStyle("-fx-font-size: 11px; -fx-text-fill: #3498db; -fx-font-weight: bold;");

            HBox header = new HBox(8, statusDot, nameLabel);
            header.setAlignment(Pos.CENTER_LEFT);
            deleteBtn.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white;");
            editBtn.setStyle("-fx-background-color: #2ecc71; -fx-text-fill: white;");

            detailsBox.getChildren().addAll(descriptionLabel, counterInfoLabel, toggleActiveBtn, sleepBtn, deleteBtn, editBtn);
            detailsBox.setVisible(false);
            detailsBox.setManaged(false);
            root.getChildren().addAll(header, detailsBox);

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
                e.consume();
            });

            deleteBtn.setOnAction(e -> {
                Rule r = getItem();
                if (r == null) return;
                Alert a = new Alert(Alert.AlertType.CONFIRMATION,
                        "Delete rule \"" + r.getName() + "\"?", ButtonType.OK, ButtonType.CANCEL);
                a.showAndWait().ifPresent(btn -> {
                    if (btn == ButtonType.OK) {
                        ruleEngine.deleteRule(r);
                        ruleObservableList.remove(r);
                    }
                });
                e.consume();
            });

            sleepBtn.setOnAction(e -> {
                Rule r = getItem();
                if (r == null) return;
                SleepingStateController controller = openSleepWindow();
                if (controller != null && controller.getSleepDuration() != null) {
                    r.setSleepDuration(controller.getSleepDuration().toMillis());
                    updateItem(r, false);
                }
                e.consume();
            });
            editBtn.setOnAction(e -> {
                Rule r = getItem();
                if (r != null) {
                    handleEditRule(r);
                }
                e.consume();
            });
        }

        @Override
        protected void updateItem(Rule rule, boolean empty) {
            super.updateItem(rule, empty);
            if (empty || rule == null) {
                setGraphic(null);
                return;
            }
            nameLabel.setText(rule.getName());
            descriptionLabel.setText(rule.getTrigger().getClass().getSimpleName()
                    + " → " + rule.getAction().getClass().getSimpleName());

            if (rule.getTrigger() instanceof CounterIntegerComparisonTrigger) {
                CounterIntegerComparisonTrigger trigger = (CounterIntegerComparisonTrigger) rule.getTrigger();
                if (trigger.getCounter() != null) {
                    counterInfoLabel.setText("Counter: " + trigger.getCounter().getName() +
                            " [Value: " + trigger.getCounter().getValue() + "]");
                    counterInfoLabel.setVisible(true);
                    counterInfoLabel.setManaged(true);
                }
            } else {
                counterInfoLabel.setVisible(false);
                counterInfoLabel.setManaged(false);
            }

            if (!rule.isActive()) statusDot.setFill(Color.RED);
            else if (rule.getSleepDurationMillis() > 0) statusDot.setFill(Color.GOLD);
            else statusDot.setFill(Color.LIMEGREEN);

            toggleActiveBtn.setText(rule.isActive() ? "Deactivate" : "Activate");
            setGraphic(root);
        }
    }

    private SleepingStateController openSleepWindow() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/flowmate_team5/view/SleepingStateView.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Set Sleep Duration");
            stage.setScene(new Scene(root));
            stage.showAndWait();
            return loader.getController();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void handleEditRule(Rule rule) {
        this.ruleBeingEdited = rule;
        RuleNameTextArea.setText(rule.getName());
        createRuleButton.setText("Save Changes");
        confirmButtonPushed();
    }

    private void showAlert(String title, String msg, Alert.AlertType type) {
        Alert a = new Alert(type, msg, ButtonType.OK);
        a.setTitle(title);
        a.setHeaderText(null);
        a.showAndWait();
    }
}