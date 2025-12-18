package flowmate_team5;

import flowmate_team5.factory.creators.FileExceedsTriggerCreator;
import flowmate_team5.models.triggers.FileExceedsTrigger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class FileExceedsTriggerTest {

    private Path tempDir;
    private Path tempFile;
    private FileExceedsTrigger trigger;

    @BeforeEach
    void setUp() throws IOException {
        // Create a temp directory and file
        tempDir = Files.createTempDirectory("test_size_trigger");
        tempFile = tempDir.resolve("log.txt");

        // Write exactly 5 bytes ("12345") into the file
        Files.writeString(tempFile, "12345");

        // Use Factory to create trigger
        FileExceedsTriggerCreator creator = new FileExceedsTriggerCreator();
        trigger = (FileExceedsTrigger) creator.createTrigger();

        // Configure trigger
        trigger.setFolderPath(tempDir);
        trigger.setFileName("log.txt");
    }

    @AfterEach
    void tearDown() throws IOException {
        // Clean up files after test
        Files.deleteIfExists(tempFile);
        Files.deleteIfExists(tempDir);
    }

    @Test
    void testTriggerFiresWhenFileIsTooBig() {
        // Logic: File is 5 bytes. Limit is 3 bytes. -> TRUE
        trigger.setMaxSizeInBytes(3);

        assertTrue(trigger.isTriggered(), "Should trigger because 5 > 3");
    }

    @Test
    void testTriggerDoesNotFireWhenFileIsSmall() {
        // Logic: File is 5 bytes. Limit is 10 bytes. -> FALSE
        trigger.setMaxSizeInBytes(10);

        assertFalse(trigger.isTriggered(), "Should NOT trigger because 5 < 10");
    }
}