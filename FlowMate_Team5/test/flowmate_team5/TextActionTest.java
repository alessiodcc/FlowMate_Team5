package flowmate_team5;

import org.junit.jupiter.api.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class TextActionTest {

    private Path tempFile;
    private final java.io.ByteArrayOutputStream errContent = new java.io.ByteArrayOutputStream();
    private final java.io.PrintStream originalErr = System.err;

    @BeforeEach
    void setUp() throws IOException {
        tempFile = Files.createTempFile("TextActionTest", ".txt");
        System.setErr(new java.io.PrintStream(errContent));
    }

    @AfterEach
    void tearDown() throws IOException {
        System.setErr(originalErr);
        Files.deleteIfExists(tempFile);
    }

    private TextAction createConfiguredAction(String path, String message) {
        TextAction action = new TextAction();
        action.setFilePath(path);
        action.setMessage(message);
        return action;
    }

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

    @Test
    void testExecute_IOErrorHandling() {
        String invalidPath = System.getProperty("java.io.tmpdir");

        TextAction action = createConfiguredAction(
                invalidPath,
                "Should fail"
        );

        assertDoesNotThrow(action::execute);
        assertFalse(errContent.toString().isEmpty(),
                "Stack trace should be printed to System.err");
    }

    @Test
    void testImplementsActionInterface() {
        TextAction action = new TextAction();
        assertTrue(action instanceof Action);
    }
}
