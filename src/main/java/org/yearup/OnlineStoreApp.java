package org.yearup;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class OnlineStoreApp
{
    // Create arraylist & scanner
    static ArrayList<Product> inventory = new ArrayList<>();
    static Scanner scanner = new Scanner(System.in);

    // Cart holds Name of item, Number of item
    static HashMap<String, Integer> cart = new HashMap<>();

    // Name map for easier searching
    static HashMap<String, Product> nameMap = new HashMap<>();

    public void loadInventory()
    {
        // Initialize stream & file scanner
        FileInputStream fileStream = null;
        Scanner fileScanner = null;

        // Only works if file found
        try
        {
            fileStream = new FileInputStream("inventory.csv");
            fileScanner = new Scanner(fileStream);

            // Loop through file & create product for each line
            while(fileScanner.hasNextLine())
            {
                String line = fileScanner.nextLine();
                String[] columns = line.split("\\|");

                String id = columns[0];
                String name = columns[1];
                double price = Double.parseDouble(columns[2]);

                // Load arraylist
                Product product = new Product(id, name, price);
                inventory.add(product);

                // Load name hashmap
                String productName = product.getName();
                nameMap.put(productName, product);
            }
        }
        catch (Exception e)
        {
            e.getMessage();
        }
        finally
        {
            if(fileScanner != null && fileStream != null)
            {
                try
                {
                    fileStream.close();
                    fileScanner.close();
                } catch (Exception e){}
            }
        }
    }

    public void displayHomeScreen()
    {

    }
    public void listAllProducts()
    {

    }

    public void showCart()
    {

    }

    public void checkOut()
    {

    }

    public void run()
    {
        loadInventory();
        System.out.println("Inventory loaded!");
    }
}
