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

/**
 * Action that plays an audio file when executed.
 *
 * This action is configurable through a file path and is designed
 * to be created via the Factory Method pattern.
 */
public class PlayAudioAction implements Action, Serializable {

    // Path to the audio file to be played
    private String filePath;

    /**
     * Default constructor.
     * Required by the Factory Method pattern.
     */
    public PlayAudioAction() {
    }

    /**
     * Sets the file path of the audio file to play.
     *
     * @param filePath the path to the audio file
     */
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Executes the action by playing the configured audio file.
     * Handles common audio-related exceptions.
     */
    @Override
    public void execute() {
        try {
            File audioFile = new File(filePath);

            // Obtain an audio input stream from the file
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

    /**
     * Returns a human-readable description of the action.
     *
     * @return a string representation of the action
     */
    @Override
    public String toString() {
        return "Play Audio Action";
    }
}
