package flowmate_team5.factory.creators;

import flowmate_team5.models.Action;
import flowmate_team5.models.actions.TextAction;
import flowmate_team5.factory.CreatorAction;

/* Factory class responsible for creating instances of TextAction. */
public class TextActionCreator implements CreatorAction {

    /* Instantiates and returns a new TextAction object. */
    @Override
    public Action createAction() {
        // Return a new instance to be configured by the controller
        return new TextAction();
    }
}