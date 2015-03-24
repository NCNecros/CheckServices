package company;

import com.linuxense.javadbf.DBFException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;


public class Main extends Application {

    public static void main(String[] args) throws FileNotFoundException, DBFException, UnsupportedEncodingException {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Проверка счета");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }
}
