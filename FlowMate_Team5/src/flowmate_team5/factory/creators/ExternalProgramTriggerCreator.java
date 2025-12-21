package flowmate_team5.factory.creators;

import flowmate_team5.factory.CreatorTrigger;
import flowmate_team5.models.Trigger;
import flowmate_team5.models.triggers.ExternalProgramTrigger;

/* Factory class responsible for instantiating External Program Triggers. */
public class ExternalProgramTriggerCreator implements CreatorTrigger {

    /* Implements the factory interface to create a default trigger instance. */
    @Override
    public Trigger createTrigger() {
        return new ExternalProgramTrigger("");
    }

    /* Creates a specific trigger instance with the provided command line argument. */
    public Trigger createTrigger(String commandLine) {
        return new ExternalProgramTrigger(commandLine);
    }
}