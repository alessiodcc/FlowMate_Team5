package flowmate_team5;

import flowmate_team5.core.Rule;
import flowmate_team5.models.Action;
import flowmate_team5.models.Trigger;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/* Unit tests focusing on the state management (Active/Inactive) of the Rule class. */
public class RuleStateTest {

    /* Mock Trigger implementation that always returns true for testing purposes. */
    private class MockTriggerTrue implements Trigger {
        @Override
        public boolean isTriggered() { return true; }
    }

    /* Mock Action implementation used to verify execution counts. */
    private class MockActionCount implements Action {
        int executionCount = 0;
        @Override
        public void execute() { executionCount++; }
    }

    /* Verifies that an active rule executes its action when the trigger condition is met. */
    @Test
    public void testRuleExecution_WhenActive_ShouldExecute() {
        MockTriggerTrue trigger = new MockTriggerTrue();
        MockActionCount action = new MockActionCount();
        Rule rule = new Rule("ActiveRule", trigger, action);

        // Explicitly set the rule to active state
        rule.setActive(true);

        // Trigger the rule check cycle
        rule.check();

        // Assert that the action was executed exactly once
        assertEquals(1, action.executionCount, "Action should execute when rule is active.");
    }

    /* Verifies that an inactive rule prevents action execution even if the trigger condition is met. */
    @Test
    public void testRuleExecution_WhenInactive_ShouldNotExecute() {
        MockTriggerTrue trigger = new MockTriggerTrue();
        MockActionCount action = new MockActionCount();
        Rule rule = new Rule("InactiveRule", trigger, action);

        // Deactivate the rule to prevent execution
        rule.setActive(false);

        // Attempt to trigger the rule
        rule.check();

        // Assert that the action was NOT executed
        assertEquals(0, action.executionCount, "Action should NOT execute when rule is inactive.");
    }

    /* Tests the boolean state toggling mechanism of the Rule class. */
    @Test
    public void testRuleState_Toggling() {
        Rule rule = new Rule("ToggleRule", null, null);

        // Check default state (should be active upon creation)
        assertTrue(rule.isActive(), "Rule should be active by default.");

        // Toggle state to inactive and verify
        rule.setActive(false);
        assertFalse(rule.isActive(), "Rule should be inactive after setting false.");

        // Toggle state back to active and verify
        rule.setActive(true);
        assertTrue(rule.isActive(), "Rule should be active after setting true.");
    }
}