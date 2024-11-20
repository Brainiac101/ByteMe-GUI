package iiitd.byteme.database;

import iiitd.byteme.logistics.Order;
import iiitd.byteme.logistics.Status;

import java.util.ArrayList;
import java.util.List;

public final class CompletedOrders {
    private final static List<Order> completedOrders = new ArrayList<>();

    public static List<Order> getCompletedOrders() {
        return completedOrders;
    }

    public static void addCompletedOrder(Order order) {
        if(!completedOrders.contains(order) && order.getStatus() == Status.Delivered) {
            completedOrders.add(order);
        }
    }


}
