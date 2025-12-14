package flowmate_team5;

// Concrete Creator in the Factory Method pattern
public class DeleteFileActionCreator implements CreatorAction {

    @Override
    public Action createAction() {
        // Instantiates the specific product (DeleteFileAction)
        return new DeleteFileAction();
    }
}