package flowmate_team5;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PlayAudioActionTest {

    @Test
    void testExecute_WithMissingFile_ShouldNotCrash() {
        // Arrange: Define an invalid path to simulate a missing file scenario
        String invalidPath = "src/flowmate_team5/resources/missing_file.wav";
        PlayAudioAction action = new PlayAudioAction(invalidPath);

        // Act & Assert: Verify that execute() handles the error internally without crashing
        assertDoesNotThrow(() -> action.execute(), "Action must handle IOExceptions gracefully without crashing.");
    }

    @Test
    void testConstructor_Initialization() {
        // Arrange: Define a dummy path
        String path = "test_audio.wav";

        // Act: Create the object
        PlayAudioAction action = new PlayAudioAction(path);

        // Assert: Verify object creation
        assertNotNull(action, "PlayAudioAction object should be successfully initialized.");
    }
}