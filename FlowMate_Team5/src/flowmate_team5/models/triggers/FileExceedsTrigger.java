package flowmate_team5.models.triggers;

import flowmate_team5.models.Trigger;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

public class FileExceedsTrigger implements Trigger, Serializable {

    private String fileName;
    // Transient because Path is not Serializable (we save it manually)
    transient private Path folderPath;
    private long maxSizeInBytes;
    transient private LocalTime timeToTrigger;

    private boolean hasTriggered = false;

    public FileExceedsTrigger() {
        // Empty constructor for Factory
    }

    // --- Setters ---
    public void setFolderPath(Path folderPath) {
        this.folderPath = folderPath;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setMaxSizeInBytes(long size) {
        this.maxSizeInBytes = size;
    }
    public LocalTime getTimeToTrigger() {
        return timeToTrigger;
    }

    public void setTimeToTrigger(LocalTime timeToTrigger) {
        // Truncate input to minutes to ignore seconds during comparison
        this.timeToTrigger = timeToTrigger.truncatedTo(ChronoUnit.MINUTES);
    }

    // --- Getters ---
    public Path getFolderPath() { return folderPath; }
    public String getFileName() { return fileName; }
    public long getMaxSizeInBytes() { return maxSizeInBytes; }

    // --- Serialization Logic (Critical for Save/Load) ---
    @Serial
    private void writeObject(ObjectOutputStream oos) throws IOException {
        oos.defaultWriteObject();
        // Manually save the path as a string
        oos.writeObject(folderPath != null ? folderPath.toString() : null);
    }

    @Serial
    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        // Manually read the string and rebuild the Path
        String pathString = (String) in.readObject();
        if (pathString != null) {
            this.folderPath = Path.of(pathString);
        }
    }

    @Override
    public boolean isTriggered() {
        if (folderPath == null || fileName == null) return false;

        Path fullPath = folderPath.resolve(fileName);
        LocalTime currentTime = LocalTime.now().truncatedTo(ChronoUnit.MINUTES);

        if (Files.exists(fullPath) && currentTime.equals(timeToTrigger)) {
            try {
                long currentSize = Files.size(fullPath);

                // Trigger only if size is strictly greater than max
                if (currentSize > maxSizeInBytes) {
                    // Ensure it fires only once per "breach"
                    if (!hasTriggered) {
                        hasTriggered = true;
                        return true;
                    }
                } else {
                    // Reset if file size drops below limit (e.g., user cleared the logs)
                    hasTriggered = false;
                }
            } catch (IOException e) {
                System.err.println("Error reading file size: " + e.getMessage());
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "File Size > " + maxSizeInBytes + " bytes";
    }
}