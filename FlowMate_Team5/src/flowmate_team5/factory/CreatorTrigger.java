package flowmate_team5.factory;

import flowmate_team5.models.Trigger;

// Interface for the Factory Method pattern used to create Triggers
public interface CreatorTrigger {

    // Factory method to instantiate a concrete Trigger
    Trigger createTrigger();
}