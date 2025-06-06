import java.util.*;

// represent a customer in the restaurant app
class Customer {
    String username;
    String password;
    // names of dishes that the customer has ordered
    List<String> orderedDishes = new ArrayList<>();

    Customer(String username, String password) {
        this.username = username;
        this.password = password;
    }
}

public class RestaurantApp {

    static Scanner sc = new Scanner(System.in);
    // map to store customer data.
    static Map<String, Customer> customers = new HashMap<>();

    // List.of(...): This is a convenient way to create a fixed-size list of items in Java
    static List<String> menu = new ArrayList<>(List.of(
        "Chhole Bhature", 
        "Pizza", 
        "Crispy Veg Burger", 
        "White Sauce Pasta",
        "Garlic bread",
        "Veg Parcel",
        "Idli",
        "Dosa"
    ));

    // keeps track of the user who is currently logged in.
    static Customer currentCustomer = null;


    public static void main(String[] args) {
        int choice;
        // ensures the menu runs at least once and repeats based on user input
        do {
            System.out.println("\n--- Restaurant Ordering App ---");

            // Checks if no user is logged in
            if (currentCustomer == null) {
                System.out.println("1. Sign Up");
                System.out.println("2. Login");
                System.out.println("0. Exit");
                System.out.print("Enter choice: ");
                choice = sc.nextInt();

                //In Java, nextLine() is a method of the Scanner class used to read a complete line of input from the user.
                // It reads characters until it encounters a newline character (\n)
                sc.nextLine(); 

                switch (choice) {
                    case 1:
                        signUp();
                        break;
                    case 2:
                        login();
                        break;
                    case 0:
                        System.out.println("Thank you for visiting. Goodbye!");
                        break;
                    default:
                        System.out.println("Invalid choice!");
                }
            } else {
                System.out.println("1. View Menu");  //shows food items
                System.out.println("2. Add Food to Order");  //adds dish to user’s order
                System.out.println("3. View My Order");  //displays what user has ordered
                System.out.println("4. Logout"); //logs the user out by setting currentCustomer = null.
                System.out.println("5. Exit");
               
                System.out.print("Enter choice: ");
                choice = sc.nextInt();
                sc.nextLine();

                switch (choice) {
                    case 1:
                        viewMenu();
                        break;
                    case 2:
                        addFoodToOrder();
                        break;
                    case 3:
                        viewOrder();
                        break;
                    case 4:
                        logout();
                        break;
                    case 5:
                        System.out.println("Thank you for dining with us. Goodbye!");
                        break;
                    default:
                        System.out.println("Invalid choice!");
                }
            }
        } while (choice != 0 ); 
    }

    static void signUp() {
        System.out.print("Enter new username: ");
        String username = sc.nextLine();
        // check whether a given key exists in the map.
        if (customers.containsKey(username)) {
            System.out.println("Username already exists.");
            return;
        }
        System.out.print("Enter password: ");
        String password = sc.nextLine();

        customers.put(username, new Customer(username, password));
        System.out.println("Customer registered successfully!");
    }

    static void login() {
        if (currentCustomer != null) {
            System.out.println("Already logged in as " + currentCustomer.username);
            return;
        }

        System.out.print("Enter username: ");
        String username = sc.nextLine();
        System.out.print("Enter password: ");
        String password = sc.nextLine();
        // Retrieve existing customer by username
        Customer customer = customers.get(username);
        if (customer != null && customer.password.equals(password)) {
            currentCustomer = customer;
            System.out.println("Logged in successfully!");
        } else {
            System.out.println("Invalid credentials!");
        }
    }

    static void logout() {
        if (currentCustomer == null) {
            System.out.println("Not logged in.");
        } else {
            System.out.println("Logged out from " + currentCustomer.username);
            currentCustomer = null;
        }
    }

    static void viewMenu() {
        System.out.println("--- Menu ---");
        int index = 1;
        for (String dish : menu) {
            // prints each menu item starting from 1, followed by the item name
            System.out.println(index++ + ". " + dish);
        }
    }

    static void addFoodToOrder() {
        if (currentCustomer == null) {
            System.out.println("Please login first.");
            return;
        }

        viewMenu();
        System.out.print("Enter dish name to add to order (or type 'cancel' to go back): ");
        String dishName = sc.nextLine().trim();

        if (dishName.equalsIgnoreCase("cancel")) {
            System.out.println("Order cancelled.");
            return;
        }

        // Search for dish ignoring case in menu
        String selectedDish = null;
        for (String dish : menu) {
            if (dish.equalsIgnoreCase(dishName)) {
                selectedDish = dish;
                break;
            }
        }

        if (selectedDish == null) {
            System.out.println("Dish not found in the menu. Please try again.");
            return;
        }

        if (currentCustomer.orderedDishes.contains(selectedDish)) {
            System.out.println("Dish already in your order.");
        } else {
            currentCustomer.orderedDishes.add(selectedDish);
            System.out.println("Added " + selectedDish + " to your order.");
        }
    }

    static void viewOrder() {
        if (currentCustomer == null) {
            System.out.println("Please login first.");
            return;
        }

        if (currentCustomer.orderedDishes.isEmpty()) {
            System.out.println("You have not ordered any dishes yet.");
        } else {
            System.out.println("\n--- Your Order ---");
            for (String dish : currentCustomer.orderedDishes) {
                System.out.println("- " + dish);
            }
        }
    }
}
