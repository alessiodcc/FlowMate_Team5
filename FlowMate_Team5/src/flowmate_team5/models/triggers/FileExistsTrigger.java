package flowmate_team5.models.triggers;

import flowmate_team5.models.Trigger;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.io.ObjectInputStream;
import java.lang.ClassNotFoundException;

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
    /**
     * Called AUTOMATICALLY during LoadFromFile.
     * Reconstructs the 'folderPath' because Path objects are not saved by default.
     */
    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        // 1. Load the data
        in.defaultReadObject();

        // 2. Read the path string we saved manually
        String pathString = (String) in.readObject();

        // 3. Convert it back to a Path object
        if (pathString != null) {
            this.folderPath = Path.of(pathString);
        }
    }
}