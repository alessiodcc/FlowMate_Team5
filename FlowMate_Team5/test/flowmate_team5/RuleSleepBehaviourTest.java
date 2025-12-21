package flowmate_team5;

import flowmate_team5.core.Rule;
import flowmate_team5.models.Action;
import flowmate_team5.models.Trigger;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the sleep / awake behaviour of a Rule.
 * These tests verify that a rule correctly handles cooldown periods
 * and activation state.
 */
public class RuleSleepBehaviourTest {

    /**
     * Manually controllable Trigger used for testing.
     * It allows explicit activation and reset.
     */
    static class TestTrigger implements Trigger {
        private boolean triggered = false;

        // Simulates the trigger firing
        void fire() {
            triggered = true;
        }

        // Resets the trigger state
        void reset() {
            triggered = false;
        }

        @Override
        public boolean isTriggered() {
            return triggered;
        }
    }

    /**
     * Fake Action that counts how many times it is executed.
     */
    static class TestAction implements Action {
        private int executions = 0;

        @Override
        public void execute() {
            executions++;
        }

        int getExecutions() {
            return executions;
        }
    }

    /**
     * Verifies that a rule does NOT execute while it is sleeping
     * after a previous execution.
     */
    @Test
    void rule_doesNotExecute_whileSleeping() {
        TestTrigger trigger = new TestTrigger();
        TestAction action = new TestAction();

        Rule rule = new Rule("SleepRule", trigger, action);
        rule.setRepeatable(true);
        rule.setSleepDuration(500); // 500 ms sleep time

        // First trigger → executes and enters sleep state
        trigger.fire();
        rule.check();

        assertEquals(1, action.getExecutions());

        // During sleep the rule must NOT execute again
        trigger.fire();
        rule.check();
        rule.check();

        assertEquals(
                1,
                action.getExecutions(),
                "Rule should NOT execute while sleeping"
        );
    }

    /**
     * Verifies that a rule executes again after the sleep period ends.
     */
    @Test
    void rule_executesAgain_afterSleepEnds() throws InterruptedException {
        TestTrigger trigger = new TestTrigger();
        TestAction action = new TestAction();

        Rule rule = new Rule("WakeUpRule", trigger, action);
        rule.setRepeatable(true);
        rule.setSleepDuration(300);

        // First execution
        trigger.fire();
        rule.check();
        assertEquals(1, action.getExecutions());

        // Wait for sleep period to end
        Thread.sleep(350);

        // Reset and fire trigger again
        trigger.reset();
        trigger.fire();

        rule.check();

        assertEquals(
                2,
                action.getExecutions(),
                "Rule should execute again after waking up"
        );
    }

    /**
     * Verifies that a non-repeatable rule becomes inactive
     * after its first execution.
     */
    @Test
    void nonRepeatableRule_becomesInactive_afterExecution() {
        TestTrigger trigger = new TestTrigger();
        TestAction action = new TestAction();

        Rule rule = new Rule("OneShotRule", trigger, action);
        rule.setRepeatable(false);

        trigger.fire();
        rule.check();

        assertEquals(1, action.getExecutions());
        assertFalse(
                rule.isActive(),
                "Non-repeatable rule should become inactive after execution"
        );
    }
}
