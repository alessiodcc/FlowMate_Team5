package flowmate_team5;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serial;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 *
 * @author Alessio
 */
public class FileExistsTrigger implements Trigger, Serializable {
    private String fileName; // The name of the file.
    transient Path folderPath; // the path of the folder we want to search the file in.
    private boolean hasTriggered = false; // indicates if the trigger has already fired

    // Constructor
    public FileExistsTrigger(String fileName, Path folderPath) {
        this.fileName = fileName;
        this.folderPath = folderPath;
    }

    // Getter and Setter methods
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

        oos.writeObject(folderPath.toString());
    }



    @Override
    public boolean isTriggered() {
        // Dynamic construction of the specific file path
        Path filePath = folderPath.resolve(fileName);

        // Checking if the file exists
        if (Files.exists(filePath)) {
            if (!hasTriggered) {
                hasTriggered = true;
                return true;
            } else {
                return false;
            }
        } else {
            if (hasTriggered) {
                hasTriggered = false;
            }
            return false;
        }
    }
}