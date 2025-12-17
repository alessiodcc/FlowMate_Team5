package flowmate_team5;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

public class ExternalProgramAction implements Action, Serializable {

    private String commandLine;
    private String workingDirectory;

    public ExternalProgramAction() {
    }

    public void setCommandLine(String commandLine) {
        this.commandLine = commandLine;
    }

    public void setWorkingDirectory(String workingDirectory) {
        this.workingDirectory = workingDirectory;
    }

    public String getCommandLine() {
        return commandLine;
    }

    public String getWorkingDirectory() {
        return workingDirectory;
    }

    @Override
    public void execute() {
        if (commandLine == null || commandLine.isBlank()) {
            System.err.println("[ExternalProgramAction] Error: No command specified.");
            return;
        }

        try {
            // Split command for ProcessBuilder (e.g., "python script.py" -> ["python", "script.py"])
            List<String> args = Arrays.asList(commandLine.split("\\s+"));
            ProcessBuilder builder = new ProcessBuilder(args);

            if (workingDirectory != null && !workingDirectory.isBlank()) {
                builder.directory(new File(workingDirectory));
            }

            builder.start();
            System.out.println("[ExternalProgramAction] Executed: " + commandLine);

        } catch (IOException e) {
            System.err.println("[ExternalProgramAction] Failed to run: " + e.getMessage());
        }
    }

    @Override
    public String toString() {
        return "Run: " + commandLine;
    }
}