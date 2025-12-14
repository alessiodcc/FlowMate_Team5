package flowmate_team5;

import java.io.Serializable;

public class Rule implements Serializable {

    private String name;
    private Trigger trigger;
    private Action action;

    // State pattern reference
    private RuleState state;

    private long sleepDurationMillis = 0;

    public Rule(String name, Trigger trigger, Action action) {
        this.name = name;
        this.trigger = trigger;
        this.action = action;
        // Default state is Active
        this.state = new ActiveState();
    }

    // Delegates logic to the current state
    public void check() {
        this.state.check(this);
    }

    // State transition method
    public void setState(RuleState newState) {
        this.state = newState;
    }

    // Logic to toggle state from UI
    public void setActive(boolean active) {
        if (active) {
            this.state = new ActiveState();
        } else {
            this.state = new InactiveState();
        }
    }

    public boolean isActive() {
        return this.state.isActive();
    }

    public void setSleepDuration(long durationMillis) {
        if (durationMillis < 0) {
            this.sleepDurationMillis = 0;
        } else {
            this.sleepDurationMillis = durationMillis;
        }
        // Force reset to active if setting a sleep duration on an inactive rule
        if (durationMillis > 0 && !isActive()) {
            this.state = new ActiveState();
        }
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

    public long getSleepDurationMillis() {
        return sleepDurationMillis;
    }

    @Override
    public String toString() {
        return name;
    }
}