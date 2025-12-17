package flowmate_team5;

import flowmate_team5.factory.creators.DayOfTheWeekTriggerCreator;
import flowmate_team5.models.triggers.DayOfTheWeekTrigger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.*;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DayOfTheWeekTriggerTest {
    DayOfTheWeekTriggerCreator creator;
    private static final ZoneId TEST_ZONE = ZoneId.of("Europe/Paris");
    private DayOfTheWeekTrigger dotwt;

    /**
     * This method initializes all the useful variables.
     */
    @BeforeEach
    void InitialSetUp() {
        this.creator = new DayOfTheWeekTriggerCreator();
        dotwt = (DayOfTheWeekTrigger) creator.createTrigger();
    }

    /**
     * This method tests the isTriggered method, checking if it returns true
     * when the current day of the week matches the one set in the trigger.
     * To ensure consistency regardless of when the test is run, a Clock
     * is used to "fix" the system time to a specific Tuesday.
     */
    @Test
    void isTriggeredShouldBeTrue() {
        // 16/12/2025 -> TUESDAY
        Instant fixedInstant = LocalDate.of(2025, 12, 16).atStartOfDay(TEST_ZONE).toInstant();
        Clock fixedClock = Clock.fixed(fixedInstant, TEST_ZONE);

        dotwt.setDayOfWeek(DayOfWeek.TUESDAY);
        dotwt.setClock(fixedClock);

        assertTrue(dotwt.isTriggered());
    }

    @Test
    void isTriggeredShouldBeFalse() {
        Instant fixedInstant = LocalDate.of(2025, 12, 16).atStartOfDay(TEST_ZONE).toInstant();
        Clock fixedClock = Clock.fixed(fixedInstant, TEST_ZONE);

        dotwt.setDayOfWeek(DayOfWeek.WEDNESDAY);
        dotwt.setClock(fixedClock);

        assertFalse(dotwt.isTriggered());
    }

}
