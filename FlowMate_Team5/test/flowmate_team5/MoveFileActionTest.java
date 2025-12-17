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
 * Unit Tests for MoveFileAction.
 */
class MoveFileActionTest {

    private Path tempSourceFile;
    private Path tempDestDir;

    // Define the Creator and the Concrete Product
    private MoveFileActionCreator creator;
    private MoveFileAction action;

    @BeforeEach
    void setUp() throws IOException {
        // Create temporary file and directory for safe testing
        tempSourceFile = Files.createTempFile("test_move_source", ".txt");
        tempDestDir = Files.createTempDirectory("test_move_dest");

        // Write dummy content
        Files.writeString(tempSourceFile, "Data to move");

        // FACTORY INITIALIZATION
        // Instead of "new MoveFileAction(...)", we use the creator
        this.creator = new MoveFileActionCreator();

        // Create the action and Cast it to the concrete class
        this.action = (MoveFileAction) creator.createAction();
    }

    @AfterEach
    void tearDown() throws IOException {
        // Delete temporary files after test
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
        // Arrange (Configure the action using Setters)
        action.setSourcePathString(tempSourceFile.toString());
        action.setDestinationDirectoryString(tempDestDir.toString());

        // Act
        action.execute();

        // Verify
        Path expectedFile = tempDestDir.resolve(tempSourceFile.getFileName());

        assertFalse(Files.exists(tempSourceFile), "Original file should no longer exist.");
        assertTrue(Files.exists(expectedFile), "File should exist in the new folder.");
    }
}