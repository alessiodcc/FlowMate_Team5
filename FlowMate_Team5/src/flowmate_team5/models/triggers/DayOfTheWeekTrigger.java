package flowmate_team5.models.triggers;

import flowmate_team5.models.Trigger;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.time.Clock;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DayOfTheWeekTrigger implements Trigger, Serializable {
    private String triggerName = "Day of the Week Trigger";
    private List<DayOfWeek> activeDays = new ArrayList<>();
    transient private Clock clock = Clock.systemDefaultZone(); // useful for the tests

    public DayOfTheWeekTrigger() {
        // Empty constructor (required for Factory Method)
    }

    /**
     *  Getter and Setter Methods
     */
    public void setDays(List<DayOfWeek> days) { this.activeDays = days; }

    public List<DayOfWeek> getDays() { return activeDays; }

    public Clock getClock() {
        return clock;
    }

    public void setClock(Clock clock) {
        this.clock = clock;
    }

    /**
     *  This method checks if the actual date (obtained using the .now() method)
     *  is equal to the day selected by the user.
     */
    @Override
    public boolean isTriggered() {
        DayOfWeek today = LocalDate.now(this.clock).getDayOfWeek();
        return activeDays.contains(today);
    }

    /**
     *  The writeObject and readObject methods are necessary for this class
     *  since the Clock class does not implement Serializable.
     */
    private void writeObject(ObjectOutputStream oos) throws IOException {
        oos.defaultWriteObject();
    }

    private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
        ois.defaultReadObject();
        this.clock = Clock.systemDefaultZone();
    }
}
