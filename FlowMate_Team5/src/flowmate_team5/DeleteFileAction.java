package flowmate_team5;

import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 *
 * @author Alessio
 */
public class DeleteFileAction implements Action, Serializable {
    private Path filePath;

    public DeleteFileAction(Path filePath){
        this.filePath = filePath;
    }

    public Path getFilePath() {
        return filePath;
    }

    public void setFilePath(Path filePath) {
        this.filePath = filePath;
    }

    @Override
    public void execute() {
        try {
            Files.deleteIfExists(filePath);
        } catch (IOException e) {
            System.out.println("Couldn't delete the file:" + filePath);
        }
        catch(SecurityException e) {
            System.out.println("Couldn't delete the file due to denied permissions:" + filePath);
        }
    }
}