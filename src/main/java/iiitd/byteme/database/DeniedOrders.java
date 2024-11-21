package iiitd.byteme.database;

import iiitd.byteme.logistics.Order;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public final class DeniedOrders implements Serializable {
    private static final List<Order> deniedOrders = new ArrayList<>();

    public static List<Order> getDeniedOrders() {
        return deniedOrders;
    }

    public static void addDeniedOrder(Order order) {
        deniedOrders.add(order);
    }

    public static void removeDeniedOrder(Order order) {
        deniedOrders.remove(order);
    }
}
