package flowmate_team5;

import flowmate_team5.factory.creators.DeleteFileActionCreator;
import flowmate_team5.models.actions.DeleteFileAction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertFalse;

/**
 *
 * @author Alessio
 */
public class DeleteFileActionTest {
    private Path tempDir;
    private Path tempFile;
    private final String TEST_FILE_NAME = "temp_test_file.txt";

    private DeleteFileActionCreator creator;
    private DeleteFileAction dfa;

    /**
     Executed before every test, this method creates a temporary directory and file
     in order to allow testing from every machine.
     It also initializes a DeleteFileAction object that will be used in the test methods.
     */
    @BeforeEach
    void InitialSetUp() throws IOException {
        tempDir = Files.createTempDirectory("flowmate_test");
        tempFile = tempDir.resolve(TEST_FILE_NAME);
        Files.createFile(tempFile);

        this.creator = new DeleteFileActionCreator();
        dfa = (DeleteFileAction) creator.createAction();
    }

    /**
     Executed after every test, this method deletes the temporary directory and file
     created in the previous method.
     */
    @AfterEach
    void directoryAndFileTearDown() throws IOException {
        Files.deleteIfExists(tempFile);
        Files.deleteIfExists(tempDir);
    }

    /**
     This test verifies if the execute method can correctly identify
     when a file doesn't exist in a specific directory.
     */
    @Test
    void fileStillExistsShouldBeFalse() {
        dfa.setFilePath(tempFile);
        dfa.execute();
        assertFalse(Files.exists(tempFile));
    }

    /**
     * This test verifies if the execute method handles the scenario where
     * the target file does not exist, confirming that it executes gracefully
     * without throwing any exceptions.
     */
    @Test
    void execute_shouldHandleNonExistingFileGracefully() {
        // Defines a path for a non-existent file.
        Path nonExistentPath = tempDir.resolve("non_existent_file.tmp");
        // to be sure that the file created doesn't exist.
        assertFalse(Files.exists(nonExistentPath));
        dfa.setFilePath(tempFile);
        // Verifies that the method doesn't throw any exception blocking the execution flow.
        assertDoesNotThrow(dfa::execute, "Executing on non-existent file should not throw an exception.");
    }
}
