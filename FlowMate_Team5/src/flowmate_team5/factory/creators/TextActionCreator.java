package flowmate_team5.factory.creators;

import flowmate_team5.models.Action;
import flowmate_team5.models.actions.TextAction;
import flowmate_team5.factory.CreatorAction;

public class TextActionCreator implements CreatorAction {

    @Override
    public Action createAction() {
        // Creates and returns a new TextAction instance
        return new TextAction();
    }
}
