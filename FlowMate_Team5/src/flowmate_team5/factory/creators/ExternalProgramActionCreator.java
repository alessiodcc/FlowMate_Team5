package flowmate_team5.factory.creators;

// IMPORTS
import flowmate_team5.models.Action;
import flowmate_team5.models.actions.ExternalProgramAction;
import flowmate_team5.factory.CreatorAction;

public class ExternalProgramActionCreator implements CreatorAction {

    @Override
    public Action createAction() {
        return new ExternalProgramAction();
    }
}