package iiitd.byteme.handlers;

import iiitd.byteme.Main;
import iiitd.byteme.database.OrderList;
import iiitd.byteme.logistics.Item;
import iiitd.byteme.logistics.Order;
import iiitd.byteme.logistics.Status;
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
import java.util.HashMap;
import java.util.ResourceBundle;

public final class PendingController implements Initializable {
    @FXML private Label alert;
    @FXML private TableView<Order> table;
    @FXML private TableColumn<Order, Integer> id;
    @FXML private TableColumn<Order, HashMap<Item, Integer>> items;
    @FXML private TableColumn<Order, Status> status;
    @FXML private TableColumn<Order, String> requests;
    @FXML private TableColumn<Order, Integer> price;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        items.setCellValueFactory(new PropertyValueFactory<>("items"));
        status.setCellValueFactory(new PropertyValueFactory<>("status"));
        requests.setCellValueFactory(new PropertyValueFactory<>("request"));
        price.setCellValueFactory(new PropertyValueFactory<>("price"));

        ObservableList<Order> list = FXCollections.observableArrayList(OrderList.getPendingOrders());
        if (list.isEmpty()) alert.setText("No pending orders");
        else {
            alert.setText("");
            table.setItems(list);
        }
    }

    @FXML
    protected void onBack(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(Main.class.getResource("Admin-Page.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 1000, 1000);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
