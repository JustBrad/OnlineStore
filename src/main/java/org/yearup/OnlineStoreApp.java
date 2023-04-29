package org.yearup;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class OnlineStoreApp
{
    // Create arraylist & scanner for user input
    static ArrayList<Product> inventory = new ArrayList<>();
    static Scanner scanner = new Scanner(System.in);

    // Cart holds Name of item, Number of item
    static HashMap<String, Integer> cart = new HashMap<>();

    // ID map for easier searching
    static HashMap<String, Product> idMap = new HashMap<>();

    private double total = 0;

    // Load products from inventory.csv into ArrayList & ID HashMap
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

                // Load ID hashmap
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
            // Display options
            System.out.println("\n------WELCOME-TO-THE-ONLINE-STORE-------\n");
            System.out.println("What would you like to do?\n");
            System.out.println("0) Quit");
            System.out.println("1) Show Products");
            System.out.println("2) Show Cart\n");
            System.out.print("Enter an option: ");
            int option = 0;

            // Handle invalid input
            try
            {
                option = Integer.parseInt(scanner.nextLine());
            }
            catch(NumberFormatException e)
            {
                System.out.println("\nInvalid input.");
                continue;
            }

            // Perform action based on input
            switch(option)
            {
                case 0:
                    System.out.println("\nQuitting...");
                    System.exit(0);
                    break;
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

    // List all products & prompt user for ID to add to cart
    public void showProducts()
    {
        System.out.println("\n----------SHOWING-ALL-PRODUCTS----------\n");

        // Print out info for each product using inventory ArrayList
        for(Product product : inventory)
        {
            System.out.println("----------------------------------------");
            System.out.printf("ID: %s\n", product.getId());
            System.out.printf("Name: %s\n", product.getName());
            System.out.printf("Price: $%.2f\n", product.getPrice());
        }

        // Prompt user for ID or if they want to return to home screen
        while(true)
        {
            System.out.println("\nEnter the ID of a product to add it to your cart, or");
            System.out.println("type 'X' to return to the home screen.\n");
            System.out.print("Enter ID or 'X': ");
            String option = scanner.nextLine();
            option = option.toUpperCase();

            // Return home if 'X'
            if(option.equalsIgnoreCase("X")) {return;}

            // If item with specified ID exists, add to cart & update quantities
            if(idMap.containsKey(option))
            {
                // Get the product from ID map
                Product product = idMap.get(option);

                // If it's the first time product is being added, set quantity to 1
                if(!cart.containsKey(product.getName()))
                {
                    cart.put(product.getName(), 1);
                }
                else    // Add 1 to current quantity
                {
                    int currentQuantity = cart.get(product.getName());
                    cart.put(product.getName(), currentQuantity + 1);
                }

                // Add the price to the total
                total += product.getPrice();

                // Notify user that item has been added & display
                System.out.println();
                System.out.println("'" + product.getName() + "' has been added to the cart. (+$" + product.getPrice() + ")");

                // Return to home screen
                return;
            }
            else
            {
                System.out.println("\nItem does not exist.");
            }


        }

    }

    // Show everything in cart & provide option to checkout or return to home screen
    public void showCart()
    {
        System.out.println("\n-------------------CART-------------------\n");
        System.out.println("Product                           Quantity\n");

        // Print each product in the cart
        for (Map.Entry <String, Integer> map : cart.entrySet())
        {
            System.out.printf("%-40s %-14s\n", map.getKey(), map.getValue());
        }
        System.out.printf("\nTOTAL: $%.2f\n", total);
        System.out.println("\n------------------------------------------\n");

        // Prompt user
        while(true)
        {
            System.out.println("\nWhat do you want to do?\n");
            System.out.println("C) Check out");
            System.out.println("R) Remove an item");
            System.out.println("E) Empty the cart");;
            System.out.println("X) Return to home screen\n");
            System.out.print("Enter an option: ");
            String option = scanner.nextLine();

            // Checkout
            if(option.equalsIgnoreCase("C"))
            {
                // Can't checkout if cart is empty
                if(total > 0)
                {
                    checkOut();
                }
                else
                {
                    System.out.println("\nYour cart is empty.");
                }
            }
            // Remove item from cart
            else if(option.equalsIgnoreCase("R"))
            {
                // If cart is empty
                if(total == 0)
                {
                    System.out.println("\nThere are no items to remove.");
                }
                else
                {
                    removeItem();
                }
            }
            // Empty the cart
            else if(option.equalsIgnoreCase("E"))
            {
                // Only if cart is NOT empty
                if(!cart.isEmpty())
                {
                    // Confirm
                    System.out.print("\nAre you sure? (y/n) ");
                    String yesNo = scanner.nextLine();
                    if (yesNo.equalsIgnoreCase("Y"))
                    {
                        cart.clear();
                        total = 0;
                        System.out.println("\n! CART HAS BEEN WIPED !");
                        displayHomeScreen();
                    } else if (yesNo.equalsIgnoreCase("N"))
                    {
                    } else
                    {
                        System.out.println("\nInvalid option.");
                    }
                }
                else
                {
                    System.out.println("\nYour cart is already empty.");
                }
            }
            // Return to home screen
            else if(option.equalsIgnoreCase("X"))
            {
                return;
            }
            else
            {
                System.out.println("\nInvalid option.");
            }
        }

    }

    // Remove an item from the cart
    // Case-sensitive, kind of an afterthought
    public void removeItem()
    {
        double price = 0;
        System.out.print("\nEnter the name of the item you want to remove: ");
        String item = scanner.nextLine();

        if(cart.containsKey(item))
        {
            int quantity = cart.get(item);

            // If only one left, remove completely
            if(quantity == 1)
            {
                cart.remove(item);
            }
            // Otherwise, subtract 1 from quantity
            else
            {
                cart.put(item, quantity - 1);
            }

            // Loop through products to find the price of removed item
            for(Product product : inventory)
            {
                // Find product in arraylist
                if(item.equalsIgnoreCase(product.getName()))
                {
                    // Subtract price from total
                    price = product.getPrice();
                    total -= price;
                }
            }

            System.out.printf("\n'%s' has been removed. (-$%.2f)\n", item, price);
            displayHomeScreen();
        }
        else
        {
            System.out.println("\nThis item is not in your cart");
        }
    }

    // Display total, calculate change, reset total & cart
    public void checkOut()
    {
        System.out.println("\n----------------CHECK-OUT----------------\n");
        System.out.printf("Your total is: $%.2f\n", total);
        System.out.print("\nEnter a payment amount: $");
        double payment = scanner.nextDouble();
        scanner.nextLine();

        // If payment is insufficient, return money
        if(payment < total)
        {
            System.out.println();
            System.out.println("! INSUFFICIENT FUNDS !");
            System.out.println("Your $" + payment + " has been returned.");
        }
        else
        {
            // Get change & complete checkout
            double change = payment - total;
            System.out.println();
            System.out.println("! CHECKOUT COMPLETE !");
            System.out.println("\n------------------------------------------");

            // Print each product in cart
            for (Map.Entry <String, Integer> map : cart.entrySet())
            {
                System.out.printf("%-40s %-14s\n", map.getKey(), map.getValue());
            }
            System.out.println("------------------------------------------\n");
            System.out.printf("Your change is $%.2f.\n\n", change);

            // Clear cart & reset total
            cart.clear();
            total = 0;
            displayHomeScreen();
        }
    }

    // Run
    public void run()
    {
        loadInventory();
        displayHomeScreen();
    }
}
