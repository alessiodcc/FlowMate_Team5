package flowmate_team5;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 *
 * @author Alessio
 */
public class FileExistsTrigger implements Trigger{
    private String fileName; // The name of the file.
    private Path folderPath; // the path of the folder we want to search the file in.

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


    @Override
    public boolean isTriggered() {
        // Dynamic construction of the specific file path
        Path filePath = folderPath.resolve(fileName);

        // Checking if the file exists
        if (Files.exists(filePath))
            return true;
        else
            return false;
    }
}
