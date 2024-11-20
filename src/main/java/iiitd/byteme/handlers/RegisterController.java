package iiitd.byteme.handlers;

import iiitd.byteme.Main;
import iiitd.byteme.database.CustomerList;
import iiitd.byteme.users.Customer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public final class RegisterController {

    @FXML private TextField password;
    @FXML private TextField username;
    @FXML private Label alert;
    @FXML private TextField address;

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    protected void onRegister(ActionEvent event) throws IOException {
        try{
            String username = this.username.getText();
            String password = this.password.getText();
            String address = this.address.getText();
            if (username.isEmpty() || password.isEmpty() || address.isEmpty()) {
                alert.setText("Field cannot be left empty");
                this.username.setText("");
                this.password.setText("");
                this.address.setText("");
            }
            else if(CustomerList.getCustomer(username) != null) {
                alert.setText("Username is already taken");
                this.username.setText("");
                this.password.setText("");
                this.address.setText("");
            }
            else{
                CustomerList.addCustomer(new Customer(username, password, address));
                this.onBack(event);
            }
        }
        catch(NullPointerException e){
            System.out.println(e.getMessage());
            alert.setText("Field cannot be left empty");
        }
    }

    @FXML
    protected void onBack(ActionEvent event) throws IOException {
        root = FXMLLoader.load(Main.class.getResource("Home-Page.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root, 1000, 1000);
        stage.setScene(scene);
        stage.show();
    }

}
