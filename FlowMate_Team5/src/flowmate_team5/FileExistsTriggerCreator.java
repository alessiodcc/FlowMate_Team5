package flowmate_team5;

// Concrete Creator in the Factory Method pattern
public class FileExistsTriggerCreator implements CreatorTrigger {

    @Override
    public Trigger createTrigger() {
        // Instantiates the specific product (FileExistsTrigger)
        return new FileExistsTrigger();
    }
}