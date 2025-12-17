package flowmate_team5.state;

import flowmate_team5.core.Rule;

public class ActiveState implements RuleState {

    @Override
    public void check(Rule context) {
        // Evaluate trigger
        if (context.getTrigger() != null && context.getTrigger().isTriggered()) {

            // Execute action
            if (context.getAction() != null) {
                context.getAction().execute();
                System.out.println("[Rule Fired]: " + context.getName());
            }

            // Handle transition based on sleep duration configuration
            long sleepDuration = context.getSleepDurationMillis();
            if (sleepDuration > 0) {
                // Transition to Cooldown
                long wakeUpTime = System.currentTimeMillis() + sleepDuration;
                context.setState(new CooldownState(wakeUpTime));
                System.out.println("[Rule State]: Transitioning to Cooldown.");
            } else {
                // One-shot rule: transition to Inactive
                context.setState(new InactiveState());
                System.out.println("[Rule State]: One-shot rule deactivated.");
            }
        }
    }

    @Override
    public boolean isActive() {
        return true;
    }
}