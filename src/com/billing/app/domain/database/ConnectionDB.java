package com.billing.app.domain.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionDB {
    public Connection getConnection() throws SQLException {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException exception) {
            System.out.println("Class not found exception.");
        }
        Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/grocery_store", "postgres", "narakabarp");
        return connection;
    }
}
