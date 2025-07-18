package com.foodordering.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/food_ordering1";
    private static final String USER = "root";
    private static final String PASSWORD = "anika123";

    static {
        try {
            // Explicitly load the JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("✅ MySQL JDBC Driver registered");
        } catch (ClassNotFoundException e) {
            System.err.println("❌ MySQL JDBC Driver not found");
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}

