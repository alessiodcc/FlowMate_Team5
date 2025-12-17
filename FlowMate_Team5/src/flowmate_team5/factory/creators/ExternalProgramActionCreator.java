package flowmate_team5.factory.creators;

import flowmate_team5.models.actions.ExternalProgramAction;

public class ExternalProgramActionCreator implements CreatorAction {
    @Override
    public Action createAction() {
        return new ExternalProgramAction();
    }
}