package flowmate_team5.factory.creators;

import flowmate_team5.factory.CreatorTrigger;
import flowmate_team5.models.Trigger;
import flowmate_team5.models.triggers.DayOfTheYearTrigger;

import java.time.LocalDate;

/* Factory implementation for creating instances of DayOfTheYearTrigger. */
public class DayOfTheYearTriggerCreator implements CreatorTrigger {

    /* Creates a new DayOfTheYearTrigger initialized with the current system date. */
    @Override
    public Trigger createTrigger() {
        LocalDate now = LocalDate.now();
        return new DayOfTheYearTrigger(now.getYear(), now.getMonth(), now.getDayOfMonth());
    }
}