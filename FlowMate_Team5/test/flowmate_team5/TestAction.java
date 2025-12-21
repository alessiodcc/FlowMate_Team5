package flowmate_team5;

import flowmate_team5.models.Action;

public class TestAction implements Action {

    private int executionCount = 0;

    @Override
    public void execute() {
        executionCount++;
    }

    public int getExecutionCount() {
        return executionCount;
    }
}
