package iiitd.byteme.handlers;


import javafx.application.Application;
import javafx.stage.Stage;

public class TestInitializer extends Application {
    @Override
    public void start(Stage primaryStage) {
        // Empty start method, just initializes JavaFX environment
    }

    public static void initializeToolkit() {
        // Launch the JavaFX environment in a non-blocking way
        Thread thread = new Thread(Application::launch);
        thread.setDaemon(true); // This ensures the thread does not block shutdown
        thread.start();
    }
}
