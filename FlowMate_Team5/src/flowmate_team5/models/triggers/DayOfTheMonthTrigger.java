package flowmate_team5.models.triggers;

import flowmate_team5.models.Trigger;

import java.time.LocalDate;

public class DayOfTheMonthTrigger implements Trigger {

    private int dayOfMonth;

    public DayOfTheMonthTrigger() {
        // default constructor required by Factory Method
    }

    // ===== SETTER =====

    public void setDayOfMonth(int dayOfMonth) {
        if (dayOfMonth < 1 || dayOfMonth > 31) {
            throw new IllegalArgumentException(
                    "Day of month must be between 1 and 31"
            );
        }
        this.dayOfMonth = dayOfMonth;
    }

    // ===== TRIGGER LOGIC =====

    @Override
    public boolean isTriggered() {
        int today = LocalDate.now().getDayOfMonth();
        return today == dayOfMonth;
    }
}
