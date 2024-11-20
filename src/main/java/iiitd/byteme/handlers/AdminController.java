package iiitd.byteme.handlers;

import iiitd.byteme.Main;
import iiitd.byteme.database.DeniedOrders;
import iiitd.byteme.database.ItemList;
import iiitd.byteme.logistics.Category;
import iiitd.byteme.logistics.Item;
import iiitd.byteme.logistics.Order;
import iiitd.byteme.logistics.Status;
import iiitd.byteme.users.Admin;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Scanner;

public final class AdminController extends UserController {

    @FXML private Label addDesc;
    @FXML private Label removeDesc;
    @FXML private Label pendingDesc;
    @FXML private Label statusDesc;
    @FXML private Label refundDesc;
    @FXML private Label reportDesc;
    @FXML private Label priceDesc;
    @FXML private Label availDesc;

    private static final Scanner sc = new Scanner(System.in);

    private final Admin admin = Main.getAdmin();

    private Category inputCategory() {
        System.out.println("Choose Category");
        System.out.println("• 1 for Beverages");
        System.out.println("• 2 for Snacks");
        System.out.println("• 3 for Meals");
        System.out.println("• 0 to return to menu");
        int choice = sc.nextInt();
        sc.nextLine();
        return switch (choice) {
            case 1 -> Category.Beverage;
            case 2 -> Category.Snack;
            case 3 -> Category.Meal;
            case 0 -> null;
            default -> {
                System.out.println("Invalid input");
                yield null;
            }
        };
    }
    private Status inputStatus() {
        System.out.println("• 1 for Delivered");
        System.out.println("• 2 for Out for Delivery");
        System.out.println("• 3 for Preparing");
        System.out.println("• 4 for Order Received");
        System.out.println("• 0 to return to menu");
        int choice = sc.nextInt();
        sc.nextLine();
        return switch (choice) {
            case 1 -> Status.Delivered;
            case 2 -> Status.OutForDelivery;
            case 3 -> Status.Preparing;
            case 4 -> Status.OrderReceived;
            case 0 -> null;
            default -> {
                System.out.println("Invalid input");
                yield null;
            }
        };
    }

    @FXML
    protected void onAdd() {
        System.out.print("Enter item name: ");
        String itemName = sc.nextLine();
        System.out.print("Enter item price: ");
        int itemPrice = sc.nextInt();
        sc.nextLine();
        System.out.print("Enter item availability (inventory): ");
        int availability = sc.nextInt();
        sc.nextLine();
        Category category = inputCategory();
        if (availability == 0 || itemPrice == 0 || itemName == null) {
            System.out.println("Invalid input");
        } else admin.addItem(itemName, itemPrice, category, availability);
    }

    @FXML
    protected void onAddMouseEnter() {
        this.addDesc.setText("Add an Item to the Menu, Done using Console");
    }

    @FXML
    protected void onAddMouseExit() {
        this.addDesc.setText("");
    }

    @FXML
    protected void onPrice() {
        System.out.print("Enter item name: ");
        String itemName = sc.nextLine();
        Item item = ItemList.getItemByName(itemName);
        if (item == null) {
            System.out.println("No such item\n");
            return;
        }
        System.out.print("Enter item price: ");
        int itemPrice = sc.nextInt();
        sc.nextLine();
        this.admin.updateItemPrice(item, itemPrice);
        System.out.println("Item updated\n");
    }

    @FXML
    protected void onPriceMouseEnter() {
        this.priceDesc.setText("Update the price of an item, Done using Console");
    }

    @FXML
    protected void onPriceMouseExit() {
        this.priceDesc.setText("");
    }

    @FXML
    protected void onAvail() {
        System.out.print("Enter item name: ");
        String itemName = sc.nextLine();
        Item item = ItemList.getItemByName(itemName);
        if (item == null) {
            System.out.println("No such item\n");
            return;
        }
        System.out.print("Enter item availability (inventory): ");
        int availability = sc.nextInt();
        sc.nextLine();
        this.admin.updateItemAvailability(item, availability);
        System.out.println("Item updated\n");
    }

    @FXML
    protected void onAvailMouseEnter() {
        this.availDesc.setText("Update the inventory of an item, Done using Console");
    }

    @FXML
    protected void onAvailMouseExit() {
        this.availDesc.setText("");
    }

    @FXML
    protected void onRemove() {
        System.out.print("Enter item name to remove: ");
        String itemName = sc.nextLine();
        this.admin.deleteItem(itemName);
    }

    @FXML
    protected void onRemoveMouseEnter() {
        this.removeDesc.setText("Remove an Item from the Menu, Done using Console");
    }

    @FXML
    protected void onRemoveMouseExit() {
        this.removeDesc.setText("");
    }

    @FXML
    protected void onView(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(Main.class.getResource("Pending-Page.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 1000, 1000);
            stage.setScene(scene);
            stage.show();
        }
        catch (IOException ioe) {
            System.out.println("Input/output error");
        }
    }

    @FXML
    protected void onViewMouseEnter() {this.pendingDesc.setText("View all Pending Orders, New Window");}

    @FXML
    protected void onViewMouseExit() {this.pendingDesc.setText("");}

    @FXML
    protected void onStatus() {
        System.out.print("Enter order ID to be updated: ");
        int ID = sc.nextInt();
        sc.nextLine();
        Status status = inputStatus();
        this.admin.updateOrderStatus(ID, status);
    }

    @FXML
    protected void onStatusMouseEnter() {this.statusDesc.setText("Update the status of an order, Done using Console");}

    @FXML
    protected void onStatusMouseExit() {this.statusDesc.setText("");}

    @FXML
    protected void onRefund() {
        int choice;
        for (int i = 0; i < DeniedOrders.getDeniedOrders().size(); i++) {
            Order o = DeniedOrders.getDeniedOrders().get(i);
            System.out.println(o);
            System.out.println("• 1 for giving the refund");
            System.out.println("• 2 for pausing the refund for this order");
            System.out.println("• 0 to return to menu");
            choice = sc.nextInt();
            sc.nextLine();
            switch (choice) {
                case 1:
                    DeniedOrders.removeDeniedOrder(o);
                    break;
                case 2:
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Invalid input");
                    return;
            }
        }
        System.out.println("Refunds resolved\n");
    }

    @FXML
    protected void onRefundMouseEnter() {this.refundDesc.setText("Process refunds of Denied/Cancelled orders, Done using Console");}

    @FXML
    protected void onRefundMouseExit() {this.refundDesc.setText("");}

    @FXML
    protected void onReport() {this.admin.getReport();}

    @FXML
    protected void onReportMouseEnter() {this.reportDesc.setText("View a report of today's orders and profits, Done using Console");}

    @FXML
    protected void onReportMouseExit() {this.reportDesc.setText("");}
}
