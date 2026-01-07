package app;

import javafx.application.Application;
import javafx.stage.Stage;

public class MainApp extends Application {

    @Override
    public void start(Stage stage) {
        FirebaseConfig.initialize();
        new Dashboard().show(stage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
