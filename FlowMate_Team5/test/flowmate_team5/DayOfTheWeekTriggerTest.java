package flowmate_team5;

import flowmate_team5.factory.creators.DayOfTheWeekTriggerCreator;
import flowmate_team5.models.triggers.DayOfTheWeekTrigger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DayOfTheWeekTriggerTest {

    private DayOfTheWeekTriggerCreator creator;
    private static final ZoneId TEST_ZONE = ZoneId.of("Europe/Paris");
    private DayOfTheWeekTrigger dotwt;

    /**
     * This method initializes all the useful variables.
     */
    @BeforeEach
    void initialSetUp() {
        this.creator = new DayOfTheWeekTriggerCreator();
        this.dotwt = (DayOfTheWeekTrigger) creator.createTrigger();
    }

    /**
     * Tests that isTriggered() returns true when the fixed day
     * matches one of the active days.
     */
    @Test
    void isTriggeredShouldBeTrue() {
        // 16/12/2025 -> TUESDAY
        Instant fixedInstant = LocalDate.of(2025, 12, 16)
                .atStartOfDay(TEST_ZONE)
                .toInstant();
        Clock fixedClock = Clock.fixed(fixedInstant, TEST_ZONE);

        dotwt.setDays(List.of(DayOfWeek.TUESDAY));
        dotwt.setClock(fixedClock);

        assertTrue(dotwt.isTriggered());
    }

    /**
     * Tests that isTriggered() returns false when the fixed day
     * does not match any active day.
     */
    @Test
    void isTriggeredShouldBeFalse() {
        // 16/12/2025 -> TUESDAY
        Instant fixedInstant = LocalDate.of(2025, 12, 16)
                .atStartOfDay(TEST_ZONE)
                .toInstant();
        Clock fixedClock = Clock.fixed(fixedInstant, TEST_ZONE);

        dotwt.setDays(List.of(DayOfWeek.WEDNESDAY));
        dotwt.setClock(fixedClock);

        assertFalse(dotwt.isTriggered());
    }
}
