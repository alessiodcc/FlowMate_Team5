package flowmate_team5.core;

import flowmate_team5.models.Action;
import flowmate_team5.models.Trigger;
import flowmate_team5.state.ActiveState;
import flowmate_team5.state.InactiveState;
import flowmate_team5.state.RuleState;

import java.io.Serializable;

public class Rule implements Serializable {

    private String name;
    private Trigger trigger;
    private Action action;

    // Sleep duration after execution (milliseconds)
    private long sleepDurationMillis = 0;

    // Current state (State Pattern)
    private RuleState currentState;

    private boolean repeatable = false;


    public Rule(String name, Trigger trigger, Action action) {
        this.name = name;
        this.trigger = trigger;
        this.action = action;
        this.currentState = new ActiveState(); // default state
    }

    // ---------------- STATE PATTERN ----------------

    public void check() {
        if (currentState != null) {
            currentState.check(this);
        }
    }

    public void setState(RuleState newState) {
        this.currentState = newState;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isActive() {
        return currentState != null && currentState.isActive();
    }
    public boolean isSleeping() { return currentState instanceof flowmate_team5.state.CooldownState; }
    // Used by UI toggle
    public void setActive(boolean active) {
        if (active) {
            this.currentState = new ActiveState();
        } else {
            this.currentState = new InactiveState();
        }
    }

    public boolean isRepeatable() {
        return repeatable;
    }

    public void setRepeatable(boolean repeatable) {
        this.repeatable = repeatable;
    }


    // ---------------- EXECUTION ----------------

    /**
     * Centralized execution point.
     * State objects MUST call this method.
     */
    public void execute() {
        if (action != null) {
            action.execute();
        }
    }

    // ---------------- CONFIGURATION ----------------

    public void setSleepDuration(long durationMillis) {
        this.sleepDurationMillis = Math.max(0, durationMillis);
    }

    public long getSleepDurationMillis() {
        return sleepDurationMillis;
    }

    // ---------------- GETTERS ----------------

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
