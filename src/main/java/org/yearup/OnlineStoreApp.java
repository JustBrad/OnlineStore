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

    // Display the home screen
    public void displayHomeScreen()
    {
        while(true)
        {
            System.out.println("\n-----WELCOME-TO-THE-ONLINE-STORE-------\n");
            System.out.println("What would you like to do?\n");
            System.out.println("0) Quit");
            System.out.println("1) Show Products");
            System.out.println("2) Show Cart\n");
            System.out.print("Enter an option: ");
            int option = scanner.nextInt();
            scanner.nextLine();

            switch(option)
            {
                case 0:
                    System.out.println("Quitting...");
                    return;
                case 1:
                    showProducts();
                    break;
                case 2:
                    showCart();
                    break;
                default:
                    System.out.println("\nInvalid option.");
                    break;
            }
        }

    }
    public void showProducts()
    {
        System.out.println("\n----------SHOWING-ALL-PRODUCTS----------\n");
//        for (int i = 0; i < inventory.size(); i++)
//        {
//            System.out.println("\nID: " + inventory.get(i).getId());
//            System.out.println("Name: " + inventory.get(i).getName());
//            System.out.printf("Price: $%.2f\n", inventory.get(i).getPrice());
//        }

        for(Product product : inventory)
        {

            System.out.println("----------------------------------------");
            System.out.printf("ID: %s\n", product.getId());
            System.out.printf("Name: %s\n", product.getName());
            System.out.printf("Price: $%.2f\n", product.getPrice());
        }
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
        displayHomeScreen();
    }
}
