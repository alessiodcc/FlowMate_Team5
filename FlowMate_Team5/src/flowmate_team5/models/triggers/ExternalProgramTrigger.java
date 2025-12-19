package flowmate_team5.models.triggers;

import flowmate_team5.models.Trigger;
import java.io.IOException;

public class ExternalProgramTrigger implements Trigger {

    private String commandLine;

    public ExternalProgramTrigger(String commandLine) {
        this.commandLine = commandLine;
    }

    // Default constructor needed for some factories
    public ExternalProgramTrigger() {
        this("");
    }

    // Setter required for GUI configuration
    public void setCommandLine(String commandLine) {
        this.commandLine = commandLine;
    }

    public String getCommandLine() {
        return commandLine;
    }

    @Override
    public boolean isTriggered() {
        if (commandLine == null || commandLine.isEmpty()) {
            return false;
        }
        try {
            // Executes the command and checks if exit code is 0 (success)
            Process process = Runtime.getRuntime().exec(commandLine);
            int exitCode = process.waitFor();
            return exitCode == 0;
        } catch (IOException | InterruptedException e) {
            return false;
        }
    }

    @Override
    public String toString() {
        return "Program Trigger: " + commandLine;
    }
}