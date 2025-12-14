package flowmate_team5;

import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;

public class DeleteFileAction implements Action, Serializable {

    private Path filePath;

    public DeleteFileAction() {
        // Empty constructor (Factory Method)
    }

    public Path getFilePath() {
        return filePath;
    }

    public void setFilePath(Path filePath) {
        this.filePath = filePath;
    }

    @Override
    public void execute() {
        try {
            // Attempt to delete the file if it exists
            Files.deleteIfExists(filePath);
            System.out.println("[DeleteFileAction] File deleted: " + filePath);
        } catch (IOException e) {
            System.out.println("Couldn't delete the file: " + filePath);
        } catch (SecurityException e) {
            System.out.println("Couldn't delete the file due to denied permissions: " + filePath);
        }
    }

    @Override
    public String toString() {
        return "Delete File Action";
    }
}