package aleksey.krhisanfov.cellularautomat;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {
    @Override
    public void start(Stage primaryStage) throws IOException {
        MainView mainView = new MainView();
        Scene scene = new Scene(mainView, 500,400);
        primaryStage.setScene(scene);
        primaryStage.show();

        mainView.draw();

    }

    public static void main(String[] args) {
        launch();
    }
}