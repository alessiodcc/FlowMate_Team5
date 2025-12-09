
package flowmate_team5;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Principale
 */
public class WriteAMessageController implements Initializable {

    @FXML
    private TextArea Title;
    @FXML
    private TextArea Introduction;
    @FXML
    private Button ConfirmButton;
    @FXML
    private TextField inputText;

    private MessageAction finalAction;

    public Action getFinalAction() {
        return finalAction;
    }



    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    public void confirmButtonPushed(ActionEvent event) {
        String writtenMessage = inputText.getText();

        if (writtenMessage.trim().isEmpty()) {
            Alert emptyAlert = new Alert(AlertType.ERROR);
            emptyAlert.setTitle("ATTENTION!");
            emptyAlert.setHeaderText("To continue, it's necessary that you write something in the apposit space!");

            emptyAlert.showAndWait();
            return;
        }

        this.finalAction = new MessageAction("MessageAction1", writtenMessage);

        Node source = (Node) event.getSource();

        Stage stage = (Stage) source.getScene().getWindow();

        stage.close();

    }

}