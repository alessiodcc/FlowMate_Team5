package flowmate_team5;

public class Rule {

    private String name;
    private Trigger trigger;
    private Action action;
    private boolean active;

    // Constructor
    public Rule(String name, Trigger trigger, Action action) {
        this.name = name;
        this.trigger = trigger;
        this.action = action;
        this.active = true;
    }

    /**
     * Task 1.4: Implement the logic that executes the action.
     */
    public void check() {
        if (this.active) {
            if (this.trigger != null && this.trigger.isTriggered()) {
                if (this.action != null) {
                    this.action.execute();
                    System.out.println("[Rule Fired]: " + this.name);
                }
            }
        }
    }

    // --- Getters and Setters ---
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