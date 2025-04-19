package com.foodordering.review;

import java.sql.*;
import java.util.Scanner;
import com.foodordering.util.DBConnection;

public class CompositeReview implements ReviewComponent {
    private int orderId;
    private int userId;

    public CompositeReview(int orderId, int userId) {
        this.orderId = orderId;
        this.userId = userId;
    }

    public void show() {
        Scanner sc = new Scanner(System.in);
        int reviewId = -1;

        try (Connection conn = DBConnection.getConnection()) {
            // Step 1: Insert into general reviews table
            String insertReview = "INSERT INTO reviews (order_id, user_id) VALUES (?, ?)";
            PreparedStatement stmt = conn.prepareStatement(insertReview, Statement.RETURN_GENERATED_KEYS);
            stmt.setInt(1, orderId);
            stmt.setInt(2, userId);
            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                reviewId = rs.getInt(1);
            }

            // Step 2: Prompt and insert food review
            System.out.print("Enter food rating (1-5): ");
            int foodRating = sc.nextInt();
            sc.nextLine();
            System.out.print("Enter food comment: ");
            String foodComment = sc.nextLine();
            PreparedStatement foodStmt = conn.prepareStatement("INSERT INTO food_reviews (review_id, rating, comment) VALUES (?, ?, ?)");
            foodStmt.setInt(1, reviewId);
            foodStmt.setInt(2, foodRating);
            foodStmt.setString(3, foodComment);
            foodStmt.executeUpdate();

            // Step 3: Prompt and insert service review
            System.out.print("Enter service rating (1-5): ");
            int serviceRating = sc.nextInt();
            sc.nextLine();
            System.out.print("Enter service comment: ");
            String serviceComment = sc.nextLine();
            PreparedStatement serviceStmt = conn.prepareStatement("INSERT INTO service_reviews (review_id, rating, comment) VALUES (?, ?, ?)");
            serviceStmt.setInt(1, reviewId);
            serviceStmt.setInt(2, serviceRating);
            serviceStmt.setString(3, serviceComment);
            serviceStmt.executeUpdate();

            // Step 4: Prompt and insert delivery review
            System.out.print("Enter delivery rating (1-5): ");
            int deliveryRating = sc.nextInt();
            sc.nextLine();
            System.out.print("Enter delivery comment: ");
            String deliveryComment = sc.nextLine();
            PreparedStatement deliveryStmt = conn.prepareStatement("INSERT INTO delivery_reviews (review_id, rating, comment) VALUES (?, ?, ?)");
            deliveryStmt.setInt(1, reviewId);
            deliveryStmt.setInt(2, deliveryRating);
            deliveryStmt.setString(3, deliveryComment);
            deliveryStmt.executeUpdate();

            System.out.println("✅ All reviews saved successfully.");

        } catch (SQLException e) {
            System.err.println("Database error during review submission: " + e.getMessage());
        }
    }
}