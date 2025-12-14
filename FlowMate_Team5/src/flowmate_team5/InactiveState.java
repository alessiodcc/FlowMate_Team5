package flowmate_team5;

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