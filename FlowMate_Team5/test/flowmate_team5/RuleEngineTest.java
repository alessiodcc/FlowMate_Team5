package flowmate_team5;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.ConcurrentModificationException;
import static org.junit.jupiter.api.Assertions.*;

public class RuleEngineTest {

    private RuleEngine engineForTesting;
    private Rule testRule1;
    private Rule testRule2;

    /**
     * Mock Trigger implementation that always returns FALSE by default.
     * This is used to create Rule objects without activating them during setup.
     */
    private static class MockTrigger implements Trigger {
        @Override
        public boolean isTriggered() {
            return false;
        }
    }

    /**
     * Mock Action implementation. Its 'execute' method is empty as we only test
     * the RuleEngine's list management here, not the execution logic.
     */
    private static class MockAction implements Action {
        @Override
        public void execute() {

        }
    }

    @BeforeEach
    void setUp() {
        // Initialize RuleEngine without starting the scheduler (essential for testing list state).
        // Uses the Singleton accessor for testing purposes.
        engineForTesting = RuleEngine.getInstance(false);

        MockTrigger mockTrigger = new MockTrigger();
        MockAction mockAction = new MockAction();

        testRule1 = new Rule("TestRule-A", mockTrigger, mockAction);
        testRule2 = new Rule("TestRule-B", mockTrigger, mockAction);
    }

    /**
     * Verifies adding a NULL rule. Standard ArrayList behavior should be preserved,
     * allowing null elements unless explicitly disallowed by the RuleEngine implementation.
     */
    @Test
    void testAddNullRule() {
        assertDoesNotThrow(() -> engineForTesting.addRule(null),
                "addRule(null) should not throw exceptions if the internal implementation uses ArrayList.");

        assertTrue(engineForTesting.getRules().contains(null),
                "The list must contain the null element after addition.");
        assertEquals(1, engineForTesting.getRules().size());
    }

    /**
     * Verifies adding the same rule multiple times (duplicate rules).
     * Standard ArrayList behavior allows duplicates.
     */
    @Test
    void testAddDuplicateRule() {

        engineForTesting.addRule(testRule1);
        engineForTesting.addRule(testRule1);

        assertEquals(2, engineForTesting.getRules().size(),
                "The list must allow the addition of duplicate rules.");
    }

    /**
     * Verifies that the list returned by getRules() is a mutable reference, allowing external
     * modifications that affect the internal state of the RuleEngine. If this test passes,
     * the internal list is NOT protected (e.g., not an unmodifiable list).
     */
    @Test
    void testGetRulesReturnsMutableReference() {
        engineForTesting.addRule(testRule1);

        List<Rule> rulesReference = engineForTesting.getRules();

        rulesReference.add(testRule2);

        assertEquals(2, engineForTesting.getRules().size(),
                "The modification to the external list modified the internal list.");
        assertTrue(engineForTesting.getRules().contains(testRule2),
                "The RuleEngine object did not protect its internal list.");
    }


    /**
     * Verifies the successful addition and counting of multiple distinct rules.
     */
    @Test
    void testAddMultipleRules() {
        engineForTesting.addRule(testRule1);
        engineForTesting.addRule(testRule2);

        List<Rule> rules = engineForTesting.getRules();
        assertEquals(2, rules.size(), "There should be exactly two rules.");
        assertTrue(rules.contains(testRule1), "The first rule must be present.");
        assertTrue(rules.contains(testRule2), "The second rule must be present.");
    }

    /**
     * Verifies that the list returned by getRules() contains the correct objects (by reference).
     */
    @Test
    void testGetRulesReturnsRulesList() {
        engineForTesting.addRule(testRule1);
        List<Rule> retrievedRules = engineForTesting.getRules();
        assertEquals(1, retrievedRules.size());
        assertSame(testRule1, retrievedRules.get(0));
    }

    /**
     * Verifies that the setup method correctly initializes the internal rule list
     * when using the test-specific Singleton accessor.
     */
    @Test
    void testConstructorWithFalseFlagInitializesEmptyList() {
        assertNotNull(engineForTesting.getRules(), "The rules list must not be null.");
        assertTrue(engineForTesting.getRules().isEmpty(), "The list must be empty initially.");
    }
}