package flowmate_team5.models.actions;

import flowmate_team5.models.Action;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class PlayAudioAction implements Action, Serializable {

    private String filePath; // Path to the audio file

    public PlayAudioAction() {
        // Empty constructor (Factory Method)
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public void execute() {
        try {
            File audioFile = new File(filePath);
            AudioInputStream audioStream =
                    AudioSystem.getAudioInputStream(audioFile);

            // Obtain a Clip instance and open the audio stream
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.start();

            System.out.println("[PlayAudioAction] Playback started.");

        } catch (UnsupportedAudioFileException e) {
            System.err.println(
                    "Error: Audio format not supported for file: " + filePath
            );
        } catch (IOException e) {
            System.err.println(
                    "Error: Unable to read audio file or file not found: " + filePath
            );
        } catch (LineUnavailableException e) {
            System.err.println("Error: Audio line unavailable.");
        }
    }

    @Override
    public String toString() {
        return "Play Audio Action";
    }
}