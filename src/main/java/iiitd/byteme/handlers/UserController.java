package iiitd.byteme.handlers;

import iiitd.byteme.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class UserController {
    @FXML protected Label logoutDesc;

    @FXML
    protected void onLogout(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(Main.class.getResource("Home-Page.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 1000, 1000);
            stage.setScene(scene);
            stage.show();
        } catch (IOException ioe) {
            System.out.println("Input/output error");
        }
    }

    @FXML
    protected void onLogoutMouseEnter() {
        this.logoutDesc.setText("Logout to return to Main Menu");
    }

    @FXML
    protected void onLogoutMouseExit() {
        this.logoutDesc.setText("");
    }
}
