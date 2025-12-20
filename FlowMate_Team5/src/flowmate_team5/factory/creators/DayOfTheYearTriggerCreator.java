package flowmate_team5.factory.creators;

import flowmate_team5.factory.CreatorTrigger;
import flowmate_team5.models.Trigger;
import flowmate_team5.models.triggers.DayOfTheYearTrigger;
import java.time.Month;

public class DayOfTheYearTriggerCreator implements CreatorTrigger {

    @Override
    public Trigger createTrigger() {
        // Returns a default instance (January 1st)
        return new DayOfTheYearTrigger();
    }

    /**
     * Helper to create with specific values.
     */
    public Trigger createTrigger(Month month, int day) {
        return new DayOfTheYearTrigger(month, day);
    }
}