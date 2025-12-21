package flowmate_team5.models.actions;

import flowmate_team5.models.Action;
import flowmate_team5.models.Counter;
import java.io.Serializable;

public class AddCounterToCounterAction implements Action, Serializable {

    private final String name = "Add Counter to Counter Action";
    private Counter sourceCounter;
    private Counter targetCounter;

    // --- Getters & Setters ---
    public void setSourceCounter(Counter source) {
        this.sourceCounter = source;
    }
    public Counter getSourceCounter() {
        return sourceCounter;
    }

    public void setTargetCounter(Counter target) {
        this.targetCounter = target;
    }
    public Counter getTargetCounter() {
        return targetCounter;
    }

    // --- Execution Logic ---
    @Override
    public void execute() {
        if (sourceCounter != null && targetCounter != null) {
            // Logic: Target Value = Target Value + Source Value
            int newValue = targetCounter.getValue() + sourceCounter.getValue();
            targetCounter.setValue(newValue);

            System.out.println("Executed: Added " + sourceCounter.getValue()
                    + " to " + targetCounter.getName()
                    + " (Total: " + newValue + ")");
        }
    }

    @Override
    public String toString() {
        return name;
    }
}