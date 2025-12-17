package flowmate_team5.state;

import flowmate_team5.core.Rule;

import java.io.Serializable;

public interface RuleState extends Serializable {
    void check(Rule context);
    boolean isActive();
}