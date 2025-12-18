package flowmate_team5.models;

import java.io.Serializable;

/**
 * Represents a counter entity with a name and a value.
 * Implements Serializable to allow saving/loading state if necessary.
 */
public class Counter implements Serializable {

    private static final long serialVersionUID = 1L;
    private String name;
    private double value;

    public Counter(String name, double initialValue) {
        this.name = name;
        this.value = initialValue;
    }

    public Counter(String name) {
        this(name, 0.0);
    }

    public void increment() {
        this.value++;
    }

    public void decrement() {
        this.value--;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
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