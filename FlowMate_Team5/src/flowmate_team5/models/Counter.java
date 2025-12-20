package flowmate_team5.models;

import java.io.Serializable;

/**
 * Represents a counter entity with a name and an integer value.
 * Updated to use int as per requirements.
 */
public class Counter implements Serializable {

    private static final long serialVersionUID = 1L;
    private String name;
    private int value;

    public Counter(String name, int initialValue) {
        this.name = name;
        this.value = initialValue;
    }

    public Counter(String name) {
        this(name, 0);
    }

    public void increment() {
        this.value++;
    }

    public void decrement() {
        this.value--;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name + " (" + value + ")";
    }
}