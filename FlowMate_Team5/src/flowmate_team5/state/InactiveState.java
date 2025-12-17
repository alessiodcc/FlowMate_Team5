package flowmate_team5.state;

import flowmate_team5.core.Rule;

public class InactiveState implements RuleState {

    @Override
    public void check(Rule context) {
        // Do nothing
    }

    @Override
    public boolean isActive() {
        return false;
    }
}