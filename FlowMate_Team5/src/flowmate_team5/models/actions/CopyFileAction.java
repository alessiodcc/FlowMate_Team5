package flowmate_team5.models.actions;

import flowmate_team5.models.Action;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class CopyFileAction implements Action, Serializable {

    private String sourcePath;
    private String destinationDir;

    public CopyFileAction() {
        // Empty constructor (Factory Method)
    }

    public void setSourcePath(String sourcePath) {
        this.sourcePath = sourcePath;
    }

    public void setDestinationDir(String destinationDir) {
        this.destinationDir = destinationDir;
    }

    // Getters required for testing verification
    public String getSourcePath() {
        return sourcePath;
    }

    public String getDestinationDir() {
        return destinationDir;
    }

    @Override
    public void execute() {
        try {
            File srcFile = new File(sourcePath);
            // Check if the source file exists
            if (!srcFile.exists()) {
                System.err.println("[CopyFileAction] Source file does not exist: " + sourcePath);
                return;
            }

            // Construct destination path using the source filename
            Path destPath = Paths.get(destinationDir, srcFile.getName());

            // Perform copy, replacing if file already exists
            Files.copy(
                    Paths.get(sourcePath),
                    destPath,
                    StandardCopyOption.REPLACE_EXISTING
            );

            System.out.println("[CopyFileAction] Successfully copied to: " + destPath);

        } catch (IOException e) {
            System.err.println("[CopyFileAction] Error copying file: " + e.getMessage());
        }
    }

    @Override
    public String toString() {
        return "Copy File Action";
    }
}