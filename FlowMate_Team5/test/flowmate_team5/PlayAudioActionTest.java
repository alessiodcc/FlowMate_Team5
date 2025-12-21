package flowmate_team5;

import flowmate_team5.factory.creators.PlayAudioActionCreator;
import flowmate_team5.models.actions.PlayAudioAction;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/* Unit tests responsible for verifying the stability and initialization of the PlayAudioAction class. */
public class PlayAudioActionTest {

    /* Verifies that the action handles execution failures gracefully when the audio file is missing. */
    @Test
    void testExecute_WithMissingFile_ShouldNotCrash() {
        String invalidPath = "src/flowmate_team5/resources/missing_file.wav";

        // Use the specific Factory Creator to instantiate the action
        PlayAudioActionCreator creator = new PlayAudioActionCreator();
        PlayAudioAction action = (PlayAudioAction) creator.createAction();

        // Configure the action with a path that does not exist
        action.setFilePath(invalidPath);

        // Ensure that the execution catches internal IOExceptions and does not crash the application
        assertDoesNotThrow(() -> action.execute(), "Action must handle IOExceptions gracefully without crashing.");
    }

    /* Tests the correct instantiation of the action object via the Factory pattern. */
    @Test
    void testFactory_Initialization() {
        String path = "test_audio.wav";

        // Instantiate the action using the creator
        PlayAudioActionCreator creator = new PlayAudioActionCreator();
        PlayAudioAction action = (PlayAudioAction) creator.createAction();

        // Set the file path property
        action.setFilePath(path);

        // Assert that the object is valid and not null
        assertNotNull(action, "PlayAudioAction object should be successfully initialized.");
    }
}