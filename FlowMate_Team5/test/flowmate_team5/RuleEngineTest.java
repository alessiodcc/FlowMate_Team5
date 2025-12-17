package flowmate_team5;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class RuleEngineTest {

    private RuleEngine engineForTesting;
    private Rule testRule1;
    private Rule testRule2;

    /**
     * Mock Trigger implementation.
     */
    private static class MockTrigger implements Trigger {
        @Override
        public boolean isTriggered() {
            return false;
        }
    }

    @BeforeEach
    void setUp() {
        // Obtain RuleEngine instance without starting scheduler (test mode)
        engineForTesting = RuleEngine.getInstance(false);

        Trigger mockTrigger = new MockTrigger();

        // Use Factory Method to create Actions
        CreatorAction creator = new PlayAudioActionCreator();
        Action action1 = creator.createAction();
        Action action2 = creator.createAction();

        testRule1 = new Rule("TestRule-A", mockTrigger, action1);
        testRule2 = new Rule("TestRule-B", mockTrigger, action2);
    }

    /**
     * Verifies that adding a null rule is NOT allowed.
     */
    @Test
    void testAddNullRuleThrowsException() {
        assertThrows(IllegalArgumentException.class,
                () -> engineForTesting.addRule(null),
                "addRule(null) should throw IllegalArgumentException.");
    }

    /**
     * Verifies that duplicate rules are NOT allowed.
     */
    @Test
    void testAddDuplicateRuleThrowsException() {
        engineForTesting.addRule(testRule1);

        assertThrows(IllegalStateException.class,
                () -> engineForTesting.addRule(testRule1),
                "Adding the same rule twice should throw IllegalStateException.");
    }

    /**
     * Verifies successful addition of multiple distinct rules.
     */
    @Test
    void testAddMultipleDistinctRules() {
        engineForTesting.addRule(testRule1);
        engineForTesting.addRule(testRule2);

        List<Rule> rules = engineForTesting.getRules();
        assertEquals(2, rules.size(), "There should be exactly two rules.");
        assertTrue(rules.contains(testRule1), "Rule 1 should be present.");
        assertTrue(rules.contains(testRule2), "Rule 2 should be present.");
    }

    /**
     * Verifies that getRules() returns an unmodifiable list.
     */
    @Test
    void testGetRulesReturnsUnmodifiableList() {
        engineForTesting.addRule(testRule1);

        List<Rule> rules = engineForTesting.getRules();

        assertThrows(UnsupportedOperationException.class,
                () -> rules.add(testRule2),
                "The returned rules list must be unmodifiable.");
    }

    /**
     * Verifies that the returned rules list contains the correct rule references.
     */
    @Test
    void testGetRulesReturnsCorrectRuleReference() {
        engineForTesting.addRule(testRule1);

        List<Rule> retrievedRules = engineForTesting.getRules();

        assertEquals(1, retrievedRules.size(), "Exactly one rule should be present.");
        assertSame(testRule1, retrievedRules.get(0),
                "The returned rule should be the same object reference.");
    }

    /**
     * Verifies that the RuleEngine initializes with an empty rule list in test mode.
     */
    @Test
    void testConstructorInitializesEmptyList() {
        assertNotNull(engineForTesting.getRules(), "Rules list must not be null.");
        assertTrue(engineForTesting.getRules().isEmpty(),
                "Rules list should be empty at initialization.");
    }

    /**
     * Verifies that deleteRule() removes an existing rule.
     */
    @Test
    void testDeleteRule() {
        engineForTesting.addRule(testRule1);
        assertTrue(engineForTesting.getRules().contains(testRule1),
                "Rule should exist before deletion.");

        engineForTesting.deleteRule(testRule1);

        assertFalse(engineForTesting.getRules().contains(testRule1),
                "Rule should be removed after deleteRule().");
        assertTrue(engineForTesting.getRules().isEmpty(),
                "Rules list should be empty after deletion.");
    }

    /**
     * Verifies that deleting a non-existing rule does not corrupt the state.
     */
    @Test
    void testDeleteNonExistingRule() {
        assertDoesNotThrow(() -> engineForTesting.deleteRule(testRule1),
                "Deleting a non-existing rule should not throw exceptions.");
    }
}
