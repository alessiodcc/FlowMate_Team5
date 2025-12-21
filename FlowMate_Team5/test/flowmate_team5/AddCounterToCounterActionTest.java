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
        // Initialize logic via Factory
        AddCounterToCounterActionCreator creator = new AddCounterToCounterActionCreator();
        action = (AddCounterToCounterAction) creator.createAction();

        // Initialize dummy counters using real Counter class
        source = new Counter("Source", 10);
        target = new Counter("Target", 50);

        // 3. Configure action
        action.setSourceCounter(source);
        action.setTargetCounter(target);
    }

    @Test
    void testExecuteAddsValueCorrectly() {
        // Arrange: Target is 50, Source is 10
        // Act: Execute action
        action.execute();

        // Assert: Target should be 60 (50 + 10)
        assertEquals(60, target.getValue(), "Target value should increase by source value");

        // Source
        assertEquals(10, source.getValue(), "Source value should not change");
    }

    @Test
    void testExecuteWithZeroValue() {
        // If source is 0, target should not change
        source.setValue(0);
        action.execute();
        assertEquals(50, target.getValue(), "Adding 0 should change nothing");
    }

    @Test
    void testConfigurationStoresCountersCorrectly() {
        assertEquals(source, action.getSourceCounter(), "Source counter should be retrieved correctly");
        assertEquals(target, action.getTargetCounter(), "Target counter should be retrieved correctly");
    }
}