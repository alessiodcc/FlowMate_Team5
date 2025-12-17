package flowmate_team5;

public class ExternalProgramActionCreator implements CreatorAction {
    @Override
    public Action createAction() {
        return new ExternalProgramAction();
    }
}