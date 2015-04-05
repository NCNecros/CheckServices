package company;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

/**
 * Created by Necros on 31.03.2015.
 */
@org.springframework.stereotype.Service
public class Stage extends Application {
    @Override
    public void start(javafx.stage.Stage primaryStage) throws Exception {
        Parent parent = FXMLLoader.load(getClass().getResource("/company/sample.fxml"));
        Scene scene = new Scene(parent, 300, 200);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Проверка реестров");
        primaryStage.show();
    }
    public void start(){
        launch();
    }
}
