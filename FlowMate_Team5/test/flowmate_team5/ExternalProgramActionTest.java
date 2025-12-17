package flowmate_team5;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit Tests for ExternalProgramAction.
 * This test uses the Creator, which resolves the "Class is never used" warning.
 */
class ExternalProgramActionTest {

    private ExternalProgramActionCreator creator;
    private ExternalProgramAction action;

    @BeforeEach
    void setUp() {
        creator = new ExternalProgramActionCreator();

        // Create the action using the factory pattern
        action = (ExternalProgramAction) creator.createAction();
    }

    @Test
    void testFactoryCreation() {
        // Verify the factory gave us the right object
        assertNotNull(action, "Factory should return a valid object");
        assertTrue(action instanceof ExternalProgramAction,
                "Object should be an instance of ExternalProgramAction");
    }

    @Test
    void testConfiguration() {
        // Configure the action with dummy data
        String expectedCommand = "notepad.exe";
        String expectedDir = "C:/Temp";

        action.setCommandLine(expectedCommand);
        action.setWorkingDirectory(expectedDir);

        // Verify values are stored correctly using getters
        assertEquals(expectedCommand, action.getCommandLine());
        assertEquals(expectedDir, action.getWorkingDirectory());
    }

    @Test
    void testExecutionIdeallyDoesNotCrash() {
        // A "Smoke Test" to ensure execute() handles basic commands safely.
        action.setCommandLine("echo hello");

        // This ensures the code doesn't crash the app if triggered
        assertDoesNotThrow(() -> action.execute());
    }
}