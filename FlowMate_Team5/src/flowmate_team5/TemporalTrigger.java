/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package flowmate_team5;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

/**
 *
 * @author Alessio
 */
public class TemporalTrigger implements Trigger, Serializable {
    private String TriggerName; // Name of the trigger
    transient LocalTime timeToTrigger; // the time that we want the trigger to fire
    private boolean hasTriggered = false; // indicates if the trigger has already fired

    // constructor that initializes all the Trigger's fields
    public TemporalTrigger(String TriggerName, LocalTime timeToTrigger) {
        this.TriggerName = TriggerName;
        this.timeToTrigger = timeToTrigger.truncatedTo(ChronoUnit.MINUTES);
    }

    // Getter and Setter methods:
    public String getTriggerName() {
        return TriggerName;
    }

    public void setTriggerName(String TriggerName) {
        this.TriggerName = TriggerName;
    }

    public LocalTime getTimeToTrigger() {
        return timeToTrigger;
    }

    public void setTimeToTrigger(LocalTime timeToTrigger) {
        this.timeToTrigger = timeToTrigger;
    }

    private void writeObject(ObjectOutputStream oos) throws IOException {
        // 1. Scrive i campi NON transienti (TriggerName, hasTriggered)
        oos.defaultWriteObject();

        oos.writeLong(timeToTrigger.toSecondOfDay());
    }

    @Override
    public boolean isTriggered() {
        LocalTime currentTime = LocalTime.now().truncatedTo(ChronoUnit.MINUTES);

        if (currentTime.equals(timeToTrigger)) {
            // if the timer has not already triggered
            if (!hasTriggered) {
                hasTriggered = true;
                return true;
            } else {
                return false;
            }
        } else {
            if (hasTriggered) {
                hasTriggered = false;
            }
            return false;
        }
    }
}