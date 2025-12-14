package flowmate_team5;

public class TextActionCreator implements CreatorAction {

    @Override
    public Action createAction() {
        // Creates and returns a new TextAction instance
        return new TextAction();
    }
}
