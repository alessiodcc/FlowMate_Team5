package flowmate_team5;

// Concrete Creator in the Factory Method pattern
public class CopyFileActionCreator implements CreatorAction {

    @Override
    public Action createAction() {
        // Instantiates the specific product (CopyFileAction)
        return new CopyFileAction();
    }
}