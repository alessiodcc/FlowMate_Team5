/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package flowmate_team5;

/**
 *
 * @author husse
 */
/**
 * Task 1.3: Create a Rule Class (Structure Only).
 * Defines the data: Name, Trigger, Action, and Active State.
 */
public class Rule {
    
    private String name;
    private Trigger trigger;
    private Action action;
    private boolean active; // Task 8.1 support

    // Constructor
    public Rule(String name, Trigger trigger, Action action) {
        this.name = name;
        this.trigger = trigger;
        this.action = action;
        this.active = true; // Rules are active by default
    }

    // --- Getters and Setters (Data Access) ---
    /**
     * Task 1.4: Implement the logic that executes the action.
     * @return 
    */
    
    public String getName() {
        return name;
    }

    public Trigger getTrigger() {
        return trigger;
    }

    public Action getAction() {
        return action;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
    
    @Override
    public String toString() {
        return name;
    }
}
