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

        ruleName.setText(rule.getName());
        ruleSummary.setText(buildSummary());

        toggleActive.setSelected(rule.isActive());
        toggleActive.selectedProperty()
                .addListener((o, a, b) -> rule.setActive(b));

        deleteBtn.setOnAction(e ->
                RuleEngine.getInstance().deleteRule(rule));
    }

    private String buildSummary() {
        String trigger =
                rule.getTrigger().getClass().getSimpleName();
        String action =
                rule.getAction().getClass().getSimpleName();
        return trigger + " → " + action;
    }
}
