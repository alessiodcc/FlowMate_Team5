package flowmate_team5;

import java.util.HashMap;
import java.util.Map;

// Registry class to manage Factory Method creators
public class RuleFactoryManager {

    // Maps to store instances of the specific creators associated with their display names
    private static final Map<String, CreatorTrigger> triggerFactories = new HashMap<>();
    private static final Map<String, CreatorAction> actionFactories = new HashMap<>();

    static {
        // -------- REGISTER TRIGGERS --------
        triggerFactories.put(
                "Temporal Trigger",
                new TemporalTriggerCreator()
        );
        triggerFactories.put(
                "File Exists Trigger",
                new FileExistsTriggerCreator()
        );

        // -------- REGISTER ACTIONS --------
        actionFactories.put(
                "Message Action",
                new MessageActionCreator()
        );
        actionFactories.put(
                "Play Audio Action",
                new PlayAudioActionCreator()
        );
        actionFactories.put(
                "Write to Text File Action",
                new TextActionCreator()
        );
        actionFactories.put(
                "Copy File Action",
                new CopyFileActionCreator()
        );
        actionFactories.put(
                "Move File Action",
                new MoveFileActionCreator()
        );
        actionFactories.put(
                "Delete File Action",
                new DeleteFileActionCreator()
        );
    }

    // -------- PUBLIC API --------

    /**
     * Retrieves the specific creator for the given trigger type string
     * and returns a new Trigger instance.
     */
    public static Trigger createTrigger(String type) {
        CreatorTrigger creator = triggerFactories.get(type);
        if (creator == null) {
            throw new IllegalArgumentException("Unknown trigger: " + type);
        }
        return creator.createTrigger();
    }

    /**
     * Retrieves the specific creator for the given action type string
     * and returns a new Action instance.
     */
    public static Action createAction(String type) {
        CreatorAction creator = actionFactories.get(type);
        if (creator == null) {
            throw new IllegalArgumentException("Unknown action: " + type);
        }
        return creator.createAction();
    }
}