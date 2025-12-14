package flowmate_team5;

// Concrete Creator in the Factory Method pattern
public class PlayAudioActionCreator implements CreatorAction {

    @Override
    public Action createAction() {
        // Instantiates the specific product (PlayAudioAction)
        return new PlayAudioAction();
    }
}