package flowmate_team5.models.triggers;

import flowmate_team5.models.Trigger;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;

/* Implementation of the Trigger interface that monitors the operating system for a specific running process. */
public class ExternalProgramTrigger implements Trigger {

    // Stores the executable path or process name to be monitored
    private String commandLine;

    /* Initializes the trigger with the specified command line or process name. */
    public ExternalProgramTrigger(String commandLine) {
        this.commandLine = commandLine;
    }

    /* Default constructor initializing the trigger with an empty command. */
    public ExternalProgramTrigger() {
        this("");
    }

    /* Sets the command line configuration for the process to monitor. */
    public void setCommandLine(String commandLine) {
        this.commandLine = commandLine;
    }

    /* Retrieves the current command line configuration. */
    public String getCommandLine() {
        return commandLine;
    }

    /* Checks if the specified external program is currently running in the OS processes. */
    @Override
    public boolean isTriggered() {
        // Return false immediately if no command is configured
        if (commandLine == null || commandLine.isEmpty()) {
            return false;
        }

        // Extract the specific process name from the full path to match against the task list
        String processName = new File(commandLine).getName();

        try {
            // Execute the system 'tasklist' command to retrieve current running processes
            Process process = Runtime.getRuntime().exec("tasklist");
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

            String line;
            while ((line = reader.readLine()) != null) {
                // Perform a case-insensitive check to see if the process is active
                if (line.toLowerCase().contains(processName.toLowerCase())) {
                    return true; // The process was found in the active list
                }
            }
            reader.close();

        } catch (Exception e) {
            // Log exception trace if the system command fails
            e.printStackTrace();
        }

        // Return false if the process was not found in the list
        return false;
    }

    /* Returns a string representation of the trigger status for display purposes. */
    @Override
    public String toString() {
        return "Monitor Process: " + (commandLine == null ? "None" : new File(commandLine).getName());
    }
}