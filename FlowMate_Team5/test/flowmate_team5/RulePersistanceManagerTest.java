package flowmate_team5;

import org.junit.jupiter.api.*;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RulePersistenceManagerTest {

    private static final String FILE_NAME = "rules.dat";

    static class SerializableTrigger implements Trigger, Serializable {
        private final boolean triggered;

        SerializableTrigger(boolean triggered) {
            this.triggered = triggered;
        }

        @Override
        public boolean isTriggered() {
            return triggered;
        }
    }

    static class SerializableAction implements Action, Serializable {
        @Override
        public void execute() {
            // no operation
        }
    }

    @AfterEach
    void cleanUp() {
        File file = new File(FILE_NAME);
        if (file.exists()) {
            file.delete();
        }
    }

    @Test
    void saveAndLoadRules_shouldWorkCorrectly() {
        List<Rule> rules = new ArrayList<>();
        rules.add(new Rule(
                "Rule Test",
                new SerializableTrigger(true),
                new SerializableAction()
        ));

        RulePersistenceManager.saveRules(rules);

        List<Rule> loaded = RulePersistenceManager.loadRules();

        assertEquals(1, loaded.size());
        assertEquals("Rule Test", loaded.get(0).getName());
    }
}
