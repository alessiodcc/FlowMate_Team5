package flowmate_team5.factory.creators;

import flowmate_team5.factory.CreatorTrigger;
import flowmate_team5.models.Trigger;
import flowmate_team5.models.triggers.DayOfTheMonthTrigger;

/**
 * Factory class responsible for creating instances of DayOfTheMonthTrigger.
 *
 * This class implements the CreatorTrigger interface and follows
 * the Factory Method pattern, encapsulating the trigger creation logic.
 */
public class DayOfTheMonthTriggerCreator implements CreatorTrigger {

    /**
     * Creates and returns a new DayOfTheMonthTrigger instance.
     *
     * @return a new DayOfTheMonthTrigger
     */
    @Override
    public Trigger createTrigger() {
        return new DayOfTheMonthTrigger();
    }
}
