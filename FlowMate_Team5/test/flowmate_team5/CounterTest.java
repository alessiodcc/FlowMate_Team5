package flowmate_team5;

import flowmate_team5.models.Counter;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/* Unit tests responsible for verifying the core functionality of the Counter model. */
class CounterTest {

    /* Verifies that the counter is correctly initialized with the specified name and value. */
    @Test
    void testInitialization() {
        // Instantiate a new counter with a specific starting value
        Counter c = new Counter("Test", 10);

        // Assert that the properties match the constructor arguments
        assertEquals(10, c.getValue());
        assertEquals("Test", c.getName());
    }

    /* Tests the increment and decrement operations to ensure state updates correctly. */
    @Test
    void testIncrementAndDecrement() {
        Counter c = new Counter("Test", 0);

        // Increment the counter and verify the value increases by one
        c.increment();
        assertEquals(1, c.getValue());

        // Decrement the counter and verify it returns to the previous value
        c.decrement();
        assertEquals(0, c.getValue());
    }
}