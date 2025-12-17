package flowmate_team5.models.triggers;

import flowmate_team5.models.Trigger;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.time.Clock;
import java.time.DayOfWeek;
import java.time.LocalDate;

public class DayOfTheWeekTrigger implements Trigger, Serializable {
    private String triggerName = "Day of the Week Trigger";
    private DayOfWeek dayOfWeek;
    transient private Clock clock = Clock.systemDefaultZone(); // useful for the tests

    public DayOfTheWeekTrigger() {
        // Empty constructor (required for Factory Method)
    }

    /**
     *  Getter and Setter Methods
     */
    public DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(DayOfWeek dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

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
        return today.equals(dayOfWeek);
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
