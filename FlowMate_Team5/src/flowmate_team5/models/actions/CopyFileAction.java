package flowmate_team5.models.actions;

import flowmate_team5.models.Action;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

/* Represents an action that copies a specific file to a target directory. */
public class CopyFileAction implements Action, Serializable {

    private String sourcePath;
    private String destinationDir;

    /* Default constructor required for instantiation via factory. */
    public CopyFileAction() {
    }

    /* Sets the absolute path of the file to be copied. */
    public void setSourcePath(String sourcePath) {
        this.sourcePath = sourcePath;
    }

    /* Sets the directory where the copy will be placed. */
    public void setDestinationDir(String destinationDir) {
        this.destinationDir = destinationDir;
    }

    /* Retrieves the source file path. */
    public String getSourcePath() {
        return sourcePath;
    }

    /* Retrieves the destination directory path. */
    public String getDestinationDir() {
        return destinationDir;
    }

    /* Performs the file copy operation. */
    @Override
    public void execute() {
        try {
            File srcFile = new File(sourcePath);

            // Verify that the source file actually exists before attempting copy
            if (!srcFile.exists()) {
                System.err.println("[CopyFileAction] Source file does not exist: " + sourcePath);
                return;
            }

            // Resolve the full destination path preserving the original filename
            Path destPath = Paths.get(destinationDir, srcFile.getName());

            // Execute copy operation, overwriting if a file with the same name exists
            Files.copy(
                    Paths.get(sourcePath),
                    destPath,
                    StandardCopyOption.REPLACE_EXISTING
            );

            System.out.println("[CopyFileAction] Successfully copied to: " + destPath);

        } catch (IOException e) {
            // Log errors to standard error output if the operation fails
            System.err.println("[CopyFileAction] Error copying file: " + e.getMessage());
        }
    }

    /* Returns a string representation of the action type. */
    @Override
    public String toString() {
        return "Copy File Action";
    }
}