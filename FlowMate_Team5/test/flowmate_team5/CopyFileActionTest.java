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

/* Unit tests verifying the functionality of the CopyFileAction class. */
class CopyFileActionTest {

    private Path tempSource;
    private Path tempDestDir;

    /* Prepares the test environment by creating temporary files and directories. */
    @BeforeEach
    void setUp() throws IOException {
        // Create temp file and dir for testing to ensure isolation
        tempSource = Files.createTempFile("test_src_sara", ".txt");
        tempDestDir = Files.createTempDirectory("test_dest_dir_sara");
        Files.write(tempSource, "Content for Unit Test 13.4".getBytes());
    }

    /* Cleans up temporary resources after each test execution to prevent clutter. */
    @AfterEach
    void tearDown() throws IOException {
        // Delete the source file and the copied destination file if it exists
        Files.deleteIfExists(tempSource);
        File destFile = new File(tempDestDir.toFile(), tempSource.toFile().getName());
        if(destFile.exists()) destFile.delete();
        Files.deleteIfExists(tempDestDir);
    }

    /* Verifies that a valid file is correctly copied to the destination directory. */
    @Test
    void testExecute_CopySuccess() {
        // Use the specific Factory Creator to instantiate the action
        CopyFileActionCreator creator = new CopyFileActionCreator();
        CopyFileAction action = (CopyFileAction) creator.createAction();

        // Configure the action properties with the temporary paths
        action.setSourcePath(tempSource.toString());
        action.setDestinationDir(tempDestDir.toString());

        // Execute the action and ensure no exceptions are thrown
        assertDoesNotThrow(action::execute);

        // Verify that the file now exists in the destination folder with correct content length
        File expectedDestFile = new File(tempDestDir.toFile(), tempSource.toFile().getName());
        assertTrue(expectedDestFile.exists(), "File should be copied.");
        assertEquals(tempSource.toFile().length(), expectedDestFile.length());
    }

    /* Ensures the action handles non-existent source files gracefully without crashing. */
    @Test
    void testExecute_SourceMissing() {
        // Instantiate the action via the factory
        CopyFileActionCreator creator = new CopyFileActionCreator();
        CopyFileAction action = (CopyFileAction) creator.createAction();

        // Configure the action with a path that does not exist
        action.setSourcePath("non_existent_file.txt");
        action.setDestinationDir(tempDestDir.toString());

        // The execution should log an error but not throw an exception
        assertDoesNotThrow(action::execute, "Should handle missing source gracefully.");
    }
}