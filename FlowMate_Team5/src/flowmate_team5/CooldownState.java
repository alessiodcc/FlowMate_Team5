package flowmate_team5;

public class CooldownState implements RuleState {

    private final long wakeUpTime;

    public CooldownState(long wakeUpTime) {
        this.wakeUpTime = wakeUpTime;
    }

    @Override
    public void check(Rule context) {
        // Check if sleep period is over
        if (System.currentTimeMillis() >= wakeUpTime) {
            context.setState(new ActiveState());
            System.out.println("[Rule State]: Cooldown finished. Rule is active again.");
        }
    }

    @Override
    public boolean isActive() {
        // Considered active but sleeping
        return true;
    }
}