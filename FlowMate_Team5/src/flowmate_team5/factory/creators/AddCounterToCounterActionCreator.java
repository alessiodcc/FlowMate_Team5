package flowmate_team5.factory.creators;

import flowmate_team5.factory.CreatorAction;
import flowmate_team5.models.Action;
import flowmate_team5.models.actions.AddCounterToCounterAction;

public class AddCounterToCounterActionCreator implements CreatorAction {

    @Override
    public Action createAction() {
        return new AddCounterToCounterAction();
    }
}