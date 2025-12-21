package flowmate_team5.models.triggers;

import flowmate_team5.models.Trigger;
import java.time.LocalDate;

/**
 * Trigger that fires when the current day of the month
 * matches the configured day.
 */
public class DayOfTheMonthTrigger implements Trigger {

    // Day of the month that must match today's date
    private int dayOfMonth;

    /**
     * Default constructor.
     * Required by the Factory Method pattern.
     */
    public DayOfTheMonthTrigger() {
    }


    /**
     * Sets the day of the month for this trigger.
     *
     * @param dayOfMonth the day of the month to be set (1–31)
     * @throws IllegalArgumentException if the value is out of range
     */
    public void setDayOfMonth(int dayOfMonth) {
        if (dayOfMonth < 1 || dayOfMonth > 31) {
            throw new IllegalArgumentException(
                    "Day of month must be between 1 and 31"
            );
        }
        this.dayOfMonth = dayOfMonth;
    }

    /**
     * Checks whether the trigger condition is satisfied.
     *
     * @return true if today's day of the month matches the configured value,
     *         false otherwise
     */
    @Override
    public boolean isTriggered() {
        int today = LocalDate.now().getDayOfMonth();
        return today == dayOfMonth;
    }
}
