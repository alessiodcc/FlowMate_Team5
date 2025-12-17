package flowmate_team5.factory.creators;

import flowmate_team5.models.triggers.DayOfTheWeekTrigger;
import flowmate_team5.models.Trigger;
import flowmate_team5.factory.CreatorTrigger;

public class DayOfTheWeekTriggerCreator implements CreatorTrigger {

    @Override
    public Trigger createTrigger() {
        // Instantiates the specific product (DayOfTheWeekTrigger)
        return new DayOfTheWeekTrigger();
    }
}
