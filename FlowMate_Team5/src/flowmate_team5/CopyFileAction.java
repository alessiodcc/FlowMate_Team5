package flowmate_team5;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class CopyFileAction implements Action, Serializable {
    private final String sourcePath;
    private final String destinationDir;

    public CopyFileAction(String sourcePath, String destinationDir) {
        this.sourcePath = sourcePath;
        this.destinationDir = destinationDir;
    }

    @Override
    public void execute() {
        try {
            File srcFile = new File(sourcePath);
            if (!srcFile.exists()) {
                System.err.println("[CopyFileAction] Source file does not exist: " + sourcePath);
                return;
            }

            // Construct destination path
            Path destPath = Paths.get(destinationDir, srcFile.getName());

            // Perform copy
            Files.copy(Paths.get(sourcePath), destPath, StandardCopyOption.REPLACE_EXISTING);
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