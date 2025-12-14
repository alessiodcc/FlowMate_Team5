package flowmate_team5;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileExistsTrigger implements Trigger, Serializable {

    private String fileName;
    // Path is not Serializable, so it is marked transient
    transient private Path folderPath;
    private boolean hasTriggered = false;

    public FileExistsTrigger() {
        // Empty constructor (Factory Method)
    }

    public void setFolderPath(Path folderPath) {
        this.folderPath = folderPath;
    }

    public Path getFolderPath() {
        return folderPath;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return fileName;
    }

    private void writeObject(ObjectOutputStream oos) throws IOException {
        oos.defaultWriteObject();
        // Manually serialize the Path as a String
        oos.writeObject(folderPath.toString());
    }

    @Override
    public boolean isTriggered() {

        Path filePath = folderPath.resolve(fileName);

        if (Files.exists(filePath)) {
            // Check flag to ensure trigger fires only once when the file is detected
            if (!hasTriggered) {
                hasTriggered = true;
                return true;
            }
            return false;
        } else {
            // Reset flag if the file is removed
            hasTriggered = false;
            return false;
        }
    }

    @Override
    public String toString() {
        return "File Exists Trigger";
    }
}