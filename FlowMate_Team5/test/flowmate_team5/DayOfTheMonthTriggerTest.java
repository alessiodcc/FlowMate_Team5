package flowmate_team5;

import flowmate_team5.factory.CreatorTrigger;
import flowmate_team5.factory.creators.DayOfTheMonthTriggerCreator;
import flowmate_team5.models.Trigger;
import flowmate_team5.models.triggers.DayOfTheMonthTrigger;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class DayOfTheMonthTriggerTest {

    /**
     * Verifies that the trigger is active on the current day of the month.
     */
    @Test
    void testIsTriggered_Today() {
        int today = LocalDate.now().getDayOfMonth();

        // Create the trigger using the Factory Method
        CreatorTrigger creator = new DayOfTheMonthTriggerCreator();
        Trigger trigger = creator.createTrigger();

        // Configure the trigger with today's day
        DayOfTheMonthTrigger dayTrigger = (DayOfTheMonthTrigger) trigger;
        dayTrigger.setDayOfMonth(today);

        // The trigger should be active today
        assertTrue(trigger.isTriggered(),
                "Trigger should be active on the configured day of the month");
    }

    /**
     * Verifies that the trigger is NOT active on a different day.
     */
    @Test
    void testIsNotTriggered_DifferentDay() {
        int today = LocalDate.now().getDayOfMonth();
        int differentDay = (today == 1) ? 2 : 1;

        // Create and configure the trigger with a different day
        DayOfTheMonthTrigger trigger = new DayOfTheMonthTrigger();
        trigger.setDayOfMonth(differentDay);

        // The trigger should not be active today
        assertFalse(trigger.isTriggered(),
                "Trigger should not be active on a different day");
    }

    /**
     * Verifies that invalid low values throw an exception.
     */
    @Test
    void testSetDayOfMonth_InvalidLowValue() {
        DayOfTheMonthTrigger trigger = new DayOfTheMonthTrigger();

        assertThrows(IllegalArgumentException.class,
                () -> trigger.setDayOfMonth(0));
    }

    /**
     * Verifies that invalid high values throw an exception.
     */
    @Test
    void testSetDayOfMonth_InvalidHighValue() {
        DayOfTheMonthTrigger trigger = new DayOfTheMonthTrigger();

        assertThrows(IllegalArgumentException.class,
                () -> trigger.setDayOfMonth(32));
    }

    /**
     * Verifies that the trigger correctly implements the Trigger interface.
     */
    @Test
    void testImplementsTriggerInterface() {
        Trigger trigger = new DayOfTheMonthTrigger();
        assertTrue(trigger instanceof Trigger);
    }
}
