package flowmate_team5;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

public class TextActionTest {

    private Path tempFilePath;
    private String tempFilePathString;

    private final java.io.ByteArrayOutputStream errContent = new java.io.ByteArrayOutputStream();
    private final java.io.PrintStream originalErr = System.err;

    private CreatorAction creator;

    @BeforeEach
    public void setUp() throws IOException {
        tempFilePath = Files.createTempFile("TextActionTest", ".txt");
        tempFilePathString = tempFilePath.toString();

        System.setErr(new java.io.PrintStream(errContent));

        // Factory Method
        creator = new TextActionCreator();
    }

    @AfterEach
    public void tearDown() throws IOException {
        System.setErr(originalErr);
        Files.deleteIfExists(tempFilePath);
    }

    /**
     * Utility method to create and configure a TextAction via Factory Method.
     */
    private TextAction createConfiguredTextAction(String path, String message) {
        Action action = creator.createAction();
        assertTrue(action instanceof TextAction,
                "Factory must create a TextAction.");

        TextAction textAction = (TextAction) action;
        textAction.setFilePath(path);
        textAction.setMessage(message);

        return textAction;
    }

    /**
     * Tests the basic execution: verifies content is written to the file.
     */
    @Test
    public void testExecute_SuccessfulWrite() throws IOException {
        String message = "Hello, world!";

        TextAction action = createConfiguredTextAction(tempFilePathString, message);
        action.execute();

        String fileContent = new String(Files.readAllBytes(tempFilePath));

        assertEquals(message, fileContent,
                "The file content should match the message.");
        assertTrue(errContent.toString().isEmpty(),
                "There should be no I/O errors in System.err.");
    }

    /**
     * Tests the append functionality.
     */
    @Test
    public void testExecute_AppendContent() throws IOException {
        String message1 = "First line.\n";
        String message2 = "Second line.\n";

        createConfiguredTextAction(tempFilePathString, message1).execute();
        createConfiguredTextAction(tempFilePathString, message2).execute();

        String fileContent = new String(Files.readAllBytes(tempFilePath));
        assertEquals(message1 + message2, fileContent,
                "The file content should be the concatenation of the two messages.");
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
            Files.createDirectories(parentDir);
        }
        Files.deleteIfExists(nonExistentPath);

        String message = "This file was created.";

        createConfiguredTextAction(nonExistentPath.toString(), message).execute();

        assertTrue(Files.exists(nonExistentPath),
                "The file should have been created.");

        String createdFileContent = new String(Files.readAllBytes(nonExistentPath));
        assertEquals(message, createdFileContent,
                "The content of the created file should be correct.");

        Files.deleteIfExists(nonExistentPath);

        File parentFile = parentDir.toFile();
        if (parentFile.exists() && parentFile.list() != null && parentFile.list().length == 0) {
            Files.deleteIfExists(parentDir);
        }
    }

    /**
     * Tests I/O error handling when the path is not writable.
     */
    @Test
    public void testExecute_IOErrorHandling() {
        String directoryPath = System.getProperty("java.io.tmpdir");
        String message = "Writing attempt failed.";

        createConfiguredTextAction(directoryPath, message).execute();

        String errOutput = errContent.toString();
        assertFalse(errOutput.isEmpty(),
                "System.err should not be empty in case of I/O error.");
        assertTrue(errOutput.contains("Errore I/O"),
                "The System.err output should contain an I/O error message.");
    }
}
