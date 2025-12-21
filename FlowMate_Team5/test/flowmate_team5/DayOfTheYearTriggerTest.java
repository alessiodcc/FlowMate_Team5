package flowmate_team5;

import flowmate_team5.models.triggers.DayOfTheYearTrigger;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

/* Unit tests verifying the logic of the DayOfTheYearTrigger class. */
class DayOfTheYearTriggerTest {

    /* Tests that the trigger activates correctly when configured with the current system date. */
    @Test
    void testTriggerToday() {
        LocalDate now = LocalDate.now();
        // Initialize trigger with the current year, month, and day
        DayOfTheYearTrigger trigger = new DayOfTheYearTrigger(now.getYear(), now.getMonth(), now.getDayOfMonth());
        assertTrue(trigger.isTriggered(), "Trigger should verify true for today's date");
    }

    /* Verifies that the trigger does not activate when configured with a future date. */
    @Test
    void testTriggerWrongDate() {
        LocalDate now = LocalDate.now();
        LocalDate future = now.plusDays(1);

        // Initialize trigger with a future date to simulate a mismatch
        DayOfTheYearTrigger trigger = new DayOfTheYearTrigger(future.getYear(), future.getMonth(), future.getDayOfMonth());
        assertFalse(trigger.isTriggered(), "Trigger should verify false for a different date");
    }

    /* Verifies that the trigger is sensitive to the specific year configuration. */
    @Test
    void testTriggerWrongYear() {
        LocalDate now = LocalDate.now();

        // Configure trigger with correct day/month but the previous year
        DayOfTheYearTrigger trigger = new DayOfTheYearTrigger(now.getYear() - 1, now.getMonth(), now.getDayOfMonth());

        assertFalse(trigger.isTriggered(), "Trigger should verify false if the year does not match");
    }
}