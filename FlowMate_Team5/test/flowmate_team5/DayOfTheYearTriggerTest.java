package flowmate_team5;

import org.junit.jupiter.api.Test;
import flowmate_team5.models.triggers.DayOfTheYearTrigger;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

/* Unit tests verifying the logic of the DayOfTheYearTrigger class. */
class DayOfTheYearTriggerTest {

    /* Tests that the trigger activates correctly when set to the current date. */
    @Test
    void testTriggerToday() {
        LocalDate now = LocalDate.now();
        // Initialize trigger with today's month and day to ensure a match
        DayOfTheYearTrigger trigger = new DayOfTheYearTrigger(now.getMonth(), now.getDayOfMonth());
        assertTrue(trigger.isTriggered());
    }

    /* Tests that the trigger does not activate when set to a future date. */
    @Test
    void testTriggerWrongDate() {
        LocalDate now = LocalDate.now();
        // Calculate a future date to simulate a mismatch scenario
        LocalDate future = now.plusDays(1);
        DayOfTheYearTrigger trigger = new DayOfTheYearTrigger(future.getMonth(), future.getDayOfMonth());
        assertFalse(trigger.isTriggered());
    }
}