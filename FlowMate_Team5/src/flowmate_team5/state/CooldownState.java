package flowmate_team5.state;

import flowmate_team5.core.Rule;


public class CooldownState implements RuleState {

    private final long wakeUpTime;

    public CooldownState(long wakeUpTime) {
        this.wakeUpTime = wakeUpTime;
    }

    @Override
    public void check(Rule rule) {
        if (System.currentTimeMillis() >= wakeUpTime) {
            rule.setState(new ActiveState());
            System.out.println("[Rule State]: Cooldown finished → Active");
        }
    }

    @Override
    public boolean isActive() {
        return false;
    }
}
