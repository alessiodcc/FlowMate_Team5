/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package flowmate_team5;

import java.io.File;
import java.io.IOException;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;


/**
 *
 * @author ester
 */
public class PlayAudioAction implements Action{
    private final String filePath;

    public PlayAudioAction(String filePath) {
        this.filePath = filePath;
    }
    
    @Override
    public void execute() {
        try {
            File audioFile = new File(filePath);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
            
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.start();
            
        } catch (UnsupportedAudioFileException e) {
            System.err.println("Errore: Formato audio non supportato per il file: " + filePath); 
        } catch (IOException e) {
            System.err.println("Errore: Impossibile leggere il file audio o file non trovato: " + filePath); 
        } catch (LineUnavailableException e) {
            System.err.println("Errore: Linea audio non disponibile."); 
        }
    }
}
