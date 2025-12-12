package flowmate_team5;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;

public class RuleItemController {

    @FXML private Label ruleName;
    @FXML private Label ruleSummary;
    @FXML private ToggleButton toggleActive;
    @FXML private Button deleteBtn;

    private Rule rule;

    public void setRule(Rule rule) {
        this.rule = rule;

        // Nome della regola
        ruleName.setText(rule.getName());

        // Riepilogo automatico
        ruleSummary.setText(generateSummary(rule));

        // Toggle attivo/disattivo
        toggleActive.setSelected(rule.isActive());
        toggleActive.selectedProperty().addListener((obs, oldV, newV) -> {
            rule.setActive(newV);
        });

        // Cancellazione regola
        deleteBtn.setOnAction(e -> {
            RuleEngine.getInstance().deleteRule(rule);
        });
    }

    /**
     * Genera automaticamente un riepilogo basato sul tipo di Trigger e Action.
     * Esempio:
     *   FileExistsTrigger → CopyFileAction
     *   TemporalTrigger → PlayAudioAction
     */
    private String generateSummary(Rule rule) {

        // Nome classe Trigger (senza package)
        String triggerName = rule.getTrigger().getClass().getSimpleName();

        // Nome classe Action (senza package)
        Action action = rule.getAction();
        String actionName = (action != null)
                ? action.getClass().getSimpleName()
                : "No Action";

        return triggerName + " → " + actionName;
    }
}
