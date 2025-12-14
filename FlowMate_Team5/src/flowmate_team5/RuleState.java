package flowmate_team5;

import java.io.Serializable;

public interface RuleState extends Serializable {
    void check(Rule context);
    boolean isActive();
}