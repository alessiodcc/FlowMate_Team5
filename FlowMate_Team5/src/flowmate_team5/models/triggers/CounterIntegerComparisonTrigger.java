package flowmate_team5.models.triggers;

import flowmate_team5.models.Counter;
import flowmate_team5.models.Trigger;

import java.io.Serializable;

public class CounterIntegerComparisonTrigger implements Trigger, Serializable {
    private Integer intValue;
    private Counter counter;
    private String comparator; // Can be <, > or =

    public CounterIntegerComparisonTrigger() {
        // Empty constructor for Factory
    }

    /**
     *
     * Getters and Setters
     */
    public Counter getCounter() {
        return counter;
    }

    public Integer getIntValue() {
        return intValue;
    }

    public String getComparator() {
        return comparator;
    }

    public void setComparator(String comparator) {
        this.comparator = comparator;
    }

    public void setCounter(Counter counter) {
        this.counter = counter;
    }

    public void setIntValue(Integer intValue) {
        this.intValue = intValue;
    }

    /**
     * Evaluates the comparison between the counter's current value and the reference integer.
     * @return true if the condition defined by the comparator is met, false otherwise.
     */
    @Override
    public boolean isTriggered() {
        switch (comparator) {
            case("<"):
                return (counter.getValue() < intValue);
            case(">"):
                return (counter.getValue() > intValue);
            case("="):
                return (counter.getValue() == intValue);
            default:
                return false;
        }
    }
}
