package flowmate_team5;

import flowmate_team5.core.Rule;
import flowmate_team5.models.Action;
import flowmate_team5.models.Trigger;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RuleRepetitionTest {

    /**
     * Fake Trigger implementation used for testing.
     * It simulates a trigger that fires only once per call to fire().
     */
    static class TestTrigger implements Trigger {
        private boolean triggered = false;

        // Simulates an external event that activates the trigger
        void fire() {
            triggered = true;
        }

        @Override
        public boolean isTriggered() {
            if (triggered) {
                triggered = false; // one-shot behaviour per fire()
                return true;
            }
            return false;
        }
    }

    /**
     * Fake Action implementation used for testing.
     * It simply counts how many times execute() is called.
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
     * Verifies that a non-repeatable rule executes its action only once,
     * even if the trigger fires multiple times.
     */
    @Test
    void nonRepeatableRule_executesOnlyOnce() {

        TestTrigger trigger = new TestTrigger();
        TestAction action = new TestAction();

        Rule rule = new Rule("OneShotRule", trigger, action);
        rule.setRepeatable(false);

        // First trigger → action should execute
        trigger.fire();
        rule.check();
        assertEquals(1, action.getExecutions());

        // Second trigger → action should NOT execute again
        trigger.fire();
        rule.check();
        assertEquals(1, action.getExecutions());
    }

    /**
     * Verifies that a repeatable rule executes its action
     * multiple times if the trigger fires again.
     */
    @Test
    void repeatableRule_executesMultipleTimes_ifTriggeredAgain() {

        TestTrigger trigger = new TestTrigger();
        TestAction action = new TestAction();

        Rule rule = new Rule("RepeatableRule", trigger, action);
        rule.setRepeatable(true);

        // First trigger
        trigger.fire();
        rule.check();
        assertEquals(1, action.getExecutions());

        // Second trigger
        trigger.fire();
        rule.check();
        assertEquals(2, action.getExecutions());
    }

    /**
     * Verifies that a repeatable rule with a cooldown period
     * executes again only after the cooldown has elapsed.
     */
    @Test
    void repeatableRule_withCooldown_executesOnlyAfterCooldown() throws InterruptedException {

        TestTrigger trigger = new TestTrigger();
        TestAction action = new TestAction();

        Rule rule = new Rule("RepeatableRule", trigger, action);
        rule.setRepeatable(true);
        rule.setSleepDuration(200); // cooldown in milliseconds

        // First execution
        trigger.fire();
        rule.check();
        assertEquals(1, action.getExecutions());

        // Trigger fired during cooldown → should NOT execute
        Thread.sleep(100);
        trigger.fire();
        rule.check();
        assertEquals(1, action.getExecutions());

        // Cooldown finished → action can execute again
        Thread.sleep(150);
        trigger.fire();
        rule.check();
        assertEquals(2, action.getExecutions());
    }

    /**
     * Verifies that a repeatable rule does not execute again
     * if the trigger is not fired.
     */
    @Test
    void repeatableRule_withoutTrigger_doesNotExecuteAgain() {

        TestTrigger trigger = new TestTrigger();
        TestAction action = new TestAction();

        Rule rule = new Rule("RepeatableRule", trigger, action);
        rule.setRepeatable(true);

        // First execution
        trigger.fire();
        rule.check();
        assertEquals(1, action.getExecutions());

        // No trigger fired → action should not execute
        rule.check();
        assertEquals(1, action.getExecutions());
    }
}
