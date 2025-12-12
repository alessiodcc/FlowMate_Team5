package flowmate_team5;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class TextAction implements Action {

    private final String filePath;
    private final String message;

    public TextAction(String filePath, String message) {
        this.filePath = filePath;
        this.message = message;
    }


    public String getFilePath() {
        return filePath;
    }

    public String getMessage() {
        return message;
    }


    @Override
    public void execute() {

        try ( PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(filePath, true)))) {

            out.print(message);

            System.out.println("[Text Action] Scrittura riuscita sul file: " + filePath);

        } catch (IOException e) {
            System.err.println("Errore I/O durante la TextAction sul file: " + filePath);
            e.printStackTrace();
        }
    }
    public String getMessageToShow() {
        return message;
    }
}
