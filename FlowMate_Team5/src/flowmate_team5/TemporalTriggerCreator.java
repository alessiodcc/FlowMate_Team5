package flowmate_team5;

// Concrete Creator in the Factory Method pattern
public class TemporalTriggerCreator implements CreatorTrigger {

    @Override
    public Trigger createTrigger() {
        // Instantiates the specific product (TemporalTrigger)
        return new TemporalTrigger();
    }
}