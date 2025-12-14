package flowmate_team5;

// Concrete Creator in the Factory Method pattern
public class MoveFileActionCreator implements CreatorAction {

    @Override
    public Action createAction() {
        // Instantiates the specific product (MoveFileAction)
        return new MoveFileAction();
    }
}