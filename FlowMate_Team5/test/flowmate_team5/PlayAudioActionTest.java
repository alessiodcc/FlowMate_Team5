package flowmate_team5; // Test package

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.io.File;

public class PlayAudioActionTest {

    @Test
    void testConstructor_InitializationSuccessful() {
        // Arrange: Define a valid file path for initialization
        String validPath = "test.wav";

        // Act: Instantiate the PlayAudioAction class
        PlayAudioAction action = new PlayAudioAction(validPath);

        // Assert: Verify the object was created successfully
        assertNotNull(action, "The PlayAudioAction object must not be null.");
    }

    @Test
    void testExecute_HandlesFileNotFoundGracefully() {
        // Arrange: Define a path that should cause an error (Task 4.3 Error Handling)
        String nonExistingPath = "/non/esiste/affatto/fictional_file.mp3";
        PlayAudioAction action = new PlayAudioAction(nonExistingPath);

        // Act: Execute the action. This should trigger the internal error handler.
        action.execute();

        // Assert: Verify that the system did not crash (graceful failure).
        // This test passes if execution reaches this point without throwing an unhandled exception.
        assertTrue(true, "Execution must gracefully handle the file not found error.");
    }
}