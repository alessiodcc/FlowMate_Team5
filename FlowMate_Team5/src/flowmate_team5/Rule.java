package flowmate_team5;

import java.io.Serializable;

public class Rule implements Serializable {

    private String name;
    private Trigger trigger;
    private Action action;
    private boolean active;
    private long sleepDurationMillis = 0;
    private long canBeFiredAfter = 0;

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
            // [C.1] GUARDIA DI COOLDOWN
            if (System.currentTimeMillis() < canBeFiredAfter) {
                // AGGIUNTO LOG DI DEBUG PER VERIFICARE IL BLOCCO
                System.out.println(
                        "[Rule Blocked]: " + this.name +
                                " is still in cooldown until: " +
                                new java.util.Date(canBeFiredAfter)
                );
                return;
            }

            // [C.2] CONTROLLO TRIGGER
            if (this.trigger != null && this.trigger.isTriggered()) {

                // --- INIZIO CORREZIONE: Imposta il cooldown PRIMA di eseguire l'azione ---

                // Imposta il Cooldown / One-Shot (PRIORITÀ)
                if (this.sleepDurationMillis > 0) {
                    this.canBeFiredAfter = System.currentTimeMillis() + this.sleepDurationMillis;
                    System.out.println("[Rule Cooldown]: " + this.name + " in sleep until: " + new java.util.Date(canBeFiredAfter));
                } else {
                    // Se sleepDurationMillis è 0, la regola è in modalità one-shot
                    this.active = false;
                    System.out.println("[Rule One-Shot]: " + this.name + " disattivata dopo l'esecuzione.");
                }

                // Esegui l'Azione
                if (this.action != null) {
                    System.out.println("[Rule Fired]: " + this.name);
                    this.action.execute(); // L'azione può bloccare, ma il cooldown è già impostato!
                }
            }
        }
    }

    /**
     * Sets the rule's cooldown period.
     * @param durationMillis The length of the sleep period in milliseconds.
     * Set to 0 for one-shot mode (disabling the rule after execution).
     */
    public void setSleepDuration(long durationMillis) {
        if (durationMillis < 0) {
            this.sleepDurationMillis = 0;
        } else {
            this.sleepDurationMillis = durationMillis;
        }

        if (durationMillis > 0 && !this.active) {
            this.active = true;
            this.canBeFiredAfter = 0;
        }

        System.out.println("[Rule Update]: " + this.name + " Sleep Duration impostata a " + this.sleepDurationMillis + "ms.");
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

    public long getSleepDurationMillis() {
        return sleepDurationMillis;
    }

    @Override
    public String toString() {
        return name;
    }
}