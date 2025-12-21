package flowmate_team5.state;

import flowmate_team5.core.Rule;

/* Represents the state where the rule is enabled and actively monitoring its trigger condition. */
public class ActiveState implements RuleState {

    /* Checks the trigger condition and executes the action if the trigger fires. */
    @Override
    public void check(Rule context) {

        // If the trigger is invalid or has not fired, exit immediately
        if (context.getTrigger() == null || !context.getTrigger().isTriggered()) {
            return;
        }

        // Execute the rule's action
        context.execute();
        System.out.println("[Rule Fired]: " + context.getName());

        long sleep = context.getSleepDurationMillis();

        // If a sleep duration is configured, transition to CooldownState to suspend execution
        if (sleep > 0) {
            long wakeUp = System.currentTimeMillis() + sleep;
            context.setState(new CooldownState(wakeUp));
            System.out.println("SLEEP: The rule " + context.getName() + " is sleeping");
            return;
        }

        // If the rule is not repeatable (one-shot), transition to InactiveState after execution
        if (!context.isRepeatable()) {
            context.setState(new InactiveState());
        }
    }

    /* Returns true indicating the rule is currently active. */
    @Override
    public boolean isActive() {
        return true;
    }
}