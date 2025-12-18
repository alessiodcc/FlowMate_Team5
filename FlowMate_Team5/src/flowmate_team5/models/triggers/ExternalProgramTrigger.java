package flowmate_team5.models.triggers;

import flowmate_team5.models.Trigger;
import java.io.IOException;

/**
 * A Trigger implementation that executes an external command/program.
 * It is considered triggered if the external process returns exit code 0.
 */
public class ExternalProgramTrigger implements Trigger {

    private final String commandLine;

    public ExternalProgramTrigger(String commandLine) {
        this.commandLine = commandLine;
    }

    @Override
    public boolean isTriggered() {
        if (commandLine == null || commandLine.isEmpty()) {
            return false;
        }
        try {
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