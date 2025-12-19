package flowmate_team5.models.triggers;

import flowmate_team5.models.Trigger;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;

public class ExternalProgramTrigger implements Trigger {

    private String commandLine; // Stores the full path or process name

    public ExternalProgramTrigger(String commandLine) {
        this.commandLine = commandLine;
    }

    public ExternalProgramTrigger() {
        this("");
    }

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

        // Extract just the filename (e.g., "notepad.exe") from the full path
        String processName = new File(commandLine).getName();

        try {
            // Run "tasklist" command to see all running processes on Windows
            Process process = Runtime.getRuntime().exec("tasklist");
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

            String line;
            while ((line = reader.readLine()) != null) {
                // Check if the process list contains our target program name
                if (line.toLowerCase().contains(processName.toLowerCase())) {
                    return true; // Found! The user has opened the program
                }
            }
            reader.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false; // Process not found
    }

    @Override
    public String toString() {
        return "Monitor Process: " + (commandLine == null ? "None" : new File(commandLine).getName());
    }
}