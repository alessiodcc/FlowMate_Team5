package flowmate_team5;

import flowmate_team5.factory.creators.PlayAudioActionCreator;
import flowmate_team5.models.actions.PlayAudioAction;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PlayAudioActionTest {

    @Test
    void testExecute_WithMissingFile_ShouldNotCrash() {
        String invalidPath = "src/flowmate_team5/resources/missing_file.wav";

        // Use Creator to instantiate
        PlayAudioActionCreator creator = new PlayAudioActionCreator();
        PlayAudioAction action = (PlayAudioAction) creator.createAction();

        // Configure path
        action.setFilePath(invalidPath);

        // Verify execute handles errors gracefully
        assertDoesNotThrow(() -> action.execute(), "Action must handle IOExceptions gracefully without crashing.");
    }

    @Test
    void testFactory_Initialization() {
        String path = "test_audio.wav";

        // Use Creator to instantiate
        PlayAudioActionCreator creator = new PlayAudioActionCreator();
        PlayAudioAction action = (PlayAudioAction) creator.createAction();

        // Configure path
        action.setFilePath(path);

        assertNotNull(action, "PlayAudioAction object should be successfully initialized.");
    }
}