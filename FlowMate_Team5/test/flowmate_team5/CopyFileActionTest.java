package flowmate_team5;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import flowmate_team5.factory.creators.CopyFileActionCreator;
import flowmate_team5.models.actions.CopyFileAction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CopyFileActionTest {

    private Path tempSource;
    private Path tempDestDir;

    @BeforeEach
    void setUp() throws IOException {
        // Create temp file and dir for testing
        tempSource = Files.createTempFile("test_src_sara", ".txt");
        tempDestDir = Files.createTempDirectory("test_dest_dir_sara");
        Files.write(tempSource, "Content for Unit Test 13.4".getBytes());
    }

    @AfterEach
    void tearDown() throws IOException {
        // Cleanup resources
        Files.deleteIfExists(tempSource);
        File destFile = new File(tempDestDir.toFile(), tempSource.toFile().getName());
        if(destFile.exists()) destFile.delete();
        Files.deleteIfExists(tempDestDir);
    }

    @Test
    void testExecute_CopySuccess() {
        // Use Creator to instantiate the action
        CopyFileActionCreator creator = new CopyFileActionCreator();
        CopyFileAction action = (CopyFileAction) creator.createAction();

        // Configure action via setters
        action.setSourcePath(tempSource.toString());
        action.setDestinationDir(tempDestDir.toString());

        assertDoesNotThrow(action::execute);

        File expectedDestFile = new File(tempDestDir.toFile(), tempSource.toFile().getName());
        assertTrue(expectedDestFile.exists(), "File should be copied.");
        assertEquals(tempSource.toFile().length(), expectedDestFile.length());
    }

    @Test
    void testExecute_SourceMissing() {
        // Use Creator to instantiate the action
        CopyFileActionCreator creator = new CopyFileActionCreator();
        CopyFileAction action = (CopyFileAction) creator.createAction();

        // Configure with invalid source
        action.setSourcePath("non_existent_file.txt");
        action.setDestinationDir(tempDestDir.toString());

        assertDoesNotThrow(action::execute, "Should handle missing source gracefully.");
    }
}