package iiitd.byteme.database;

import iiitd.byteme.logistics.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public final class ItemList implements Serializable {
    private static final String file = "files/items.dat";

    private static List<Item> readFile() {
        List<Item> items = new ArrayList<>();
        File temp = new File(file);
        if (!temp.exists()) return items;
        try {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(temp));
            items = (List<Item>) in.readObject();
            in.close();
            return items;
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error reading file");
            System.exit(1);
        }
        return items;
    }

    private static void writeFile(List<Item> items) {
        try{
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file));
            out.writeObject(items);
        } catch (IOException e) {
            System.out.println("Error reading file");
            System.exit(1);
        }
    }

    public static void deleteFile(){
        File f = new File(file);
        if (f.delete()) {  // Try to delete the file
            System.out.println("File deleted successfully.");
        } else {
            System.out.println("Failed to delete the file.");
        }

    }

    public static void addItem(Item item) {
        List<Item> items = readFile();
        items.add(item);
        writeFile(items);
    }

    public static List<Item> getItems() {
        return readFile();
    }

    public static List<Item> getItemsByCategory(Category category) {
        List<Item> items = readFile();
        List<Item> itemsByCategory = new ArrayList<>();
        items.forEach(item -> {
            if (item.getCategory().equals(category))
                itemsByCategory.add(item);
        });
        return itemsByCategory;
    }

    public static List<Item> searchItems(String itemName) {
        List<Item> items = readFile();
        List<Item> temp = new ArrayList<>();
        final Item[] best = {null};
        items.forEach(item -> {
            if (item.getName().contains(itemName)) {
                if(item.getName().equals(itemName)){
                    best[0] = item;
                }
                temp.add(item);
            }
        });
        temp.remove(best[0]);
        temp.addFirst(best[0]);
        return temp;
    }

    public static Item getItemByName(String itemName) {
        List<Item> items = readFile();
        for (Item item : items) {
            if (item.getName().equals(itemName)) {
                return item;
            }
        }
        return null;
    }

    public static List<Item> getItemsByIncreasingPrice() {
        List<Item> itemsByIncreasingPrice = readFile();
        itemsByIncreasingPrice = itemsByIncreasingPrice.stream()
                .sorted((i1, i2) -> i1.getPrice() - i2.getPrice())
                .toList();
        return itemsByIncreasingPrice;
    }

    public static List<Item> getItemsByDecreasingPrice() {
        List<Item> itemsByDecreasingPrice = readFile();
        itemsByDecreasingPrice = itemsByDecreasingPrice.stream()
                .sorted((i1, i2) -> i2.getPrice() - i1.getPrice())
                .toList();
        return itemsByDecreasingPrice;
    }

    public static void updateItem(Item item) {
        List<Item> items = readFile();
        for (int i = 0; i < items.size(); i++) {
            Item temp = items.get(i);
            if (temp.getName().equals(item.getName()) && temp.getCategory().equals(item.getCategory())) {
                items.set(i, item);
                writeFile(items);
                break;
            }
        }
    }

    public static void removeItem(Item item) {
        List<Item> items = readFile();
        items.remove(item);
        writeFile(items);
    }
}
