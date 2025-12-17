package flowmate_team5.models.actions;

import flowmate_team5.models.Action;

import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class MoveFileAction implements Action, Serializable {

    private String sourcePathString;
    private String destinationDirectoryString;

    public MoveFileAction() {
        // Empty constructor (Factory Method)
    }

    public void setSourcePathString(String sourcePathString) {
        this.sourcePathString = sourcePathString;
    }

    public void setDestinationDirectoryString(String destinationDirectoryString) {
        this.destinationDirectoryString = destinationDirectoryString;
    }

    @Override
    public void execute() {
        try {
            Path source = Paths.get(sourcePathString);
            Path destDir = Paths.get(destinationDirectoryString);

            // Check if source file exists
            if (!Files.exists(source)) {
                System.err.println(
                        "[MoveFileAction] Error: Source file not found at " + sourcePathString
                );
                return;
            }

            // Create destination directory if it doesn't exist
            if (!Files.exists(destDir)) {
                Files.createDirectories(destDir);
                System.out.println(
                        "[MoveFileAction] Created missing directory: " + destinationDirectoryString
                );
            }

            // Resolve final destination path
            Path destination = destDir.resolve(source.getFileName());

            // Move file, replacing existing if necessary
            Files.move(
                    source,
                    destination,
                    StandardCopyOption.REPLACE_EXISTING
            );

            System.out.println(
                    "[MoveFileAction] Success: Moved file to " + destination.toAbsolutePath()
            );

        } catch (IOException e) {
            System.err.println("[MoveFileAction] Critical Error: Failed to move file.");
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return "Move File: " + Paths.get(sourcePathString).getFileName();
    }
}