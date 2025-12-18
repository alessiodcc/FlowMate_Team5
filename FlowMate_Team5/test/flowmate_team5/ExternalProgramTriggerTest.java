package flowmate_team5;

import flowmate_team5.models.triggers.ExternalProgramTrigger;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for ExternalProgramTrigger.
 * Checks interaction with OS processes.
 */
class ExternalProgramTriggerTest {

    @Test
    void testTriggerSuccess() {
        // Detect OS to run a command that is guaranteed to exit with 0
        String os = System.getProperty("os.name").toLowerCase();
        // 'cmd.exe /c exit 0' returns 0 on Windows, 'true' returns 0 on Unix/Mac
        String command = os.contains("win") ? "cmd.exe /c exit 0" : "true";

        ExternalProgramTrigger trigger = new ExternalProgramTrigger(command);
        assertTrue(trigger.isTriggered(), "Should return true for exit code 0");
    }

    @Test
    void testTriggerFailure() {
        // Run a command that doesn't exist to force failure
        ExternalProgramTrigger trigger = new ExternalProgramTrigger("invalid_command_xyz");
        assertFalse(trigger.isTriggered(), "Should return false if command fails or does not exist");
    }
}