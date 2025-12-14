package flowmate_team5;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

/**
 *  Unit Tests for Saving and Loading rules.
 */
public class RulePersistenceManagerTest {

    private static final String FILE_NAME = "rules.dat";

    @BeforeEach
    @AfterEach
    public void cleanUp() {
        File file = new File(FILE_NAME);
        if (file.exists()) {
            file.delete();
        }
    }

    @Test
    public void testSaveAndLoad() {
        //  Create dummy rules
        Rule rule1 = new Rule("Rule A", null, null);
        Rule rule2 = new Rule("Rule B", null, null);

        List<Rule> originalList = new ArrayList<>();
        originalList.add(rule1);
        originalList.add(rule2);

        //  Save
        RulePersistenceManager.saveRules(originalList);

        //  Load
        List<Rule> loadedList = RulePersistenceManager.loadRules();

        //  Verify
        assertNotNull(loadedList);
        assertEquals(2, loadedList.size());
        assertEquals("Rule A", loadedList.get(0).getName());
    }
}