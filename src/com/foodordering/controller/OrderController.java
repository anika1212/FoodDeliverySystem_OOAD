package com.foodordering.controller;

import com.foodordering.factory.OrderFactory;
import com.foodordering.model.Order;
import com.foodordering.command.*;
import com.foodordering.review.*;
import com.foodordering.util.DBConnection;
import java.util.Scanner;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderController {

    public void placeOrder(int userId, int restaurantId, String type) {
        Order order = OrderFactory.createOrder(userId, restaurantId, type);
        System.out.println("Order Placed: " + order.getType());

        try (Connection conn = DBConnection.getConnection()) {
            String sql = "INSERT INTO orders (user_id, restaurant_id, order_type, placed_time) VALUES (?, ?, ?, NOW())";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, order.getUserId());
            stmt.setInt(2, order.getRestaurantId());
            stmt.setString(3, order.getType());

            int rows = stmt.executeUpdate();
            if (rows > 0) {
                System.out.println("Order saved to database successfully.");
            } else {
                System.out.println("Failed to save order to database.");
            }

        } catch (SQLException e) {
            System.err.println("Database error during order placement: " + e.getMessage());
        }
    }

    public void trackOrder(int userId, int elapsedMinutes) {
        String status;
        if (elapsedMinutes <= 1) {
            status = "Placed";
        } else if (elapsedMinutes <= 10) {
            status = "Preparing";
        } else if (elapsedMinutes <= 20) {
            status = "Out for Delivery";
        } else {
            status = "Delivered";
        }

        int orderId = -1;

        try (Connection conn = DBConnection.getConnection()) {
            String sql = "SELECT order_id FROM orders WHERE user_id = ? ORDER BY placed_time DESC LIMIT 1";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                orderId = rs.getInt("order_id");

                // Insert into order_status
                String insertStatus = "INSERT INTO order_status (order_id, status) VALUES (?, ?)";
                PreparedStatement insertStmt = conn.prepareStatement(insertStatus);
                insertStmt.setInt(1, orderId);
                insertStmt.setString(2, status);
                insertStmt.executeUpdate();

                System.out.println("Order Status: " + status);
            } else {
                System.out.println("No order found for user " + userId);
            }

        } catch (SQLException e) {
            System.err.println("Database error during order tracking: " + e.getMessage());
        }
    }

	public void completeDelivery(int orderId) {
    Scanner sc = new Scanner(System.in);

    // Prompt user for delivery status and house number
    System.out.print("Enter Delivery Status ('delivered on time', 'delivered early', 'delivered late'): ");
    String status = sc.nextLine();
    
    System.out.print("Enter House Number: ");
    int houseNumber = sc.nextInt();

    // Insert into delivery_status table
    try (Connection conn = DBConnection.getConnection()) {
        String sql = "INSERT INTO delivery_status (order_id, status, house_number) VALUES (?, ?, ?)";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, orderId);
        stmt.setString(2, status);
        stmt.setInt(3, houseNumber);

        int rows = stmt.executeUpdate();
        if (rows > 0) {
            System.out.println("Delivery personnel has delivered the order.");
        } else {
            System.out.println("Failed to update delivery status.");
        }

    } catch (SQLException e) {
        System.err.println("Database error during delivery completion: " + e.getMessage());
    }
}


    public void reviewOrder(int orderId, int userId) {
        CompositeReview review = new CompositeReview(orderId, userId);
        review.show();
    }
}