package iiitd.byteme.handlers;

import iiitd.byteme.Main;
import iiitd.byteme.database.CustomerList;
import iiitd.byteme.users.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

import java.io.IOException;

public final class LoginController {
    @FXML private Label header;
    @FXML private TextField username;
    @FXML private TextField password;
    @FXML private Label alert;

    private boolean type = false;
    private Stage stage;
    private Scene scene;
    private Parent root;

//   public LoginController(){
//        username.setText("");
//        password.setText("");
//        alert.setText("");
//    }
//
//    public String getAlert() {
//        return alert.getText();
//    }

    @FXML private void onAdmin() {
        header.setText("Login as: Admin");
        type = false;
    }

    @FXML private void onCustomer() {
        header.setText("Login as: Customer");
        type = true;
    }

    @FXML private void onLogin(ActionEvent event) throws IOException {
        String username;
        String password;
        try {
            username = this.username.getText();
            password = this.password.getText();
        } catch (NullPointerException e) {
            System.out.println(e.getMessage());
            alert.setTextFill(Paint.valueOf("red"));
            alert.setText("Field cannot be left empty");
            return;
        }
        if (username.isEmpty() || password.isEmpty()) {
            alert.setTextFill(Paint.valueOf("red"));
            alert.setText("Field cannot be left empty");
            return;
        }
        if (!type) {
            if (username.equals("admin") && password.equals("admin")) {
                root = FXMLLoader.load(Main.class.getResource("Admin-Page.fxml"));
                stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                scene = new Scene(root, 1000, 1000);
                stage.setScene(scene);
                stage.show();
            } else {
                alert.setText("Invalid credentials");
                this.username.setText("");
                this.password.setText("");
            }
        } else {
            Customer c = CustomerList.getCustomer(username);
            if (c == null) {
                alert.setText("Invalid credentials");
                this.username.setText("");
                this.password.setText("");
            } else if(c instanceof VIP){
                FXMLLoader loader = new FXMLLoader(Main.class.getResource("VIP-Page.fxml"));
                root = loader.load();
                ((CustomerController) loader.getController()).setCustomer((VIP) c);
                stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                scene = new Scene(root, 1000, 1000);
                stage.setScene(scene);
                stage.show();
            } else {
                FXMLLoader loader = new FXMLLoader(Main.class.getResource("Customer-Page.fxml"));
                root = loader.load();
                ((CustomerController) loader.getController()).setCustomer(c);
                stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                scene = new Scene(root, 1000, 1000);
                stage.setScene(scene);
                stage.show();
            }
        }
    }

    @FXML private void onBack(ActionEvent event) throws IOException {
        root = FXMLLoader.load(Main.class.getResource("Home-Page.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root, 1000, 1000);
        stage.setScene(scene);
        stage.show();
    }

//    public void loginTest() throws IOException {
//        this.onLogin(new ActionEvent());
//    }
}
