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
import flowmate_team5.models.Trigger;
import flowmate_team5.models.actions.*;
import flowmate_team5.models.triggers.FileExistsTrigger;
import flowmate_team5.models.triggers.TemporalTrigger;

// Imports added for your tasks
import flowmate_team5.models.actions.ExternalProgramAction;
import flowmate_team5.controllers.SelectExternalProgramController;
import flowmate_team5.controllers.SelectTwoCounterController;

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

    // ================= UI Components =================
    @FXML private ComboBox<String> triggerDropDownMenu;
    @FXML private ComboBox<String> actionDropDownMenu;
    @FXML private TextField RuleNameTextArea;
    @FXML private ListView<Rule> RuleList;
    @FXML private AnchorPane sidebar;

    // ================= Backend Variables =================
    private RuleEngine ruleEngine;
    private ObservableList<Rule> ruleObservableList;

    // Temporary storage for the rule currently being configured
    private Trigger chosenTrigger;
    private Action chosenAction;

    // ======================================================
    // INITIALIZATION
    // ======================================================
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        // Note: These strings must match the keys in RuleFactoryManager exactly
        triggerDropDownMenu.setItems(FXCollections.observableArrayList(
                "Temporal Trigger",
                "File Exists Trigger",
                "Day of Year Trigger",       // Added for US17
                "External Program Trigger"   // Added for US25
        ));

        actionDropDownMenu.setItems(FXCollections.observableArrayList(
                "Message Action",
                "Play Audio Action",
                "Write to Text File Action",
                "Copy File Action",
                "Move File Action",
                "Delete File Action",
                "External Program Action",    // Added for US14
                "Counter Operation Action"    // Added for US21
        ));

        ruleEngine = RuleEngine.getInstance();

        // Load rules from persistence
        ruleEngine.getRules().addAll(RulePersistenceManager.loadRules());

        ruleObservableList =
                FXCollections.observableArrayList(ruleEngine.getRules());

        RuleList.setItems(ruleObservableList);
        RuleList.setCellFactory(lv -> new RuleCell());

        // Initialize sidebar state (hidden)
        sidebar.setVisible(false);
        sidebar.setManaged(false);
        sidebar.setTranslateX(-320);
    }

    // ======================================================
    // SIDEBAR TOGGLE
    // ======================================================
    @FXML
    private void toggleSidebar() {
        TranslateTransition tt =
                new TranslateTransition(Duration.millis(250), sidebar);

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

    // ======================================================
    // CREATE RULE (Using Factory Manager)
    // ======================================================
    @FXML
    public void confirmButtonPushed() {

        String ruleName = RuleNameTextArea.getText();
        if (ruleName == null || ruleName.isBlank()) {
            showAlert("Error", "Rule name required", Alert.AlertType.ERROR);
            return;
        }

        String triggerType = triggerDropDownMenu.getValue();
        String actionType = actionDropDownMenu.getValue();

        if (triggerType == null || actionType == null) {
            showAlert("Error", "Select both trigger and action", Alert.AlertType.ERROR);
            return;
        }

        try {
            // 1. CREATE TRIGGER via Factory Manager
            chosenTrigger = RuleFactoryManager.createTrigger(triggerType);

            // 2. CONFIGURE TRIGGER (Open specific UI)
            switch (triggerType) {
                case "Temporal Trigger" -> openNewWindowWithInjection(
                        "/flowmate_team5/view/SelectTime.fxml",
                        "Select Time",
                        (SelectTimeController c) ->
                                c.setTrigger((TemporalTrigger) chosenTrigger)
                );
                case "File Exists Trigger" -> openNewWindowWithInjection(
                        "/flowmate_team5/view/FileExistsView.fxml",
                        "Configure File Trigger",
                        (FileExistsController c) ->
                                c.setTrigger((FileExistsTrigger) chosenTrigger)
                );
                // Note: DayOfTheYearTrigger and ExternalProgramTrigger are created via Factory,
                // but their Views are not yet connected here as they depend on Teammate 1/3 implementation.
            }

            // 3. CREATE ACTION via Factory Manager
            chosenAction = RuleFactoryManager.createAction(actionType);

            // 4. CONFIGURE ACTION (Open specific UI)
            switch (actionType) {
                case "Message Action" -> openNewWindowWithInjection(
                        "/flowmate_team5/view/WriteAMessage.fxml",
                        "Message",
                        (WriteAMessageController c) ->
                                c.setAction((MessageAction) chosenAction)
                );
                case "Play Audio Action" -> openNewWindowWithInjection(
                        "/flowmate_team5/view/SelectAudioPath.fxml",
                        "Audio",
                        (SelectAudioPathController c) ->
                                c.setAction((PlayAudioAction) chosenAction)
                );
                case "Write to Text File Action" -> openNewWindowWithInjection(
                        "/flowmate_team5/view/WriteOnFile.fxml",
                        "Write to File",
                        (WriteOnFileController c) ->
                                c.setAction((TextAction) chosenAction)
                );
                case "Copy File Action" -> openNewWindowWithInjection(
                        "/flowmate_team5/view/CopyFile.fxml",
                        "Copy File",
                        (CopyFileController c) ->
                                c.setAction((CopyFileAction) chosenAction)
                );
                case "Move File Action" -> openNewWindowWithInjection(
                        "/flowmate_team5/view/MoveFileView.fxml",
                        "Move File",
                        (MoveFileController c) ->
                                c.setAction((MoveFileAction) chosenAction)
                );
                case "Delete File Action" -> openNewWindowWithInjection(
                        "/flowmate_team5/view/DeleteFileView.fxml",
                        "Delete File",
                        (DeleteFileController c) ->
                                c.setAction((DeleteFileAction) chosenAction)
                );
                // Added for US14
                case "External Program Action" -> openNewWindowWithInjection(
                        "/flowmate_team5/view/SelectExternalProgramView.fxml",
                        "Run Program",
                        (SelectExternalProgramController c) -> {
                            // Assuming the controller has a method to accept the action
                            // c.setAction((ExternalProgramAction) chosenAction);
                        }
                );
                // Added for US21
                case "Counter Operation Action" -> openNewWindowWithInjection(
                        "/flowmate_team5/view/SelectTwoCounterView.fxml",
                        "Counter Operations",
                        (SelectTwoCounterController c) -> {
                            // Logic to pass action to controller
                        }
                );
            }

            // 5. ASSEMBLE RULE
            Rule rule = new Rule(ruleName, chosenTrigger, chosenAction);
            ruleEngine.addRule(rule);
            ruleObservableList.add(rule);

            // Clear input
            RuleNameTextArea.clear();
            triggerDropDownMenu.getSelectionModel().clearSelection();
            actionDropDownMenu.getSelectionModel().clearSelection();

        } catch (IllegalArgumentException e) {
            showAlert("Factory Error", e.getMessage(), Alert.AlertType.ERROR);
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error", "An unexpected error occurred", Alert.AlertType.ERROR);
        }
    }

    // ======================================================
    // GENERIC WINDOW OPENER + INJECTION
    // ======================================================
    private <T> void openNewWindowWithInjection(
            String fxmlPath,
            String title,
            Consumer<T> injector
    ) {
        try {
            FXMLLoader loader =
                    new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();

            T controller = loader.getController();
            // Inject the specific object into the controller
            injector.accept(controller);

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle(title);
            stage.setScene(new Scene(root));
            stage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // ======================================================
    // SIDEBAR RULE CELL (Custom List View Item)
    // ======================================================
    private class RuleCell extends ListCell<Rule> {

        private final VBox root = new VBox(8);
        private final Circle statusDot = new Circle(6);
        private final Label nameLabel = new Label();
        private final Label descriptionLabel = new Label();

        private final Button toggleActiveBtn = new Button();
        private final Button sleepBtn = new Button("Sleep");
        private final Button deleteBtn = new Button("Delete");

        private final VBox detailsBox = new VBox(6);
        private boolean expanded = false;

        RuleCell() {
            root.setPadding(new Insets(10));
            nameLabel.setStyle("-fx-font-weight: bold;");

            HBox header = new HBox(8, statusDot, nameLabel);
            header.setAlignment(Pos.CENTER_LEFT);

            deleteBtn.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white;");

            detailsBox.getChildren().addAll(
                    descriptionLabel,
                    toggleActiveBtn,
                    sleepBtn,
                    deleteBtn
            );
            detailsBox.setVisible(false);
            detailsBox.setManaged(false);

            root.getChildren().addAll(header, detailsBox);

            // Expand/Collapse logic
            root.setOnMouseClicked(e -> {
                expanded = !expanded;
                detailsBox.setVisible(expanded);
                detailsBox.setManaged(expanded);
            });

            // Toggle Active State
            toggleActiveBtn.setOnAction(e -> {
                Rule r = getItem();
                if (r != null) {
                    r.setActive(!r.isActive());
                    updateItem(r, false);
                }
                e.consume();
            });

            // Delete Rule
            deleteBtn.setOnAction(e -> {
                Rule r = getItem();
                if (r == null) return;

                Alert a = new Alert(Alert.AlertType.CONFIRMATION,
                        "Delete rule \"" + r.getName() + "\"?",
                        ButtonType.OK, ButtonType.CANCEL);

                a.showAndWait().ifPresent(btn -> {
                    if (btn == ButtonType.OK) {
                        ruleEngine.deleteRule(r);
                        ruleObservableList.remove(r);
                    }
                });
                e.consume();
            });

            // Set Sleep Mode
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
        }

        @Override
        protected void updateItem(Rule rule, boolean empty) {
            super.updateItem(rule, empty);

            if (empty || rule == null) {
                setGraphic(null);
                return;
            }

            nameLabel.setText(rule.getName());
            descriptionLabel.setText(
                    rule.getTrigger().getClass().getSimpleName()
                            + " → "
                            + rule.getAction().getClass().getSimpleName()
            );

            if (!rule.isActive()) {
                statusDot.setFill(Color.RED);
            } else if (rule.getSleepDurationMillis() > 0) {
                statusDot.setFill(Color.GOLD); // Yellow for sleeping
            } else {
                statusDot.setFill(Color.LIMEGREEN);
            }

            toggleActiveBtn.setText(
                    rule.isActive() ? "Deactivate" : "Activate"
            );

            setGraphic(root);
        }
    }

    // ======================================================
    // SLEEP WINDOW
    // ======================================================
    private SleepingStateController openSleepWindow() {
        try {
            FXMLLoader loader =
                    new FXMLLoader(getClass().getResource(
                            "/flowmate_team5/view/SleepingStateView.fxml"));
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

    // ======================================================
    // UTIL
    // ======================================================
    private void showAlert(String title, String msg, Alert.AlertType type) {
        Alert a = new Alert(type, msg, ButtonType.OK);
        a.setTitle(title);
        a.setHeaderText(null);
        a.showAndWait();
    }
}