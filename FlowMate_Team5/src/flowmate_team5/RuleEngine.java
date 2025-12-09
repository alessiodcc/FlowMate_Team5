package flowmate_team5;

import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class RuleEngine {

    private final List<Rule> rules = new ArrayList<>();
    private final ScheduledExecutorService scheduler =
            Executors.newSingleThreadScheduledExecutor();
    
    List<Rule> getRules() {
    return rules;
}


    // ✔ Usato dall'app reale → avvia il thread periodico
    public RuleEngine() {
        startScheduler();
    }

    // ✔ Usato nei test → nessun thread
    public RuleEngine(boolean startScheduler) {
        if (startScheduler) {
            startScheduler();
        }
    }

    private void startScheduler() {
        scheduler.scheduleAtFixedRate(this::checkAllRules, 0, 2, TimeUnit.SECONDS);
    }

    public void checkAllRules() {
        for (Rule rule : rules) {
            if (rule.isActive() && rule.getTrigger().isTriggered()) {
                rule.getAction().execute();
            }
        }
    }

    public void addRule(Rule newRule) {
        rules.add(newRule);
    }

    /**
     * Task 7.1: Delete a Rule.
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
