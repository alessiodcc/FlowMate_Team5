package flowmate_team5;

import flowmate_team5.models.Action;
import flowmate_team5.models.actions.TextAction;
import org.junit.jupiter.api.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the TextAction class.
 * These tests verify correct file writing, appending behaviour,
 * file creation, and error handling.
 */
class TextActionTest {

    // Temporary file used for testing file output
    private Path tempFile;

    // Used to capture System.err output for error handling tests
    private final java.io.ByteArrayOutputStream errContent =
            new java.io.ByteArrayOutputStream();

    private final java.io.PrintStream originalErr = System.err;

    /**
     * Creates a temporary file and redirects System.err
     * before each test.
     */
    @BeforeEach
    void setUp() throws IOException {
        tempFile = Files.createTempFile("TextActionTest", ".txt");
        System.setErr(new java.io.PrintStream(errContent));
    }

    /**
     * Restores System.err and deletes temporary files
     * after each test.
     */
    @AfterEach
    void tearDown() throws IOException {
        System.setErr(originalErr);
        Files.deleteIfExists(tempFile);
    }

    /**
     * Utility method to create and configure a TextAction.
     *
     * @param path the file path
     * @param message the message to write
     * @return a configured TextAction instance
     */
    private TextAction createConfiguredAction(String path, String message) {
        TextAction action = new TextAction();
        action.setFilePath(path);
        action.setMessage(message);
        return action;
    }

    /**
     * Verifies that execute() successfully writes a message to the file
     * and does not produce any error output.
     */
    @Test
    void testExecute_SuccessfulWrite() throws IOException {
        String message = "Hello world";

        TextAction action = createConfiguredAction(
                tempFile.toString(),
                message
        );

        action.execute();

        String content = Files.readString(tempFile);
        assertEquals(message + System.lineSeparator(), content);
        assertTrue(errContent.toString().isEmpty());
    }

    /**
     * Verifies that multiple executions append content
     * instead of overwriting the file.
     */
    @Test
    void testExecute_AppendContent() throws IOException {
        createConfiguredAction(tempFile.toString(), "Line 1").execute();
        createConfiguredAction(tempFile.toString(), "Line 2").execute();

        String content = Files.readString(tempFile);

        String expected =
                "Line 1" + System.lineSeparator() +
                        "Line 2" + System.lineSeparator();

        assertEquals(expected, content);
    }

    /**
     * Verifies that the file is automatically created
     * if it does not already exist.
     */
    @Test
    void testExecute_FileCreationIfNotExist() throws IOException {
        Path newFile = Files.createTempDirectory("textAction")
                .resolve("created.txt");

        Files.deleteIfExists(newFile);

        TextAction action = createConfiguredAction(
                newFile.toString(),
                "Created file"
        );

        action.execute();

        assertTrue(Files.exists(newFile));

        String content = Files.readString(newFile);
        assertEquals("Created file" + System.lineSeparator(), content);

        Files.deleteIfExists(newFile);
        Files.deleteIfExists(newFile.getParent());
    }

    /**
     * Verifies that I/O errors are handled gracefully
     * and do not cause the application to crash.
     */
    @Test
    void testExecute_IOErrorHandling() {
        String invalidPath = System.getProperty("java.io.tmpdir");

        TextAction action = createConfiguredAction(
                invalidPath,
                "Should fail"
        );

        assertDoesNotThrow(action::execute);
        assertFalse(
                errContent.toString().isEmpty(),
                "Stack trace should be printed to System.err"
        );
    }

    /**
     * Verifies that TextAction correctly implements
     * the Action interface.
     */
    @Test
    void testImplementsActionInterface() {
        TextAction action = new TextAction();
        assertTrue(action instanceof Action);
    }
}
