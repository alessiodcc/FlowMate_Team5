package flowmate_team5;

import flowmate_team5.factory.creators.CounterIntegerComparisonTriggerCreator;
import flowmate_team5.models.triggers.CounterIntegerComparisonTrigger;
import org.junit.jupiter.api.BeforeEach;
import flowmate_team5.models.Counter;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class CounterIntegerComparisonTriggerTest {

    private Counter counter;
    private CounterIntegerComparisonTrigger trigger;
    private CounterIntegerComparisonTriggerCreator creator;

    /**
     Executed before every test, this method initializes a new counter
     with a specific starting value (10) and from it initializes
     also a new CounterIntegerComparisonTrigger, that will be used in the test methods.
     */
    @BeforeEach
    void setUp() {
        this.creator = new CounterIntegerComparisonTriggerCreator();
        this.trigger = (CounterIntegerComparisonTrigger) creator.createTrigger();
        this.counter = new Counter("TestCounter", 10);
        trigger.setCounter(counter);
    }

    /**
     * Verifies that the trigger returns true when the counter value is strictly less than the threshold.
     */
    @Test
    void isTriggeredShouldBeTrueWhenValueIsLess() {
        trigger.setComparator("<");
        trigger.setIntValue(20);
        assertTrue(trigger.isTriggered());
    }

    /**
     * Verifies that the trigger returns false when the comparison (greater than) is not satisfied.
     */
    @Test
    void isTriggeredShouldBeFalseWhenValueIsLess() {
        trigger.setComparator(">");
        trigger.setIntValue(20);
        assertFalse(trigger.isTriggered());
    }

    /**
     * Verifies that the trigger returns true when the counter value is exactly equal to the threshold.
     */
    @Test
    void isTriggeredShouldBeTrueWhenValueIsEqual() {
        trigger.setComparator("=");
        trigger.setIntValue(10);
        assertTrue(trigger.isTriggered());
    }

    /**
     * Verifies that the trigger returns false when values are equal but the operator is strictly 'greater than'.
     */
    @Test
    void isTriggeredShouldBeFalseWhenValuesAreExactlyEqualButOperatorIsGreater() {
        trigger.setComparator(">");
        trigger.setIntValue(10);
        assertFalse(trigger.isTriggered(), "10 is not greater than 10");
    }

    /**
     * Verifies that the trigger returns false when values are equal but the operator is strictly 'less than'.
     */
    @Test
    void isTriggeredShouldBeFalseWhenValuesAreExactlyEqualButOperatorIsLess() {
        trigger.setComparator("<");
        trigger.setIntValue(10);
        assertFalse(trigger.isTriggered(), "10 is not less than 10");
    }

}