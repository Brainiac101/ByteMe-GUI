package iiitd.byteme.handlers;

import iiitd.byteme.Main;
import iiitd.byteme.database.ItemList;
import iiitd.byteme.logistics.*;
import iiitd.byteme.users.Customer;
import iiitd.byteme.users.VIP;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public final class ItemsController implements Initializable {
    @FXML private TableView<Item> table;
    @FXML private TableColumn<Item, String> name;
    @FXML private TableColumn<Item, Integer> price;
    @FXML private TableColumn<Item, Category> category;
    @FXML private TableColumn<Item, Integer> avail;
    @FXML private Label alert;

    private boolean isVIP;
    private Customer customer;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        price.setCellValueFactory(new PropertyValueFactory<>("price"));
        category.setCellValueFactory(new PropertyValueFactory<>("category"));
        avail.setCellValueFactory(new PropertyValueFactory<>("availability"));
        price.setCellValueFactory(new PropertyValueFactory<>("price"));

        ObservableList<Item> list = FXCollections.observableArrayList(ItemList.getItems());
        if (list.isEmpty()) alert.setText("No items present");
        else {
            alert.setText("");
            table.setItems(list);
        }
    }

    public void setIsVIP(boolean isVIP) {
        this.isVIP = isVIP;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    @FXML
    protected void onBack(ActionEvent event) {
        try {
            if(!isVIP){
                FXMLLoader loader = new FXMLLoader(Main.class.getResource("Customer-Page.fxml"));
                Parent root = loader.load();
                ((CustomerController)loader.getController()).setCustomer(this.customer);
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                Scene scene = new Scene(root, 1000, 1000);
                stage.setScene(scene);
                stage.show();
            } else {
                FXMLLoader loader = new FXMLLoader(Main.class.getResource("VIP-Page.fxml"));
                Parent root = loader.load();
                ((VIPController)loader.getController()).setCustomer(this.customer);
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                Scene scene = new Scene(root, 1000, 1000);
                stage.setScene(scene);
                stage.show();
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
