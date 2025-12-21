package flowmate_team5.models.actions;

import flowmate_team5.models.Action;

import java.io.FileWriter;
import java.io.IOException;

/**
 * Action that writes a text message to a file.
 * The message is appended to the file each time the action is executed.
 */
public class TextAction implements Action {

    // Path of the file where the message will be written
    private String filePath;

    // Message to be written to the file
    private String message;

    /**
     * Default constructor.
     * Required for Factory Method usage.
     */
    public TextAction() {
    }

    /**
     * Sets the path of the output file.
     *
     * @param filePath the file path where the message will be written
     */
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Sets the message to be written to the file.
     *
     * @param message the text message
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Executes the action by appending the message to the specified file.
     * Each execution writes the message on a new line.
     */
    @Override
    public void execute() {
        // Open the file in append mode
        try (FileWriter writer = new FileWriter(filePath, true)) {

            // Write the message followed by a system-dependent line separator
            writer.write(message + System.lineSeparator());

        } catch (IOException e) {
            // Handle file I/O errors
            e.printStackTrace();
        }
    }
}
