/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package flowmate_team5;


import flowmate_team5.core.RuleEngine;
import flowmate_team5.core.RulePersistenceManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.image.Image;
/**

 * @author husse
 */
public class FlowMate_Team5 extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("view/MainPage.fxml"));
        Scene scene = new Scene(root);
        java.io.InputStream logoStream = getClass().getResourceAsStream("images/AppIcon.png");
        primaryStage.getIcons().add(new Image(logoStream));
        primaryStage.setScene(scene);
        primaryStage.setTitle("FlowMate - Task Automation");

        primaryStage.setOnCloseRequest(event -> {
            System.out.println("[App] Closing FlowMate...");
            RulePersistenceManager.saveRules(
                    RuleEngine.getInstance().getRules()
            );
        });

        primaryStage.show();
    }
}