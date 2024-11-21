package iiitd.byteme.logistics;

import iiitd.byteme.database.DeniedOrders;
import iiitd.byteme.database.ItemList;
import iiitd.byteme.database.OrderList;
import iiitd.byteme.users.Customer;

import java.io.*;
import java.util.*;

public final class Cart implements Serializable {
    private final Customer user;
    private final HashMap<Item, Integer> items;
    private int price;
    private String request;
    private String address;
    private boolean isPaid;

    private final String file;

    public Cart(Customer user) {
        this.user = user;
        items = new HashMap<>();
        this.file = "files/" + user.getUsername() + ".dat";
    }

    public Cart(Customer user, List<Order> orders) {
        this.user = user;
        items = new HashMap<>();
        this.file = "files/" + user.getUsername() + ".dat";
        writeFile(orders);
    }

    private List<Order> readFile() {
        List<Order> orders = new ArrayList<>();
        File temp = new File(file);
        if (!temp.exists()) return orders;
        try {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(temp));
            orders = (List<Order>) in.readObject();
            in.close();
            return orders;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            System.exit(1);
        }
        return orders;
    }

    private void writeFile(List<Order> customers) {
        try{
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file));
            out.writeObject(customers);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public void setPaid(boolean paid) {
        isPaid = paid;
    }

    public void setRequest(String request) {
        this.request = request;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<Order> getOrders() {
        return this.readFile();
    }

    public String addItem(Item item, int quantity) {
        if (quantity > 0 && item.getAvailability() > quantity) {
            item.setAvailability(item.getAvailability() - quantity);
            ItemList.updateItem(item);
            this.items.put(item, quantity);
            this.price += item.getPrice() * quantity;
            System.out.println("Item added to the cart\n");
            return "Item added to the cart\n";
        } else if (quantity < 0) {
            System.out.println("Please enter valid quantity (must be greater than zero)");
            return "Please enter valid quantity (must be greater than zero)";
        }
        else System.out.println("Entered quantity exceeds count in Inventory");
        return "Entered quantity exceeds count in Inventory";
    }

    public boolean containsItem(Item item) {
        return items.containsKey(item);
    }

    public void removeItem(Item item) {
        item.setAvailability(item.getAvailability() + this.items.get(item));
        ItemList.updateItem(item);
        this.price -= item.getPrice() * this.items.get(item);
        this.items.remove(item);
    }

    public Order getLastOrder() {
        List<Order> orders = this.readFile();
        if (!orders.isEmpty()) return orders.getLast();
        else return null;
    }

    public void cancelOrder() {
        Order order = getLastOrder();
        if (order == null || order.getStatus() == Status.Delivered || order.getStatus() == Status.Cancelled || order.getStatus() == Status.Denied) {
            System.out.println("Order cancellation not possible\n");
            return;
        }
        System.out.println(order.getItems());
//        for (Item i : order.getItems().keySet()) {
        for (Item i : order.getItems().keySet()) {
            if (this.items.containsKey(i)) {
                i.setAvailability(i.getAvailability() + this.items.get(i));
                ItemList.updateItem(i);
            }
        }
        List<Order> orders = this.readFile();
        orders.remove(order);
        order.setStatus(Status.Cancelled);
        OrderList.removeOrder(order.getId());
        if (order.isPaid()) DeniedOrders.addDeniedOrder(order);
        System.out.println("Order Cancelled\n");
        this.writeFile(orders);
    }

    public void addQuantity(Item item) {
        item.setAvailability(item.getAvailability() + this.items.get(item));
        ItemList.updateItem(item);
        this.price -= item.getPrice() * this.items.get(item);
        this.addItem(item, items.get(item) + 1);
    }

    public void decreaseQuantity(Item item) {
        item.setAvailability(item.getAvailability() + this.items.get(item));
        ItemList.updateItem(item);
        this.price -= item.getPrice() * this.items.get(item);
        this.addItem(item, items.get(item) - 1);
    }

    public void placeOrder(boolean isVIP) {
        int priority = isVIP ? 1 : 0;
        if (!items.isEmpty()) {
            List<Order> orders = this.readFile();
            if(request == null) request = "No requests";
            orders.add(OrderList.addOrder(new Order(items, Status.OrderReceived, priority, request, price, address, isPaid, user)));
            this.writeFile(orders);
        }
    }

    public void placeOrder(boolean isVIP, int index) {
        List<Order> orders = this.readFile();
        Order o = orders.get(index);
        int priority = isVIP ? 1 : 0;
        o.setStatus(Status.OrderReceived);
        o.setPriority(priority);
        orders.add(OrderList.addOrder(o));
        this.writeFile(orders);
    }

    public boolean hasBoughtItem(Item item) {
        List<Order> orders = this.readFile();
        for (Order o : orders) {
            if (o.getStatus() == Status.OrderReceived) {
                for (Item i : o.getItems().keySet()) {
                    if (item.getName().equals(i.getName())) return true;
                }
            }
        }
        return false;
    }

    @Override
    public String toString() {
        StringBuilder temp = new StringBuilder();
        for(Item item : items.keySet()){
            temp.append("Name: ").append(item.getName()).append("\nPrice: ").append(item.getPrice()).append("\nCategory: ").append(item.getCategory()).append("\nQuantity: ").append(items.get(item)).append("\n");
        }
        return "Item List:\n" + temp + "\nRequests: " + this.request + "\nOrder Value: " + this.price + "\n";
    }
}