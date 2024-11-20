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
import java.net.URL;
import java.util.ResourceBundle;

public final class VIPController extends CustomerController {
    @FXML private Label info;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        super.initialize(url, resourceBundle);
        info.setText("Your orders will be given priority over orders by regular customers");
    }
    @FXML
    protected void onAllItems(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("Items-Page.fxml"));
            Parent root = loader.load();
            ((ItemsController)loader.getController()).setIsVIP(true);
            ((ItemsController)loader.getController()).setCustomer(this.customer);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 1000, 1000);
            stage.setScene(scene);
            stage.show();
        } catch (IOException ioe) {
            System.out.println("Input/output error");
        }
    }
}
