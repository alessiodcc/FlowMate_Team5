package flowmate_team5.state;

import flowmate_team5.core.Rule;

public class CooldownState implements RuleState {

    private final long wakeUpTime;

    public CooldownState(long wakeUpTime) {
        this.wakeUpTime = wakeUpTime;
    }

    @Override
    public void check(Rule context) {
        if (System.currentTimeMillis() >= wakeUpTime) {
            context.setState(new ActiveState());

            // 🔁 IMPORTANTISSIMO:
            // riesegue subito il check ora che è attiva
            context.check();
        }
    }


    @Override
    public boolean isActive() {
        return false;
    }
}