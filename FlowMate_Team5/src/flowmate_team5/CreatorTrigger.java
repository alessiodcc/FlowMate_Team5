package flowmate_team5;

// Interface for the Factory Method pattern used to create Triggers
public interface CreatorTrigger {

    // Factory method to instantiate a concrete Trigger
    Trigger createTrigger();
}