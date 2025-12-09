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
        engineForTesting = new RuleEngine(false);

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
                "addRule(null) non deve lanciare eccezioni se l'implementazione interna usa ArrayList.");

        assertTrue(engineForTesting.getRules().contains(null),
                "La lista deve contenere l'elemento null dopo l'aggiunta.");
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
                "La lista deve permettere l'aggiunta di regole duplicate.");
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
                "La modifica alla lista esterna ha modificato la lista interna.");
        assertTrue(engineForTesting.getRules().contains(testRule2),
                "L'oggetto RuleEngine non ha protetto la sua lista interna.");
    }


    /**
     * Verifies the successful addition and counting of multiple distinct rules.
     */
    @Test
    void testAddMultipleRules() {
        engineForTesting.addRule(testRule1);
        engineForTesting.addRule(testRule2);

        List<Rule> rules = engineForTesting.getRules();
        assertEquals(2, rules.size(), "Dovrebbero esserci esattamente due regole.");
        assertTrue(rules.contains(testRule1), "La prima regola deve essere presente.");
        assertTrue(rules.contains(testRule2), "La seconda regola deve essere presente.");
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
     * Verifies that the constructor used for testing (RuleEngine(false)) correctly
     * initializes the internal rule list.
     */
    @Test
    void testConstructorWithFalseFlagInitializesEmptyList() {
        assertNotNull(engineForTesting.getRules(), "La lista delle regole non deve essere nulla.");
        assertTrue(engineForTesting.getRules().isEmpty(), "La lista deve essere vuota all'inizio.");
    }
}