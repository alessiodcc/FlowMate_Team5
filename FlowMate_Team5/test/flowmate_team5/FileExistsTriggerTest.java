package flowmate_team5;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class FileExistsTriggerTest {
    private Path tempDir;
    private Path tempFile;
    private final String TEST_FILE_NAME = "temp_test_file.txt";

    private FileExistsTriggerCreator creator;
    private FileExistsTrigger fett;

    /**
     Executed before every test, this method creates a temporary directory and file
     in order to allow testing from every machine.
     It also initializes a FileExistsTrigger object that will be used in the test methods.
     */
    @BeforeEach
    void InitialSetUp() throws IOException {
        tempDir = Files.createTempDirectory("flowmate_test");
        tempFile = tempDir.resolve(TEST_FILE_NAME);
        Files.createFile(tempFile);

        this.creator = new FileExistsTriggerCreator();
        fett = (FileExistsTrigger) creator.createTrigger();

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
     This method tests if the method isTriggered
     can correctly identify a file within a specific directory,
     returning true when it exists.
     */
    @Test
    void isTriggeredTestShouldBeTrue(){
        fett.setFileName(TEST_FILE_NAME);
        fett.setFolderPath(tempDir);
        assertTrue(fett.isTriggered());
    }

    /**
     This method tests if the method isTriggered
     can correctly identify a file within a specific directory,
     returning false when it doesn't exist.
     */
    @Test
    void isTriggeredTestShouldBeFalse(){
        fett.setFileName("NON-EXISTENT-FILE-NAME");
        fett.setFolderPath(tempDir);
        assertFalse(fett.isTriggered());
    }
}
