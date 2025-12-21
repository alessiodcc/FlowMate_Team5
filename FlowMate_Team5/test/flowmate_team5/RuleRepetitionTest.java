package flowmate_team5;

import flowmate_team5.core.Rule;
import flowmate_team5.models.Action;
import flowmate_team5.models.Trigger;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RuleRepetitionTest {

    // ---------- TEST DOUBLES ----------

    static class AlwaysTrueTrigger implements Trigger {
        @Override
        public boolean isTriggered() {
            return true;
        }
    }

    static class CountingAction implements Action {
        private int executions = 0;

        @Override
        public void execute() {
            executions++;
        }

        int getExecutions() {
            return executions;
        }
    }

    // ---------- TESTS ----------

    @Test
    void nonRepeatableRule_executesOnlyOnce() {
        CountingAction action = new CountingAction();
        Rule rule = new Rule(
                "OneShotRule",
                new AlwaysTrueTrigger(),
                action
        );

        rule.setRepeatable(false);

        rule.check(); // first execution
        rule.check(); // should NOT execute again

        assertEquals(
                1,
                action.getExecutions(),
                "Non-repeatable rule must execute only once"
        );

        assertFalse(rule.isActive(), "Rule should be inactive after one execution");
    }

    @Test
    void repeatableRule_withCooldown_executesAgainAfterCooldown() throws InterruptedException {
        CountingAction action = new CountingAction();
        Rule rule = new Rule(
                "RepeatableRule",
                new AlwaysTrueTrigger(),
                action
        );

        rule.setRepeatable(true);
        rule.setSleepDuration(100); // 100 ms cooldown

        // First trigger
        rule.check();
        assertEquals(1, action.getExecutions());

        // During cooldown → should NOT execute
        rule.check();
        assertEquals(
                1,
                action.getExecutions(),
                "Rule must not execute during cooldown"
        );

        // Wait for cooldown to expire
        Thread.sleep(150);

        // Trigger again
        rule.check();
        assertEquals(
                2,
                action.getExecutions(),
                "Repeatable rule should execute again after cooldown"
        );
    }

    @Test
    void repeatableRule_doesNotExecuteWhileInactive() {
        CountingAction action = new CountingAction();
        Rule rule = new Rule(
                "RepeatableButInactive",
                new AlwaysTrueTrigger(),
                action
        );

        rule.setRepeatable(true);
        rule.setActive(false); // force inactive

        rule.check();
        rule.check();

        assertEquals(
                0,
                action.getExecutions(),
                "Inactive rule must never execute"
        );
    }

    @Test
    void repeatableRule_executesOnlyAfterCooldown() throws Exception {

        TestAction action = new TestAction();
        AlwaysTrueTrigger trigger = new AlwaysTrueTrigger();

        Rule rule = new Rule("Repeatable", trigger, action);
        rule.setSleepDuration(100);

        rule.check(); // fires
        assertEquals(1, action.getExecutionCount()
        );

        rule.check(); // still sleeping
        assertEquals(1, action.getExecutionCount()
        );

        Thread.sleep(150);
        rule.check(); // wakes up
        rule.check(); // fires again

        assertEquals(2, action.getExecutionCount());
    }

}
