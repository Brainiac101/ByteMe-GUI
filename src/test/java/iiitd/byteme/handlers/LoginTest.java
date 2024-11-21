package iiitd.byteme.handlers;

import iiitd.byteme.Main;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class LoginTest {

    private static LoginController controller;

    @BeforeAll
    public static void setUp() throws IOException {
        TestInitializer.initializeToolkit();
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("Login-Page.fxml"));
        loader.load();
        controller = loader.getController();
    }

    @Test
    void onLogin() {
        Platform.runLater(() -> {
            try {
                controller.setUsername("wrongUser");
                controller.setPassword("wrongPass");
                controller.loginTest();
                assertEquals("Invalid credentials", controller.getAlert());
            } catch (IOException e) {
                System.err.println("Input error");
            }
        });
    }

    @Test
    void onLoginEmpty(){
        Platform.runLater(() -> {
            try {
                controller.setUsername("");
                controller.setPassword("");
                controller.loginTest();
                assertEquals("Field cannot be left empty", controller.getAlert());
            } catch (IOException e) {
                System.err.println("Input error");
            }
        });
    }

}