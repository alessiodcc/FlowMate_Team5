package flowmate_team5.factory.creators;

import flowmate_team5.models.Action;
import flowmate_team5.models.actions.MessageAction;
import flowmate_team5.factory.CreatorAction;

// Concrete Creator in the Factory Method pattern
public class MessageActionCreator implements CreatorAction {

    @Override
    public Action createAction() {
        // Instantiates the specific product (MessageAction)
        return new MessageAction();
    }
}