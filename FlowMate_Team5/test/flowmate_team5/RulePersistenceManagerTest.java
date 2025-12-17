package flowmate_team5;

import flowmate_team5.core.Rule;
import flowmate_team5.core.RulePersistenceManager;
import flowmate_team5.factory.CreatorAction;
import flowmate_team5.factory.CreatorTrigger;
import flowmate_team5.factory.creators.FileExistsTriggerCreator;
import flowmate_team5.factory.creators.MoveFileActionCreator;
import flowmate_team5.models.Action;
import flowmate_team5.models.Trigger;
import flowmate_team5.models.actions.MoveFileAction;
import flowmate_team5.models.triggers.FileExistsTrigger;
import org.junit.jupiter.api.*;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RulePersistenceManagerTest {

    private static final String FILE_NAME = "rules.dat";

    @AfterEach
    void cleanUp() {
        // Delete the test file so it doesn't mess up your real app data
        File file = new File(FILE_NAME);
        if (file.exists()) {
            file.delete();
        }
    }

    @Test
    void saveAndLoadRules_shouldWorkCorrectly() {

        // Create the Trigger via Factory
        CreatorTrigger triggerFactory = new FileExistsTriggerCreator();
        Trigger myTrigger = triggerFactory.createTrigger();

        // Configure it
        if (myTrigger instanceof FileExistsTrigger) {
            ((FileExistsTrigger) myTrigger).setFileName("test_save.txt");
            // Note: We use a string path that can be reconstructed
            ((FileExistsTrigger) myTrigger).setFolderPath(Path.of("C:/Temp"));
        }

        // Create the Action via Factory
        CreatorAction actionFactory = new MoveFileActionCreator();
        Action myAction = actionFactory.createAction();

        // Configure it
        if (myAction instanceof MoveFileAction) {
            ((MoveFileAction) myAction).setSourcePathString("C:/Temp/source.txt");
            ((MoveFileAction) myAction).setDestinationDirectoryString("C:/Temp/Dest");
        }

        // Create the Rule
        Rule originalRule = new Rule("Factory Test Rule", myTrigger, myAction);

        // Add to list
        List<Rule> rulesToSave = new ArrayList<>();
        rulesToSave.add(originalRule);

        // ACT: Save and Load

        RulePersistenceManager.saveRules(rulesToSave);

        // Load back
        List<Rule> loadedRules = RulePersistenceManager.loadRules();

        // ASSERT: Verify Data

        assertNotNull(loadedRules, "Loaded rules list should not be null");
        assertEquals(1, loadedRules.size(), "Should verify exactly 1 rule was loaded");

        Rule loadedRule = loadedRules.get(0);
        assertEquals("Factory Test Rule", loadedRule.getName());

        // Verify that the Trigger type was preserved
        assertTrue(loadedRule.getTrigger() instanceof FileExistsTrigger,
                "The loaded trigger should be a FileExistsTrigger");

        // Verify that the Action type was preserved
        assertTrue(loadedRule.getAction() instanceof MoveFileAction,
                "The loaded action should be a MoveFileAction");

        // Verify specific data inside was saved
        FileExistsTrigger loadedTrigger = (FileExistsTrigger) loadedRule.getTrigger();
        assertEquals("test_save.txt", loadedTrigger.getFileName(),
                "Trigger filename should be preserved after loading");
    }
}