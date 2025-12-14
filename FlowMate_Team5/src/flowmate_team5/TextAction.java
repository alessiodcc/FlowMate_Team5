package flowmate_team5;

import java.io.FileWriter;
import java.io.IOException;

public class TextAction implements Action {

    private String filePath;
    private String message;

    public TextAction() {
        // Costruttore vuoto
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public void execute() {
        try (FileWriter writer = new FileWriter(filePath, true)) {
            writer.write(message + System.lineSeparator());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
