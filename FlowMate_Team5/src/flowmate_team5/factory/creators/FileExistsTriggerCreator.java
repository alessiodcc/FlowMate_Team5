package flowmate_team5.factory.creators;

import flowmate_team5.models.triggers.FileExistsTrigger;
import flowmate_team5.models.Trigger;
import flowmate_team5.factory.CreatorTrigger;

// Concrete Creator in the Factory Method pattern
public class FileExistsTriggerCreator implements CreatorTrigger {

    @Override
    public Trigger createTrigger() {
        // Instantiates the specific product (FileExistsTrigger)
        return new FileExistsTrigger();
    }
}