package flowmate_team5;

// Concrete Creator in the Factory Method pattern
public class MessageActionCreator implements CreatorAction {

    @Override
    public Action createAction() {
        // Instantiates the specific product (MessageAction)
        return new MessageAction();
    }
}