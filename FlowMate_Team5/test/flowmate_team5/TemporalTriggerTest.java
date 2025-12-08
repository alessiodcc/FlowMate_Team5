/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package flowmate_team5;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

/**
 *
 * @author Alessio
 */
public class TemporalTriggerTest {
    
    /*
        The isTriggeredEqualitySuccessShouldBeTrue test verifies if the method isTriggered actually returns true when the condition is verified
    */
    @Test
    void isTriggeredEqualitySuccessShouldBeTrue(){
        TemporalTrigger tt = new TemporalTrigger("Test temporal trigger", LocalTime.now().truncatedTo(ChronoUnit.MINUTES));
        assertTrue(tt.isTriggered());
    }

    /*
        The isTriggeredEqualitySuccessShouldBeFalse test verifies if the method isTriggered actually returns false when the condition is not verified
    */
    @Test
    void isTriggeredEqualitySuccessShouldBeFalse(){
        TemporalTrigger tt = new TemporalTrigger("Test temporal trigger", LocalTime.now().plusMinutes(1).truncatedTo(ChronoUnit.MINUTES));
        assertFalse(tt.isTriggered());
    }
    
}
