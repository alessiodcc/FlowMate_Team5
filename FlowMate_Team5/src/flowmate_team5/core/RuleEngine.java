package flowmate_team5.core;

import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import flowmate_team5.models.Counter;

/**
 * The core engine responsible for managing and evaluating all rules.
 * This class is implemented as a **Singleton** to ensure only one instance of the
 * RuleEngine and its associated scheduler runs within the application.
 */
public class RuleEngine {

    private static RuleEngine instance;

    private final List<Rule> rules = new ArrayList<>();

    // List to hold all Counter objects created in the system
    private final List<Counter> counters = new ArrayList<>();

    // Scheduler to run the checkAllRules method on a fixed schedule in a background thread.
    private final ScheduledExecutorService scheduler =
            Executors.newSingleThreadScheduledExecutor();

    private RuleEngine() {
        startScheduler();
    }

    // Private constructor alternative for tests (to avoid starting the scheduler automatically)
    private RuleEngine(boolean startScheduler) {
        if (startScheduler) {
            startScheduler();
        }
    }

    /**
     * Provides the global access point to the single instance of the RuleEngine,
     * ensuring it is initialized only once.
     * @return The single instance of RuleEngine.
     */
    public static synchronized RuleEngine getInstance() {
        if (instance == null) {
            instance = new RuleEngine();
        }
        return instance;
    }

    /**
     * Provides an alternative, thread-safe access point specifically for setup/testing
     * where the caller may choose whether to start the scheduler immediately.
     * @param startScheduler If true, the scheduler is initialized and started.
     * @return The single instance of RuleEngine.
     */
    public static synchronized RuleEngine getInstance(boolean startScheduler) {
        if (instance == null) {
            instance = new RuleEngine(startScheduler);
        }
        return instance;
    }

    /**
     * Returns the internal list of rules.
     * @return The list of rules.
     */
    public List<Rule> getRules() {
        return rules;
    }

    /**
     * Retrieves the list of all available counters.
     * @return A list of Counter objects.
     */
    public List<Counter> getCounters() { return counters; }

    /**
     * Adds a new counter to the Rule Engine.
     * @param c The Counter object to add.
     */
    public void addCounter(Counter c) {
        if (c != null) { counters.add(c);
            System.out.println("[RuleEngine] Counter added: " + c.getName());}
    }

    /**
     * Configures and starts the background thread that checks rules.
     * It runs immediately (initial delay 0) and then every 2 seconds.
     */
    private void startScheduler() {
        scheduler.scheduleAtFixedRate(this::checkAllRules, 0, 2, TimeUnit.SECONDS);
        System.out.println("[RuleEngine] Scheduler started.");
    }

    /**
     * The main logic loop. Iterates through all rules and executes the action
     * if the rule is active and its trigger condition is met.
     */
    public void checkAllRules() {
        for (Rule rule : rules) {
            rule.check();
        }
    }


    /**
     * Adds a new rule to the engine's internal list.
     * @param newRule The Rule object to add.
     */
    public void addRule(Rule newRule) {
        rules.add(newRule);
    }

    /**
     * Delete a Rule.
     * Removes the specified rule from the list.
     */
    public void deleteRule(Rule rule) {
        if (rules.contains(rule)) {
            rules.remove(rule);
            System.out.println("[RuleEngine] Rule deleted: " + rule.getName());
        } else {
            System.out.println("[RuleEngine] Error: Rule not found.");
        }
    }
}