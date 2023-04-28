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

    // ID map for easier searching
    static HashMap<String, Product> idMap = new HashMap<>();

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
                String productId = product.getId();
                idMap.put(productId, product);
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
                    System.out.println("\nQuitting...");
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

        // Print out info for each product
        for(Product product : inventory)
        {
            System.out.println("----------------------------------------");
            System.out.printf("ID: %s\n", product.getId());
            System.out.printf("Name: %s\n", product.getName());
            System.out.printf("Price: $%.2f\n", product.getPrice());
        }

        while(true)
        {
            System.out.println("\nEnter the ID of a product to add it to your cart, or");
            System.out.println("type 'X' to return to the home screen.\n");
            System.out.print("Enter ID or 'X': ");
            String option = scanner.nextLine();

            // Return home if 'X'
            if(option.equalsIgnoreCase("X")) {return;}

            // If item with specified ID exists
            if(idMap.containsKey(option))
            {
                // Get the product from ID map
                Product product = idMap.get(option);

                // Add product to cart
                if(!cart.containsKey(product.getName()))
                {
                    cart.put(product.getName(), 1);
                }
                else
                {
                    int currentQuantity = cart.get(product.getName());
                    cart.put(product.getName(), currentQuantity + 1);
                }

                // Display message
                System.out.println();
                System.out.println("'" + product.getName() + "' has been added to the cart.");
                //System.out.println(cart.get(product.getName()));

                // Return to home screen
                return;
            }
            else
            {
                System.out.println("\nInvalid ID.");
            }


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
