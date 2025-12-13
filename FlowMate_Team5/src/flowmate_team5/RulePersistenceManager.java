package flowmate_team5;

import java.io.*;
import java.util.ArrayList;
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

    /**
     * Load all rules from file
     */
    public static List<Rule> loadRules() {
        List<Rule> rules = new ArrayList<>();
        File file = new File(FILE_NAME);

        if (!file.exists()) {
            System.out.println("[Persistence] No saved rules found. Starting fresh.");
            return rules;
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            Object obj = ois.readObject();
            if (obj instanceof List<?>) {
                rules = (List<Rule>) obj;
            }
            System.out.println("[Persistence] Loaded " + rules.size() + " rules.");
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("[Persistence] Error loading rules.");
            e.printStackTrace();
        }

        return rules;
    }
}