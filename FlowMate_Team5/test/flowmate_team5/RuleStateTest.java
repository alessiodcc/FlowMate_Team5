package flowmate_team5;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class RuleStateTest {

    // Mock Trigger that is always TRUE
    private class MockTriggerTrue implements Trigger {
        @Override
        public boolean isTriggered() { return true; }
    }

    // Mock Action to count executions
    private class MockActionCount implements Action {
        int executionCount = 0;
        @Override
        public void execute() { executionCount++; }
    }

    @Test
    public void testRuleExecution_WhenActive_ShouldExecute() {
        MockTriggerTrue trigger = new MockTriggerTrue();
        MockActionCount action = new MockActionCount();
        Rule rule = new Rule("ActiveRule", trigger, action);

        // Ensure it is active (Task 8.1 logic)
        rule.setActive(true);

        rule.check();

        assertEquals(1, action.executionCount, "Action should execute when rule is active.");
    }

    @Test
    public void testRuleExecution_WhenInactive_ShouldNotExecute() {
        MockTriggerTrue trigger = new MockTriggerTrue();
        MockActionCount action = new MockActionCount();
        Rule rule = new Rule("InactiveRule", trigger, action);

        // Deactivate rule (Task 8.1 logic)
        rule.setActive(false);

        rule.check();

        assertEquals(0, action.executionCount, "Action should NOT execute when rule is inactive.");
    }

    @Test
    public void testRuleState_Toggling() {
        Rule rule = new Rule("ToggleRule", null, null);
        assertTrue(rule.isActive(), "Rule should be active by default.");

        rule.setActive(false);
        assertFalse(rule.isActive(), "Rule should be inactive after setting false.");

        rule.setActive(true);
        assertTrue(rule.isActive(), "Rule should be active after setting true.");
    }
}