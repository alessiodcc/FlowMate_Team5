package flowmate_team5.models.triggers;

import flowmate_team5.models.Trigger;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.io.ObjectInputStream;
import java.lang.ClassNotFoundException;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

public class FileExistsTrigger implements Trigger, Serializable {

    private String fileName;
    // Path and LocalTime are not Serializable, so they are marked transient
    transient private Path folderPath;
    transient private LocalTime timeToTrigger;
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

    public LocalTime getTimeToTrigger() {
        return timeToTrigger;
    }

    public void setTimeToTrigger(LocalTime timeToTrigger) {
        // Truncate input to minutes to ignore seconds during comparison
        this.timeToTrigger = timeToTrigger.truncatedTo(ChronoUnit.MINUTES);
    }

    private void writeObject(ObjectOutputStream oos) throws IOException {
        oos.defaultWriteObject();
        oos.writeObject(folderPath != null ? folderPath.toString() : null);
        oos.writeObject(timeToTrigger != null ? timeToTrigger.toString() : null);
    }

    @Override
    public boolean isTriggered() {

        Path filePath = folderPath.resolve(fileName);
        LocalTime currentTime = LocalTime.now().truncatedTo(ChronoUnit.MINUTES);

        if (Files.exists(filePath) && currentTime.equals(timeToTrigger)) {
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
     * Reconstructs the 'folderPath' and the time because they are not saved by default.
     */
    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();

        String pathString = (String) in.readObject();
        if (pathString != null) this.folderPath = Path.of(pathString);

        String timeString = (String) in.readObject();
        if (timeString != null) this.timeToTrigger = LocalTime.parse(timeString);
    }
}