package flowmate_team5.state;

import flowmate_team5.core.Rule;

public class CooldownState implements RuleState {

    private final long wakeUpTime;

    public CooldownState(long wakeUpTime) {
        this.wakeUpTime = wakeUpTime;
    }

    @Override
    public void check(Rule context) {
        System.out.println("SLEEP: The rule: " + context.getName() + " is sleeping");
        // Check if sleep period is over
        if (System.currentTimeMillis() >= wakeUpTime) {
            context.setState(new ActiveState());
            context.setSleepDuration(0);
            System.out.println("[Rule State]: Cooldown finished. Rule is active again.");
        }
    }

    @Override
    public boolean isActive() {
        return false;
    }
}