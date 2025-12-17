package flowmate_team5.factory.creators;

import flowmate_team5.models.triggers.TemporalTrigger;
import flowmate_team5.models.Trigger;
import flowmate_team5.factory.CreatorTrigger;

// Concrete Creator in the Factory Method pattern
public class TemporalTriggerCreator implements CreatorTrigger {

    @Override
    public Trigger createTrigger() {
        // Instantiates the specific product (TemporalTrigger)
        return new TemporalTrigger();
    }
}