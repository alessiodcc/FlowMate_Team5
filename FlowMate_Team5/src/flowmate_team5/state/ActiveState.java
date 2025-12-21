package flowmate_team5.state;

import flowmate_team5.core.Rule;

public class ActiveState implements RuleState {

    @Override
    public void check(Rule context) {

        // Se il trigger non scatta → non fare nulla
        if (context.getTrigger() == null || !context.getTrigger().isTriggered()) {
            return;
        }

        // 🔥 ESECUZIONE
        context.execute();
        System.out.println("[Rule Fired]: " + context.getName());

        long sleep = context.getSleepDurationMillis();

        // 💤 PRIORITÀ ASSOLUTA: SLEEP
        if (sleep > 0) {
            long wakeUp = System.currentTimeMillis() + sleep;
            context.setState(new CooldownState(wakeUp));
            System.out.println("SLEEP: The rule " + context.getName() + " is sleeping");
            return;
        }

        // ❌ Se non è repeatable → spegni
        if (!context.isRepeatable()) {
            context.setState(new InactiveState());
        }
    }

    @Override
    public boolean isActive() {
        return true;
    }
}
