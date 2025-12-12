package flowmate_team5;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
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
        // Cleanup
        Files.deleteIfExists(tempSource);
        File destFile = new File(tempDestDir.toFile(), tempSource.toFile().getName());
        if(destFile.exists()) destFile.delete();
        Files.deleteIfExists(tempDestDir);
    }

    @Test
    void testExecute_CopySuccess() {
        CopyFileAction action = new CopyFileAction(tempSource.toString(), tempDestDir.toString());

        assertDoesNotThrow(action::execute);

        File expectedDestFile = new File(tempDestDir.toFile(), tempSource.toFile().getName());
        assertTrue(expectedDestFile.exists(), "File should be copied.");
        assertEquals(tempSource.toFile().length(), expectedDestFile.length());
    }

    @Test
    void testExecute_SourceMissing() {
        CopyFileAction action = new CopyFileAction("non_existent_file.txt", tempDestDir.toString());

        assertDoesNotThrow(action::execute);

        File expectedDestFile = new File(tempDestDir.toFile(), "non_existent_file.txt");
        assertFalse(expectedDestFile.exists());
    }
}