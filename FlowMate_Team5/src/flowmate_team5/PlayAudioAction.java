/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package flowmate_team5;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;


/**
 *
 * @author ester
 */
public class PlayAudioAction implements Action, Serializable {
    private final String filePath; // The absolute path to the audio file to be played.

    public PlayAudioAction(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Executes the action by attempting to load and play the audio file
     * specified in filePath. This uses Clip, which loads the entire file
     * into memory before playing.
     * It typically supports uncompressed formats like WAV and AIFF.
     */
    @Override
    public void execute() {
        try {
            File audioFile = new File(filePath);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
            
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.start();
            
        } catch (UnsupportedAudioFileException e) {
            // Error if the file format is not supported
            System.err.println("Errore: Formato audio non supportato per il file: " + filePath); 
        } catch (IOException e) {
            //Error if the file cannot be read or found at the specified path.
            System.err.println("Errore: Impossibile leggere il file audio o file non trovato: " + filePath); 
        } catch (LineUnavailableException e) {
            // Error if the audio output line is currently in use or unavailable.
            System.err.println("Errore: Linea audio non disponibile."); 
        }
    }
}
