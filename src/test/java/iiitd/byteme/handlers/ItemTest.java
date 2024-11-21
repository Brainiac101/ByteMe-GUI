package iiitd.byteme.handlers;

import iiitd.byteme.database.ItemList;
import iiitd.byteme.logistics.Category;
import iiitd.byteme.logistics.Item;
import iiitd.byteme.users.Customer;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ItemTest {

    private static Customer c;

    @BeforeAll
    public static void setUp() {
        Item i = new Item("juice", 10, Category.Beverage, 0);
        ItemList.addItem(i);
        c = new Customer("temp", "temp", "temp");
    }

    @Test
    public void addItem() {
        String message = c.addItem("juice", 10);
        assertEquals("Entered quantity exceeds count in Inventory", message);
    }

}