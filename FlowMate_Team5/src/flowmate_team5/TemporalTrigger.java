/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package flowmate_team5;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

/**
 *
 * @author Alessio
 */
public class TemporalTrigger implements Trigger{
    private String TriggerName; // Name of the trigger
    private LocalTime timeToTrigger; // the time that we want the trigger to fire

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
    
    @Override
    public boolean isTriggered() {
        LocalTime currentTime = LocalTime.now().truncatedTo(ChronoUnit.MINUTES);
        // If the time assigned to the trigger corresponds to the current date-time from the system clock in the default time-zone.
        if(timeToTrigger.equals(currentTime)) 
            return true;
        else
            return false;
    }
}
