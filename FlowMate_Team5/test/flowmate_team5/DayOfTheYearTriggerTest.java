package flowmate_team5;

import org.junit.jupiter.api.Test;
import flowmate_team5.models.triggers.DayOfTheYearTrigger;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;
class DayOfTheYearTriggerTest {

    @Test
    void testTriggerToday() {
        LocalDate now = LocalDate.now();
        DayOfTheYearTrigger trigger = new DayOfTheYearTrigger(now.getMonth(), now.getDayOfMonth());
        assertTrue(trigger.isTriggered());
    }

    @Test
    void testTriggerWrongDate() {
        LocalDate now = LocalDate.now();
        LocalDate future = now.plusDays(1);
        DayOfTheYearTrigger trigger = new DayOfTheYearTrigger(future.getMonth(), future.getDayOfMonth());
        assertFalse(trigger.isTriggered());
    }
}
