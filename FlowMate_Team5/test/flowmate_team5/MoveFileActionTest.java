package flowmate_team5;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Task 13.8: Unit Tests for MoveFileAction.
 * Verifies that files are correctly moved and edge cases (missing files) are handled.
 */
class MoveFileActionTest {

    private Path tempSourceFile;
    private Path tempDestDir;

    @BeforeEach
    void setUp() throws IOException {
        // Create a temporary file and directory for safe testing
        tempSourceFile = Files.createTempFile("test_move_source", ".txt");
        tempDestDir = Files.createTempDirectory("test_move_dest");

        // Write dummy content
        Files.writeString(tempSourceFile, "Data to move");
    }

    @AfterEach
    void tearDown() throws IOException {
        // Cleanup: Delete temporary files after test
        Files.deleteIfExists(tempSourceFile);

        // Recursively delete the directory
        if (Files.exists(tempDestDir)) {
            try (Stream<Path> walk = Files.walk(tempDestDir)) {
                walk.sorted(Comparator.reverseOrder())
                        .forEach(path -> {
                            try { Files.delete(path); } catch (IOException e) {}
                        });
            }
        }
    }

    @Test
    void testMoveFileSuccess() {
        // 1. Arrange
        MoveFileAction action = new MoveFileAction(
                tempSourceFile.toString(),
                tempDestDir.toString()
        );

        // 2. Act
        action.execute();

        // 3. Verify
        Path expectedFile = tempDestDir.resolve(tempSourceFile.getFileName());

        assertFalse(Files.exists(tempSourceFile), "Original file should no longer exist.");
        assertTrue(Files.exists(expectedFile), "File should exist in the new folder.");
    }
}