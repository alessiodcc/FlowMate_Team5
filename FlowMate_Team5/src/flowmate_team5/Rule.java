package flowmate_team5;

import java.io.Serializable;

public class Rule implements Serializable {

    private String name;
    private Trigger trigger;
    private Action action;
    private long sleepDurationMillis = 0;

    // The State object (Active, Inactive, or Cooldown)
    private RuleState currentState;

    public Rule(String name, Trigger trigger, Action action) {
        this.name = name;
        this.trigger = trigger;
        this.action = action;
        // Initial state is Active by default
        this.currentState = new ActiveState();
    }

    // --- State Pattern Logic ---

    // Delegate the check to the current state
    public void check() {
        if (currentState != null) {
            currentState.check(this);
        }
    }

    // Method used by State objects to switch the Rule's state
    public void setState(RuleState newState) {
        this.currentState = newState;
    }

    // Check if the rule is conceptually active (delegated to state)
    public boolean isActive() {
        return currentState != null && currentState.isActive();
    }

    // Force specific state (e.g., from UI Toggle)
    public void setActive(boolean active) {
        if (active) {
            this.currentState = new ActiveState();
        } else {
            this.currentState = new InactiveState();
        }
    }

    // --- Configuration & Getters ---

    public void setSleepDuration(long durationMillis) {
        this.sleepDurationMillis = (durationMillis < 0) ? 0 : durationMillis;

        // If the rule was Inactive but gets a new duration, we might want to reset it.
        // Or simply ensure that if it is currently Active, it stays Active.
        // For safety, if user edits duration, we often ensure it's in a valid state.
        if (this.sleepDurationMillis > 0 && !isActive()) {
            this.currentState = new ActiveState();
        }
    }

    public long getSleepDurationMillis() {
        return sleepDurationMillis;
    }

    public String getName() {
        return name;
    }

    public Trigger getTrigger() {
        return trigger;
    }

    public Action getAction() {
        return action;
    }

    @Override
    public String toString() {
        return name;
    }
}