package flowmate_team5;

import flowmate_team5.core.Rule;
import flowmate_team5.core.RuleEngine;
import flowmate_team5.factory.CreatorAction;
import flowmate_team5.factory.creators.PlayAudioActionCreator;
import flowmate_team5.models.Action;
import flowmate_team5.models.Trigger;
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
        // Obtain RuleEngine singleton in test mode
        engineForTesting = RuleEngine.getInstance(false);

        // IMPORTANT: reset singleton state between tests
        engineForTesting.getRules().clear();

        Trigger trigger = new MockTrigger();

        CreatorAction creator = new PlayAudioActionCreator();
        Action action1 = creator.createAction();
        Action action2 = creator.createAction();

        testRule1 = new Rule("TestRule-A", trigger, action1);
        testRule2 = new Rule("TestRule-B", trigger, action2);
    }

    /**
     * Verifies that the RuleEngine initializes with an empty rule list.
     */
    @Test
    void testInitialRulesListIsEmpty() {
        assertTrue(engineForTesting.getRules().isEmpty(),
                "Rules list should be empty at initialization.");
    }

    /**
     * Verifies that adding a null rule does not crash the system.
     */
    @Test
    void testAddNullRuleDoesNotCrash() {
        assertDoesNotThrow(() -> engineForTesting.addRule(null),
                "addRule(null) should not throw exceptions.");
    }

    /**
     * Verifies that duplicate rules are allowed.
     */
    @Test
    void testAddDuplicateRuleAllowed() {
        engineForTesting.addRule(testRule1);
        engineForTesting.addRule(testRule1);

        assertEquals(2, engineForTesting.getRules().size(),
                "Duplicate rules should be allowed.");
    }

    /**
     * Verifies successful addition of multiple distinct rules.
     */
    @Test
    void testAddMultipleDistinctRules() {
        engineForTesting.addRule(testRule1);
        engineForTesting.addRule(testRule2);

        List<Rule> rules = engineForTesting.getRules();
        assertEquals(2, rules.size(),
                "There should be exactly two rules.");
        assertTrue(rules.contains(testRule1),
                "Rule 1 should be present.");
        assertTrue(rules.contains(testRule2),
                "Rule 2 should be present.");
    }

    /**
     * Verifies that getRules() returns a mutable list.
     */
    @Test
    void testGetRulesReturnsMutableList() {
        engineForTesting.addRule(testRule1);

        List<Rule> rules = engineForTesting.getRules();

        assertDoesNotThrow(() -> rules.add(testRule2),
                "The returned rules list should be mutable.");
    }

    /**
     * Verifies that getRules() returns the correct rule reference.
     */
    @Test
    void testGetRulesReturnsCorrectRuleReference() {
        engineForTesting.addRule(testRule1);

        List<Rule> retrievedRules = engineForTesting.getRules();

        assertEquals(1, retrievedRules.size(),
                "Exactly one rule should be present.");
        assertSame(testRule1, retrievedRules.get(0),
                "The returned rule should be the same object reference.");
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
                "Rule should be removed after deletion.");
    }

    /**
     * Verifies that deleting a non-existing rule does not throw exceptions.
     */
    @Test
    void testDeleteNonExistingRuleDoesNotThrow() {
        assertDoesNotThrow(() -> engineForTesting.deleteRule(testRule1),
                "Deleting a non-existing rule should not throw exceptions.");
    }
}
