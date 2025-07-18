package com.foodordering.view;

import com.foodordering.controller.OrderController;
import java.util.Scanner;

public class CommandLineInterface {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        OrderController controller = new OrderController();
        
        int choice;
        
        // Continue prompting the user until they choose to exit
        do {
            System.out.println("1. Place Order\n2. Track Order\n3. Complete Delivery\n4. Review Order\n5. Exit");
            choice = sc.nextInt();
            
            switch (choice) {
                case 1:
                    System.out.print("Enter User ID, Restaurant ID, and Order Type(regular,rush,express): ");
                    int userId = sc.nextInt();
                    int restaurantId = sc.nextInt();
                    String type = sc.next();
                    controller.placeOrder(userId, restaurantId, type);
                    break;

                case 2:
                    System.out.print("Enter your User ID: ");
                    int trackUserId = sc.nextInt();
                    System.out.print("Enter time elapsed since order placed (in minutes): ");
                    int elapsedTime = sc.nextInt();
                    controller.trackOrder(trackUserId, elapsedTime);
                    break;

                case 3:
                    System.out.print("Enter Order ID: ");
                    int oid = sc.nextInt();
                    controller.completeDelivery(oid);  // Calls completeDelivery() to handle input and update DB
                    break;

                case 4:
                    System.out.print("Enter Order ID: ");
                    int revOrderId = sc.nextInt();
                    System.out.print("Enter Your User ID: ");
                    int revUserId = sc.nextInt();
                    controller.reviewOrder(revOrderId, revUserId);
                    break;

                case 5:
                    System.out.println("Exiting program...");
                    break;

                default:
                    System.out.println("Invalid option. Please try again.");
                    break;
            }
        } while (choice != 5); // Loop continues until 'Exit' (choice 5) is selected

        sc.close();
    }
}
