package flowmate_team5.models.actions;

import flowmate_team5.models.Action;

import java.io.FileWriter;
import java.io.IOException;

public class TextAction implements Action {

    private String filePath;
    private String message;

    public TextAction() {
        // Empty constructor
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public void execute() {
        // Open the file in append mode (true)
        try (FileWriter writer = new FileWriter(filePath, true)) {
            // Write the message followed by a system-dependent line separator
            writer.write(message + System.lineSeparator());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}