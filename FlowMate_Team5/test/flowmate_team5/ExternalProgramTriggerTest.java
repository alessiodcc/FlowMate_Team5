package flowmate_team5;

import flowmate_team5.models.triggers.ExternalProgramTrigger;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/* Unit tests verifying the ExternalProgramTrigger functionality and its interaction with OS processes. */
class ExternalProgramTriggerTest {

    /* Verifies that the trigger reports success when a valid command or process is detected. */
    @Test
    void testTriggerSuccess() {
        // Determine the operating system to select a command guaranteed to run successfully
        String os = System.getProperty("os.name").toLowerCase();
        // Select the appropriate command for Windows or Unix-based systems
        String command = os.contains("win") ? "cmd.exe /c exit 0" : "true";

        ExternalProgramTrigger trigger = new ExternalProgramTrigger(command);
        assertTrue(trigger.isTriggered(), "Should return true for exit code 0");
    }

    /* Verifies that the trigger reports failure when an invalid command is provided. */
    @Test
    void testTriggerFailure() {
        // Initialize the trigger with a non-existent command string
        ExternalProgramTrigger trigger = new ExternalProgramTrigger("invalid_command_xyz");
        assertFalse(trigger.isTriggered(), "Should return false if command fails or does not exist");
    }
}