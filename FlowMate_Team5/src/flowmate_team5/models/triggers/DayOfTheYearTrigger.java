package flowmate_team5.models.triggers;

import flowmate_team5.models.Trigger;
import java.time.LocalDate;
import java.time.Month;

/* Trigger implementation that activates when the current date matches a configured month and day. */
public class DayOfTheYearTrigger implements Trigger {

    private Month month;
    private int dayOfMonth;

    /* Initializes the trigger with a specific month and day. */
    public DayOfTheYearTrigger(Month month, int dayOfMonth) {
        this.month = month;
        this.dayOfMonth = dayOfMonth;
    }

    /* Default constructor initializing the trigger to January 1st. */
    public DayOfTheYearTrigger() {
        this(Month.JANUARY, 1);
    }

    /* Sets the target month for the trigger. */
    public void setMonth(Month month) {
        this.month = month;
    }

    /* Retrieves the currently configured month. */
    public Month getMonth() {
        return month;
    }

    /* Sets the target day of the month. */
    public void setDayOfMonth(int dayOfMonth) {
        this.dayOfMonth = dayOfMonth;
    }

    /* Retrieves the currently configured day of the month. */
    public int getDayOfMonth() {
        return dayOfMonth;
    }

    /* Checks if the current system date matches the configured month and day. */
    @Override
    public boolean isTriggered() {
        LocalDate today = LocalDate.now();
        // Return true only if both month and day match the current date
        return today.getMonth() == this.month && today.getDayOfMonth() == this.dayOfMonth;
    }

    /* Returns a string representation of the trigger state. */
    @Override
    public String toString() {
        return "Day Trigger: " + month + " " + dayOfMonth;
    }
}