package flowmate_team5.factory.creators;

import flowmate_team5.factory.CreatorTrigger;
import flowmate_team5.models.Trigger;
import flowmate_team5.models.triggers.CounterIntegerComparisonTrigger;

public class CounterIntegerComparisonTriggerCreator implements CreatorTrigger {
    @Override
    public Trigger createTrigger() {
        return new CounterIntegerComparisonTrigger();
    }
}
