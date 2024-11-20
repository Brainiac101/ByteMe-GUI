package iiitd.byteme.handlers;

import iiitd.byteme.Main;
import iiitd.byteme.database.*;
import iiitd.byteme.logistics.*;
import iiitd.byteme.users.*;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.Scanner;

public class CustomerController extends UserController implements Initializable {
    @FXML private Label header;
    @FXML private Label allDesc;
    @FXML private Label searchDesc;
    @FXML private Label filterDesc;
    @FXML private Label sortDesc;
    @FXML private Label addDesc;
    @FXML private Label modifyDesc;
    @FXML private Label removeDesc;
    @FXML private Label viewDesc;
    @FXML private Label placeDesc;
    @FXML private Label statusDesc;
    @FXML private Label cancelDesc;
    @FXML private Label historyDesc;
    @FXML private Label repeatDesc;
    @FXML private Label writeDesc;
    @FXML private Label readDesc;
    @FXML private Label vipDesc;

    protected Customer customer;
    private static final Scanner sc = new Scanner(System.in);

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Platform.runLater(() -> this.header.setText("Welcome " + this.customer.getUsername()));
    }

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

    protected void placeOrder() {
        System.out.println("• 1 to set the delivery address automatically");
        System.out.println("• 2 to set the delivery address manually");
        System.out.println("• 0 to return to menu");
        String address;
        int temp = sc.nextInt();
        sc.nextLine();
        switch (temp) {
            case 1:
                address = this.customer.getAddress();
                break;
            case 2:
                System.out.print("Enter address: ");
                address = sc.nextLine();
                break;
            case 0:
                return;
            default:
                System.out.println("Invalid input\n");
                return;
        }
        System.out.println("• 1 to pay using cash");
        System.out.println("• 2 to pay using UPI");
        System.out.println("• 3 to pay using Net banking/Cards");
        System.out.println("• 0 to return to menu");
        boolean isPaid;
        temp = sc.nextInt();
        sc.nextLine();
        switch (temp) {
            case 1:
                isPaid = false;
                break;
            case 2, 3:
                isPaid = true;
                break;
            case 0:
                return;
            default:
                System.out.println("Invalid input\n");
                return;
        }
        System.out.println("Order will be delivered to " + address + "\n");
        this.customer.checkout(address, isPaid);
    }

    @FXML
    protected void onAllItems(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("Items-Page.fxml"));
            Parent root = loader.load();
            ((ItemsController)loader.getController()).setIsVIP(false);
            ((ItemsController)loader.getController()).setCustomer(this.customer);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 1000, 1000);
            stage.setScene(scene);
            stage.show();
        } catch (IOException ioe) {
            System.out.println("Input/output error");
        }
    }

    @FXML
    protected void onAllItemsMouseEnter() {
        this.allDesc.setText("View all Items in the Menu");
    }

    @FXML
    protected void onAllItemsMouseExit() {
        this.allDesc.setText("");
    }

    @FXML
    protected void onSearch() {
        System.out.print("Enter name of the item: ");
        String itemName = sc.nextLine();
        this.customer.searchByName(itemName);
    }

    @FXML
    protected void onSearchMouseEnter() {
        this.searchDesc.setText("Search for items in the Menu by name via the Console");
    }

    @FXML
    protected void onSearchMouseExit() {
        this.searchDesc.setText("");
    }

    @FXML
    protected void onFilter() {
        Category category = this.inputCategory();
        if (category == null) return;
        this.customer.searchByCategory(category);
    }

    @FXML
    protected void onFilterMouseEnter() {
        this.filterDesc.setText("Filter Items in the Menu based on Category via the Console");
    }

    @FXML
    protected void onFilterMouseExit() {
        this.filterDesc.setText("");
    }

    @FXML
    protected void onSort() {
        System.out.println("• 1 for viewing all items in ascending order (by prices)");
        System.out.println("• 2 for viewing all items in descending order (by prices)");
        System.out.println("• 0 to return to menu");
        int temp = sc.nextInt();
        sc.nextLine();
        switch (temp) {
            case 1:
                this.customer.getItemsByIncreasingPrice();
                break;
            case 2:
                this.customer.getItemsByDecreasingPrice();
                break;
            case 0:
                break;
            default:
                System.out.println("Invalid input\n");
        }
    }

    @FXML
    protected void onSortMouseEnter() {
        this.sortDesc.setText("Sort Items based on Price (Ascending/Decreasing) via the Console");
    }

    @FXML
    protected void onSortMouseExit() {
        this.sortDesc.setText("");
    }

    @FXML
    protected void onAdd() {
        System.out.print("Enter name of the item: ");
        String itemName = sc.nextLine();
        System.out.print("Enter quantity required: ");
        int quantity = sc.nextInt();
        sc.nextLine();
        this.customer.addItem(itemName, quantity);
    }

    @FXML
    protected void onAddMouseEnter() {
        this.addDesc.setText("Add Items to Your Cart via the Console");
    }

    @FXML
    protected void onAddMouseExit() {
        this.addDesc.setText("");
    }

    @FXML
    protected void onModify() {
        this.customer.viewCart();
        System.out.println("• 1 for increasing an item's quantity by 1");
        System.out.println("• 2 for decreasing an item's quantity by 1");
        System.out.println("• 0 to return to menu");
        int temp = sc.nextInt();
        sc.nextLine();
        String itemName;
        switch (temp) {
            case 1:
                System.out.print("Enter item name: ");
                itemName = sc.nextLine();
                this.customer.increaseQuantity(itemName);
                break;
            case 2:
                System.out.print("Enter item name: ");
                itemName = sc.nextLine();
                this.customer.decreaseQuantity(itemName);
                break;
            case 0:
                break;
            default:
                System.out.println("Invalid input\n");
                break;
        }
    }

    @FXML
    protected void onModifyMouseEnter() {
        this.modifyDesc.setText("Modify quantity of an Item present in the Cart via the Console");
    }

    @FXML
    protected void onModifyMouseExit() {
        this.modifyDesc.setText("");
    }

    @FXML
    protected void onRemove() {
        this.customer.viewCart();
        System.out.print("Enter item name to remove: ");
        String itemName = sc.nextLine();
        this.customer.removeItem(itemName);
    }

    @FXML
    protected void onRemoveMouseEnter() {
        this.removeDesc.setText("Remove an Item from the Cart via the Console");
    }

    @FXML
    protected void onRemoveMouseExit() {
        this.removeDesc.setText("");
    }

    @FXML
    protected void onCart() {
        this.customer.viewCart();
    }

    @FXML
    protected void onCartMouseEnter() {
        this.viewDesc.setText("View the current contents of the Cart via the Console");
    }

    @FXML
    protected void onCartMouseExit() {
        this.viewDesc.setText("");
    }

    @FXML
    protected void onPlace() {
        System.out.println("• 1 for adding specifications to the order");
        System.out.println("• 2 for directly placing the order");
        System.out.println("• 0 to return to menu");
        int temp = sc.nextInt();
        sc.nextLine();
        switch (temp) {
            case 1:
                System.out.print("Enter specifications/requests: ");
                String specifications = sc.nextLine();
                this.customer.addSpecification(specifications);
                System.out.println("Specification added to the order\n");
            case 2:
                this.placeOrder();
                break;
            case 0:
                break;
            default:
                System.out.println("Invalid input\n");
        }
    }

    @FXML
    protected void onPlaceMouseEnter() {
        this.placeDesc.setText("Place an order of current contents of the Cart via the Console");
    }

    @FXML
    protected void onPlaceMouseExit() {
        this.placeDesc.setText("");
    }

    @FXML
    protected void onStatus() {
        System.out.println("Searching for recent orders...");
        this.customer.viewStatus();
    }

    @FXML
    protected void onStatusMouseEnter() {
        this.statusDesc.setText("View the order status of your latest order via the Console");
    }

    @FXML
    protected void onStatusMouseExit() {
        this.statusDesc.setText("");
    }

    @FXML
    protected void onCancel() {
        this.customer.cancelOrder();
    }

    @FXML
    protected void onCancelMouseEnter() {
        this.cancelDesc.setText("Cancel your latest order via the Console");
    }

    @FXML
    protected void onCancelMouseExit() {
        this.cancelDesc.setText("");
    }

    @FXML
    protected void onHistory() {
        this.customer.viewOrderHistory();
    }

    @FXML
    protected void onHistoryMouseEnter() {
        this.historyDesc.setText("View your previously placed orders in the Console");
    }

    @FXML
    protected void onHistoryMouseExit() {
        this.historyDesc.setText("");
    }

    @FXML
    protected void onRepeat() {
        this.customer.viewOrderHistory();
        System.out.print("Enter index of the order to repeat (the index is mentioned as a bullet point): ");
        int orderIndex = sc.nextInt();
        sc.nextLine();
        this.customer.repeatOrder(orderIndex);
    }

    @FXML
    protected void onRepeatMouseEnter() {
        this.repeatDesc.setText("Repeat previously placed orders via the console");
    }

    @FXML
    protected void onRepeatMouseExit() {
        this.repeatDesc.setText("");
    }

    @FXML
    protected void onWrite() {
        System.out.print("Enter item name you wish to write a review about: ");
        String itemName = sc.nextLine();
        System.out.print("Enter the review: ");
        String review = sc.nextLine();
        this.customer.submitReview(itemName, review);
    }

    @FXML
    protected void onWriteMouseEnter() {
        this.writeDesc.setText("Write a review for a used product via the Console");
    }

    @FXML
    protected void onWriteMouseExit() {
        this.writeDesc.setText("");
    }

    @FXML
    protected void onRead() {
        System.out.print("Enter item name you wish to read reviews about: ");
        String itemName = sc.nextLine();
        this.customer.viewReviews(itemName);
    }

    @FXML
    protected void onReadMouseEnter() {
        this.readDesc.setText("View the reviews for a product via the Console");
    }

    @FXML
    protected void onReadMouseExit() {
        this.readDesc.setText("");
    }

    @FXML
    protected void onVIP(ActionEvent event) {
        System.out.println("Upgrading status to VIP");
        CustomerList.updateCustomer(new VIP(this.customer.getUsername(), this.customer.getPassword(), this.customer.getAddress(), this.customer.getCart()));
        System.out.println("Please login again to ensure that VIP status has been enabled\n");
        this.onLogout(event);
    }

    @FXML
    protected void onVIPMouseEnter() {
        this.vipDesc.setText("Upgrade status to VIP to prioritize your orders");
    }

    @FXML
    protected void onVIPMouseExit() {
        this.vipDesc.setText("");
    }
}
