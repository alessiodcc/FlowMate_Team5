package flowmate_team5.models;

import java.io.Serializable;

/* Represents a named counter entity maintaining an integer state. */
public class Counter implements Serializable {

    private static final long serialVersionUID = 1L;
    private String name;
    private int value;

    /* Initializes the counter with a specific name and starting value. */
    public Counter(String name, int initialValue) {
        this.name = name;
        this.value = initialValue;
    }

    /* Overloaded constructor that defaults the initial value to zero. */
    public Counter(String name) {
        this(name, 0);
    }

    /* Increases the counter value by one unit. */
    public void increment() {
        this.value++;
    }

    /* Decreases the counter value by one unit. */
    public void decrement() {
        this.value--;
    }

    /* Retrieves the current integer value of the counter. */
    public int getValue() {
        return value;
    }

    /* Updates the counter to a specific integer value. */
    public void setValue(int value) {
        this.value = value;
    }

    /* Retrieves the display name of the counter. */
    public String getName() {
        return name;
    }

    /* Updates the display name of the counter. */
    public void setName(String name) {
        this.name = name;
    }

    /* Returns a formatted string representation of the counter state. */
    @Override
    public String toString() {
        return name + " (" + value + ")";
    }
}