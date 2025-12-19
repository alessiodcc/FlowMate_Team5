package flowmate_team5.models.actions;

import flowmate_team5.models.Action;
import flowmate_team5.models.Counter;
import java.io.Serializable;

public class AddCounterToCounterAction implements Action, Serializable {

    private Counter sourceCounter;
    private Counter targetCounter;

    public AddCounterToCounterAction() {
        // Empty constructor for Factory
    }

    // --- Setters ---
    public void setSourceCounter(Counter sourceCounter) {
        this.sourceCounter = sourceCounter;
    }

    public void setTargetCounter(Counter targetCounter) {
        this.targetCounter = targetCounter;
    }

    // --- Getters ---
    public Counter getSourceCounter() { return sourceCounter; }
    public Counter getTargetCounter() { return targetCounter; }

    @Override
    public void execute() {
        if (sourceCounter == null || targetCounter == null) {
            System.err.println("[AddCounterAction] Error: Counters are not configured.");
            return;
        }

        double amountToAdd = sourceCounter.getValue();
        double oldTargetValue = targetCounter.getValue();

        // LOGIC: Since Counter.java doesn't have an 'add' method, we calculate manually
        double newTargetValue = oldTargetValue + amountToAdd;
        targetCounter.setValue(newTargetValue);

        System.out.println("[AddCounterAction] Added " + amountToAdd +
                " from '" + sourceCounter.getName() +
                "' to '" + targetCounter.getName() + "'");
        System.out.println(" -> New Value of " + targetCounter.getName() + ": " + newTargetValue);
    }

    @Override
    public String toString() {
        return "Add Counter Action";
    }
}