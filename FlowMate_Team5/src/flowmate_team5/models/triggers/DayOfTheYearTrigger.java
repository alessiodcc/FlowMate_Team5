package flowmate_team5.models.triggers;

import flowmate_team5.models.Trigger;
import java.time.LocalDate;
import java.time.Month;

/**
 * Trigger that fires if the current date matches the configured month and day.
 * Updated to be mutable for GUI configuration (US17).
 */
public class DayOfTheYearTrigger implements Trigger {

    private Month month;
    private int dayOfMonth;

    public DayOfTheYearTrigger(Month month, int dayOfMonth) {
        this.month = month;
        this.dayOfMonth = dayOfMonth;
    }

    // Default constructor (defaults to January 1st)
    public DayOfTheYearTrigger() {
        this(Month.JANUARY, 1);
    }

    public void setMonth(Month month) {
        this.month = month;
    }

    public Month getMonth() {
        return month;
    }

    public void setDayOfMonth(int dayOfMonth) {
        this.dayOfMonth = dayOfMonth;
    }

    public int getDayOfMonth() {
        return dayOfMonth;
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