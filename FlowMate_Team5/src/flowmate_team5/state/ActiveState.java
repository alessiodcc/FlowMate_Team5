package flowmate_team5.state;

import flowmate_team5.core.Rule;

public class ActiveState implements RuleState {

    @Override
    public void check(Rule context) {

        if (context.getTrigger() != null && context.getTrigger().isTriggered()) {

            context.execute();
            System.out.println("[Rule Fired]: " + context.getName());

            long sleepDuration = context.getSleepDurationMillis();

            if (context.isRepeatable()) {

                if (sleepDuration > 0) {
                    long wakeUpTime = System.currentTimeMillis() + sleepDuration;
                    context.setState(new CooldownState(wakeUpTime));
                }

            } else {
                context.setState(new InactiveState());
            }

        }
    }

    @Override
    public boolean isActive() {
        return true;
    }
}
