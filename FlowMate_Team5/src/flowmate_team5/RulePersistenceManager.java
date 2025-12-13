package flowmate_team5;

import java.io.*;
import java.util.List;

public class RulePersistenceManager {

    private static final String FILE_NAME = "rules.dat";

    /**
     * Save all rules to file
     */
    public static void saveRules(List<Rule> rules) {
        try (ObjectOutputStream oos =
                     new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {

            oos.writeObject(rules);
            System.out.println("[Persistence] Rules saved successfully.");

        } catch (IOException e) {
            System.err.println("[Persistence] Error saving rules.");
            e.printStackTrace();
        }
    }
}
