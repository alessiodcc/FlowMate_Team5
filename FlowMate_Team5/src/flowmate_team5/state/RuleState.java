package flowmate_team5.state;

import flowmate_team5.core.Rule;

import java.io.Serializable;

/* Defines the contract for the various states a Rule can inhabit, supporting persistence. */
public interface RuleState extends Serializable {

    /* Executes the logic specific to the current state, using the provided Rule context. */
    void check(Rule context);

    /* Returns the operational status of the state (true for active, false otherwise). */
    boolean isActive();
}