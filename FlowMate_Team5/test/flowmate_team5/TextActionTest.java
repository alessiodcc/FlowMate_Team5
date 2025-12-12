package flowmate_team5;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TextActionTest {

    private Path tempFilePath;
    private String tempFilePathString;
    private final java.io.ByteArrayOutputStream errContent = new java.io.ByteArrayOutputStream();
    private final java.io.PrintStream originalErr = System.err;

    @BeforeEach
    public void setUp() throws IOException {
        // Creates a temporary file (createTempFile is available since Java 7)
        tempFilePath = Files.createTempFile("TextActionTest", ".txt");
        tempFilePathString = tempFilePath.toString();

        // Redirects System.err to capture I/O errors
        System.setErr(new java.io.PrintStream(errContent));
    }

    @AfterEach
    public void tearDown() throws IOException {
        // Restores System.err
        System.setErr(originalErr);

        // Deletes the temporary file
        Files.deleteIfExists(tempFilePath);
    }

    /**
     * Tests the basic execution: verifies content is written to the file.
     */
    @Test
    public void testExecute_SuccessfulWrite() throws IOException {
        String message = "Hello, world!";
        TextAction action = new TextAction(tempFilePathString, message);

        action.execute();
        // Reads the entire content of the file
        String fileContent = new String(Files.readAllBytes(tempFilePath));

        // Verifies that the file content is the expected message
        assertEquals(message, fileContent, "The file content should match the message.");

        // Verifies that there were no error messages
        assertTrue(errContent.toString().isEmpty(), "There should be no I/O errors in System.err.");
    }

    /**
     * Tests the append functionality of the TextAction.
     */
    @Test
    public void testExecute_AppendContent() throws IOException {
        String message1 = "First line.\n";
        String message2 = "Second line.\n";

        TextAction action1 = new TextAction(tempFilePathString, message1);
        action1.execute();

        TextAction action2 = new TextAction(tempFilePathString, message2);
        action2.execute();

        String fileContent = new String(Files.readAllBytes(tempFilePath)); // readAllBytes is compatible with Java 7/8
        String expectedContent = message1 + message2;
        // Verifies that the content is the concatenation of the two messages
        assertEquals(expectedContent, fileContent, "The file content should be the concatenation of the two messages.");
    }

    /**
     * Tests file creation if the file does not exist.
     */
    @Test
    public void testExecute_FileCreationIfNotExist() throws IOException {
        String tempDir = System.getProperty("java.io.tmpdir");

        Path nonExistentPath = Paths.get(tempDir, "nonexistent", "newly_created_file.tmp");

        Path parentDir = nonExistentPath.getParent();
        if (!Files.exists(parentDir)) {
            Files.createDirectories(parentDir); // Creates the parent directory (and its predecessors)
        }
        // Ensure the file does not exist before the test
        Files.deleteIfExists(nonExistentPath);

        String nonExistentPathString = nonExistentPath.toString();
        String message = "This file was created.";

        TextAction action = new TextAction(nonExistentPathString, message);

        action.execute();

        // Verifies that the file was actually created
        assertTrue(Files.exists(nonExistentPath), "The file should have been created.");

        // Verifies content
        String createdFileContent = new String(Files.readAllBytes(nonExistentPath));
        assertEquals(message, createdFileContent, "The content of the created file should be correct.");

        // Manual cleanup of the created file
        Files.deleteIfExists(nonExistentPath);

        // Cleanup of the fictitious parent directory, if it is empty
        File parentFile = parentDir.toFile();
        if (parentFile.exists() && parentFile.list() != null && parentFile.list().length == 0) {
            Files.deleteIfExists(parentDir);
        }
    }

    /**
     * Tests I/O error handling when the path is not writable (e.g., a directory).
     */
    @Test
    public void testExecute_IOErrorHandling() {
        // We use a directory as 'filePath' to force an IOException
        String directoryPath = System.getProperty("java.io.tmpdir");
        String message = "Writing attempt failed.";

        TextAction action = new TextAction(directoryPath, message);

        // Execution should catch the IOException and print it to System.err
        action.execute();

        // Verifies that System.err is not empty, indicating the error was printed
        String errOutput = errContent.toString();
        assertFalse(errOutput.isEmpty(), "System.err should not be empty in case of I/O error.");

        // Verifies that the custom error message is printed
        assertTrue(errOutput.contains("Errore I/O durante la TextAction sul file: " + directoryPath), // NOTE: This checks the original error message from the TextAction class, which is still in Italian.
                "The System.err output should contain the error message.");
    }

    /**
     * Tests the getter methods.
     */
    @Test
    public void testGetters() {
        String filePath = "/path/to/some/file.txt";
        String message = "Test message for getters.";

        TextAction action = new TextAction(filePath, message);

        assertEquals(filePath, action.getFilePath(), "getFilePath() should return the correct path.");
        assertEquals(message, action.getMessage(), "getMessage() should return the correct message.");
        assertEquals(message, action.getMessageToShow(), "getMessageToShow() should return the same message.");

        // Also checks that the object implements the Action interface
        assertTrue(action instanceof Action, "TextAction should implement the Action interface.");
    }
}