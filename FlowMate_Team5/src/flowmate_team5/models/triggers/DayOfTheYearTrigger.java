package flowmate_team5.models.triggers;

import flowmate_team5.models.Trigger;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.Month;

/**
 * Trigger implementation that activates when the current date
 * matches a configured year, month, and day.
 */
public class DayOfTheYearTrigger implements Trigger, Serializable {

    private int year;
    private Month month;
    private int dayOfMonth;

    /**
     * Full constructor.
     */
    public DayOfTheYearTrigger(int year, Month month, int dayOfMonth) {
        this.year = year;
        this.month = month;
        this.dayOfMonth = dayOfMonth;
    }

    /**
     * Default constructor required for Factory Method.
     * Initializes the trigger to January 1st of the current year.
     */
    public DayOfTheYearTrigger() {
        LocalDate today = LocalDate.now();
        this.year = today.getYear();
        this.month = Month.JANUARY;
        this.dayOfMonth = 1;
    }

    // ---------------- SETTERS ----------------

    public void setYear(int year) {
        this.year = year;
    }

    public void setMonth(Month month) {
        this.month = month;
    }

    public void setDayOfMonth(int dayOfMonth) {
        this.dayOfMonth = dayOfMonth;
    }

    // ---------------- GETTERS ----------------

    public int getYear() {
        return year;
    }

    public Month getMonth() {
        return month;
    }

    public int getDayOfMonth() {
        return dayOfMonth;
    }

    // ---------------- TRIGGER LOGIC ----------------

    @Override
    public boolean isTriggered() {
        LocalDate today = LocalDate.now();
        return today.getYear() == year
                && today.getMonth() == month
                && today.getDayOfMonth() == dayOfMonth;
    }

    // ---------------- DEBUG ----------------

    @Override
    public String toString() {
        return "Day Trigger: " + dayOfMonth + " " + month + " " + year;
    }
}
