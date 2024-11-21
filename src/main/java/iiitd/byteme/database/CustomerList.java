package iiitd.byteme.database;

import iiitd.byteme.users.Customer;

import java.io.*;
import java.util.HashMap;

public final class CustomerList implements Serializable{
    private final static String file = "files/users.dat";

    private static HashMap<String, Customer> readFile() {
        HashMap<String, Customer> customers = new HashMap<>();
        File temp = new File(file);
        if (!temp.exists()) return customers;
        try {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(temp));
            customers = (HashMap<String, Customer>) in.readObject();
            in.close();
            return customers;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            System.exit(1);
        }
        return customers;
    }

    private static void writeFile(HashMap<String, Customer> customers) {
        try{
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file));
            out.writeObject(customers);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public static void addCustomer(Customer customer) {
        HashMap<String, Customer> customers = readFile();
        customers.put(customer.getUsername(), customer);
        writeFile(customers);
    }

    public static void updateCustomer(Customer customer) {
        HashMap<String, Customer> customers = readFile();
        Customer oldCustomer = customers.replace(customer.getUsername(), customer);
        writeFile(customers);
        if(oldCustomer == null) System.out.println("No customer with username " + customer.getUsername());
    }
    public static Customer getCustomer(String username) {
        HashMap<String, Customer> customers = readFile();
        return customers.get(username);
    }
}
