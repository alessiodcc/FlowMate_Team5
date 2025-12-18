package flowmate_team5.models.triggers;

import flowmate_team5.models.Trigger;
import java.time.LocalDate;
import java.time.Month;

/**
 * A Trigger implementation that fires if the current date matches
 * the configured month and day.
 */
public class DayOfTheYearTrigger implements Trigger {

    private final Month month;
    private final int dayOfMonth;

    public DayOfTheYearTrigger(Month month, int dayOfMonth) {
        this.month = month;
        this.dayOfMonth = dayOfMonth;
    }

    @Override
    public boolean isTriggered() {
        LocalDate today = LocalDate.now();
        return today.getMonth() == this.month && today.getDayOfMonth() == this.dayOfMonth;
    }

    @Override
    public String toString() {
        return "Day Trigger: " + month + " " + dayOfMonth;
    }
}