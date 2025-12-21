package flowmate_team5.state;

import flowmate_team5.core.Rule;

public class ActiveState implements RuleState {

    @Override
    public void check(Rule context) {

        if (context.getTrigger() == null || !context.getTrigger().isTriggered()) {
            return;
        }

        context.execute();
        System.out.println("[Rule Fired]: " + context.getName());

        if (context.isRepeatable()) {

            long sleep = context.getSleepDurationMillis();
            long wakeUpTime = System.currentTimeMillis() + Math.max(sleep, 0);

            context.setState(new CooldownState(wakeUpTime));

        } else {
            context.setState(new InactiveState());
        }
    }

    @Override
    public boolean isActive() {
        return true;
    }
}
