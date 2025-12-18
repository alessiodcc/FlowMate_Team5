package flowmate_team5.factory.creators;

import flowmate_team5.factory.CreatorTrigger;
import flowmate_team5.models.Trigger;
import flowmate_team5.models.triggers.DayOfTheYearTrigger;
import java.time.Month;

public class DayOfTheYearTriggerCreator implements CreatorTrigger {

    /**
     * This method is required by the CreatorTrigger interface.
     * It creates a default instance (e.g., January 1st) to satisfy the compiler.
     */
    @Override
    public Trigger createTrigger() {
        return new DayOfTheYearTrigger(Month.JANUARY, 1);
    }

    /**
     * Specific method to create the trigger with user data.
     * Used by the Controller.
     */
    public Trigger createTrigger(Month month, int day) {
        return new DayOfTheYearTrigger(month, day);
    }
}