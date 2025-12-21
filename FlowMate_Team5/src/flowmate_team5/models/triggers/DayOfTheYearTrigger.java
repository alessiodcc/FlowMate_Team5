package flowmate_team5.models.triggers;

import flowmate_team5.models.Trigger;
import java.time.LocalDate;
import java.time.Month;

/* Trigger implementation that activates when the current date matches a configured month and day. */
public class DayOfTheYearTrigger implements Trigger {
    private int year;
    private Month month;
    private int dayOfMonth;


    /* Initializes the trigger with a specific month and day. */
    public DayOfTheYearTrigger(int year, Month month, int dayOfMonth) {
        this.year = year;
        this.month = month;
        this.dayOfMonth = dayOfMonth;
    }

    /* Default constructor initializing the trigger to January 1st. */
    public DayOfTheYearTrigger() {
        LocalDate today = LocalDate.now();
        this.year = today.getYear();
        this.month = Month.JANUARY;
        this.dayOfMonth = 1;
    }

    /* Sets the target year for the trigger. */
    public void setYear(int year) {
        this.year = year;
    }

    /* Retrieves the currently configured year. */
    public int getYear() {
        return year;
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
        return today.getYear() == year
                && today.getMonth() == month
                && today.getDayOfMonth() == dayOfMonth;
    }

    /* Returns a string representation of the trigger state. */
    @Override
    public String toString() {
        return "Day Trigger: " + month + " " + dayOfMonth + " " + year;
    }
}