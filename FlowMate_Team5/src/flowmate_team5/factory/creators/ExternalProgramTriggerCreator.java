package flowmate_team5.factory.creators;

import flowmate_team5.factory.CreatorTrigger;
import flowmate_team5.models.Trigger;
import flowmate_team5.models.triggers.ExternalProgramTrigger;

public class ExternalProgramTriggerCreator implements CreatorTrigger {

    /**
     * This method is required by the CreatorTrigger interface.
     * Creates a trigger with an empty command to satisfy the compiler.
     */
    @Override
    public Trigger createTrigger() {
        return new ExternalProgramTrigger("");
    }

    /**
     * Specific method to create the trigger with a command.
     * Used by the Controller.
     */
    public Trigger createTrigger(String commandLine) {
        return new ExternalProgramTrigger(commandLine);
    }
}