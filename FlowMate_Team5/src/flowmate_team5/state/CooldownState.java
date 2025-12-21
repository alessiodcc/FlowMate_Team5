package flowmate_team5.state;

import flowmate_team5.core.Rule;

/* Represents the state where the rule is in a cooling-down period (sleeping) after execution. */
public class CooldownState implements RuleState {

    private final long wakeUpTime;

    /* Initializes the state with the specific timestamp when the rule should wake up. */
    public CooldownState(long wakeUpTime) {
        this.wakeUpTime = wakeUpTime;
    }

    /* Verifies if the sleep duration has elapsed and restores the rule to the Active state. */
    @Override
    public void check(Rule context) {
        if (System.currentTimeMillis() >= wakeUpTime) {
            // Transition state back to Active
            context.setState(new ActiveState());

            // Immediately re-evaluate the rule logic now that it is active again.
            // This ensures the rule does not wait for the next scheduler cycle to fire.
            context.check();
        }
    }

    /* Returns false, indicating the rule is currently paused and not monitoring triggers. */
    @Override
    public boolean isActive() {
        return false;
    }
}