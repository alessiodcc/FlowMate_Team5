package flowmate_team5;

import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

/**
 * Task 13.5: Implementation of the Move File Action.
 * This class encapsulates the logic required to move a file from a source path
 * to a destination directory using the Action interface.
 *
 * @author Ayesha
 */
public class MoveFileAction implements Action, Serializable {

    private final String sourcePathString;
    private final String destinationDirectoryString;

    /**
     * Constructs a new MoveFileAction.
     *
     * @param sourcePath           The absolute path of the file to be moved.
     * @param destinationDirectory The directory where the file should be moved.
     */
    public MoveFileAction(String sourcePath, String destinationDirectory) {
        this.sourcePathString = sourcePath;
        this.destinationDirectoryString = destinationDirectory;
    }

    /**
     * Executes the move operation.
     * <p>
     * Logic:
     * 1. Checks if the source file exists.
     * 2. Checks if the destination directory exists (creates it if missing).
     * 3. Moves the file, replacing any existing file with the same name.
     */
    @Override
    public void execute() {
        try {
            // Convert String paths to Java Path objects
            Path source = Paths.get(sourcePathString);
            Path destDir = Paths.get(destinationDirectoryString);

            // Validation: Ensure source exists
            if (!Files.exists(source)) {
                System.err.println("[MoveFileAction] Error: Source file not found at " + sourcePathString);
                return;
            }

            // Validation: Ensure destination directory exists
            if (!Files.exists(destDir)) {
                Files.createDirectories(destDir);
                System.out.println("[MoveFileAction] Created missing directory: " + destinationDirectoryString);
            }

            // Construct the full destination path (Directory + Original Filename)
            Path destination = destDir.resolve(source.getFileName());

            // Perform the move (Atomic move if possible, Replace existing allowed)
            Files.move(source, destination, StandardCopyOption.REPLACE_EXISTING);

            System.out.println("[MoveFileAction] Success: Moved file to " + destination.toAbsolutePath());

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