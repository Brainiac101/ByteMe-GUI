package iiitd.byteme.database;

import iiitd.byteme.users.Customer;

import java.util.HashMap;

public final class CustomerList {
    private final static HashMap<String, Customer> customers = new HashMap<>();

    public static void addCustomer(Customer customer) {
        customers.put(customer.getUsername(), customer);
    }

    public static void updateCustomer(Customer customer) {
        Customer oldCustomer = customers.replace(customer.getUsername(), customer);
        if(oldCustomer == null) System.out.println("No customer with username " + customer.getUsername());
    }

    public static Customer getCustomer(String username) {
        return customers.get(username);
    }
}
