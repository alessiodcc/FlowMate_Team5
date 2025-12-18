package flowmate_team5;

import flowmate_team5.models.Counter;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CounterTest {

    @Test
    void testInitialization() {
        Counter c = new Counter("Test", 10);
        assertEquals(10, c.getValue());
        assertEquals("Test", c.getName());
    }

    @Test
    void testIncrementAndDecrement() {
        Counter c = new Counter("Test", 0);
        c.increment();
        assertEquals(1, c.getValue());
        c.decrement();
        assertEquals(0, c.getValue());
    }
}