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
     * Verifica che il trigger sia attivo nel giorno corrente.
     */
    @Test
    void testIsTriggered_Today() {
        int today = LocalDate.now().getDayOfMonth();

        CreatorTrigger creator = new DayOfTheMonthTriggerCreator();
        Trigger trigger = creator.createTrigger();

        DayOfTheMonthTrigger dayTrigger = (DayOfTheMonthTrigger) trigger;
        dayTrigger.setDayOfMonth(today);

        assertTrue(trigger.isTriggered(),
                "Trigger should be active on the configured day of the month");
    }

    /**
     * Verifica che il trigger NON sia attivo in un giorno diverso.
     */
    @Test
    void testIsNotTriggered_DifferentDay() {
        int today = LocalDate.now().getDayOfMonth();
        int differentDay = (today == 1) ? 2 : 1;

        DayOfTheMonthTrigger trigger = new DayOfTheMonthTrigger();
        trigger.setDayOfMonth(differentDay);

        assertFalse(trigger.isTriggered(),
                "Trigger should not be active on a different day");
    }

    /**
     * Verifica che valori non validi lancino eccezione.
     */
    @Test
    void testSetDayOfMonth_InvalidLowValue() {
        DayOfTheMonthTrigger trigger = new DayOfTheMonthTrigger();

        assertThrows(IllegalArgumentException.class,
                () -> trigger.setDayOfMonth(0));
    }

    @Test
    void testSetDayOfMonth_InvalidHighValue() {
        DayOfTheMonthTrigger trigger = new DayOfTheMonthTrigger();

        assertThrows(IllegalArgumentException.class,
                () -> trigger.setDayOfMonth(32));
    }

    /**
     * Verifica che il trigger implementi correttamente l'interfaccia Trigger.
     */
    @Test
    void testImplementsTriggerInterface() {
        Trigger trigger = new DayOfTheMonthTrigger();
        assertTrue(trigger instanceof Trigger);
    }
}
