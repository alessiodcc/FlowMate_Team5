package flowmate_team5.state;

import flowmate_team5.core.Rule;

/* Represents the state where the rule is disabled and performs no operations. */
public class InactiveState implements RuleState {

    /* Performs no action, ensuring the rule remains idle during the check cycle. */
    @Override
    public void check(Rule context) {
        // Deliberately empty to suspend all rule processing
    }

    /* Returns false to indicate the rule is not currently active. */
    @Override
    public boolean isActive() {
        return false;
    }
}