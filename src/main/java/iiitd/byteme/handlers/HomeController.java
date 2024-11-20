package iiitd.byteme.handlers;

import iiitd.byteme.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public final class HomeController {
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    protected void onLogin(ActionEvent event) throws IOException {
        root = FXMLLoader.load(Main.class.getResource("Login-Page.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root, 1000, 1000);
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    protected void onRegister(ActionEvent event) throws IOException {
        root = FXMLLoader.load(Main.class.getResource("Register-Page.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root, 1000, 1000);
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    protected void onExit() {System.exit(0);}
}