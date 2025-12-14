package flowmate_team5;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

public class TemporalTrigger implements Trigger, Serializable {

    private String triggerName;
    // Transient field requires custom serialization logic
    transient private LocalTime timeToTrigger;
    private boolean hasTriggered = false;

    public TemporalTrigger() {
        // Empty constructor (required for Factory Method)
    }

    public String getTriggerName() {
        return triggerName;
    }

    public void setTriggerName(String triggerName) {
        this.triggerName = triggerName;
    }

    public LocalTime getTimeToTrigger() {
        return timeToTrigger;
    }

    public void setTimeToTrigger(LocalTime timeToTrigger) {
        // Truncate input to minutes to ignore seconds during comparison
        this.timeToTrigger = timeToTrigger.truncatedTo(ChronoUnit.MINUTES);
    }

    private void writeObject(ObjectOutputStream oos) throws IOException {
        oos.defaultWriteObject();
        // Manually serialize the LocalTime as seconds of the day
        oos.writeLong(timeToTrigger.toSecondOfDay());
    }

    @Override
    public boolean isTriggered() {
        // Get current time truncated to minutes for precise comparison
        LocalTime currentTime = LocalTime.now().truncatedTo(ChronoUnit.MINUTES);

        if (currentTime.equals(timeToTrigger)) {
            // Check flag to ensure trigger fires only once within the minute
            if (!hasTriggered) {
                hasTriggered = true;
                return true;
            }
            return false;
        } else {
            // Reset flag when the time no longer matches
            hasTriggered = false;
            return false;
        }
    }
}