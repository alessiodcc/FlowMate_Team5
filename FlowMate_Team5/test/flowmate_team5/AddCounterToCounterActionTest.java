package flowmate_team5;

import flowmate_team5.models.Counter;
import flowmate_team5.models.actions.AddCounterToCounterAction;
import flowmate_team5.factory.creators.AddCounterToCounterActionCreator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class AddCounterToCounterActionTest {

    private AddCounterToCounterAction action;
    private Counter source;
    private Counter target;

    @BeforeEach
    void setUp() {
        // 1. Initialize logic via Factory
        AddCounterToCounterActionCreator creator = new AddCounterToCounterActionCreator();
        action = (AddCounterToCounterAction) creator.createAction();

        // 2. Initialize dummy counters using Sara's real Counter class
        source = new Counter("Source", 10.0);
        target = new Counter("Target", 50.0);

        // 3. Configure action
        action.setSourceCounter(source);
        action.setTargetCounter(target);
    }

    @Test
    void testExecuteAddsValueCorrectly() {
        // Arrange: Target is 50.0, Source is 10.0
        // Act: Execute action
        action.execute();

        // Assert: Target should be 60.0 (50 + 10)
        assertEquals(60.0, target.getValue(), 0.001, "Target value should increase by source value");

        // Source should remain unchanged
        assertEquals(10.0, source.getValue(), 0.001, "Source value should not change");
    }

    @Test
    void testExecuteWithZeroValue() {
        // If source is 0, target should not change
        source.setValue(0.0);
        action.execute();
        assertEquals(50.0, target.getValue(), 0.001, "Adding 0 should change nothing");
    }
    @Test
    void testConfigurationStoresCountersCorrectly() {
        assertEquals(source, action.getSourceCounter(), "Source counter should be retrieved correctly");
        assertEquals(target, action.getTargetCounter(), "Target counter should be retrieved correctly");
    }
}