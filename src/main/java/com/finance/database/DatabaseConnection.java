package com.finance.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/finance_tracker?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String USER = "finance_user";
    private static final String PASSWORD = "finance_password";

    private static Connection conn = null;

    public Connection getConnection() {
        if (conn != null && !isClosed(conn)) return conn;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.err.println("MySQL driver not found");
            e.printStackTrace();
            return null;
        }

        for (int i = 0; i < 15; i++) {
            try {
                conn = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println("Connection established successfully");
                return conn;
            } catch (SQLException e) {
                System.err.println("Connection attempt " + (i + 1) + " failed, retrying in 2s...");
                try { Thread.sleep(2000); } catch (InterruptedException ie) { Thread.currentThread().interrupt(); break; }
            }
        }
        System.err.println("Failed to connect after 15 attempts");
        return null;
    }

    private boolean isClosed(Connection c) {
        try { return c.isClosed(); } catch (SQLException e) { return true; }
    }
}
