package com.billing.app.domain.repository;

import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseSchemaJdbcDAO implements DatabaseSchemaDAO {
    ConnectionDB connectionDB = new ConnectionDB();
    public void create() throws SQLException, ClassNotFoundException {
        String query = "CREATE TABLE products (code TEXT PRIMARY KEY NOT NULL, name TEXT NOT NULL, unit_code TEXT NOT NULL, type TEXT NOT NULL, available_quantity NUMERIC NOT NULL, price NUMERIC NOT NULL, cost_price NUMERIC NOT NULL);" + "CREATE TABLE unit (name TEXT NOT NULL, code TEXT NOT NULL PRIMARY KEY, description TEXT NOT NULL, isdividable BOOLEAN NOT NULL);";
        Statement statement = connectionDB.getConnection().createStatement();
        statement.executeUpdate(query);
    }

    @Override
    public void delete() throws SQLException, ClassNotFoundException {
        String query = "DROP TABLE products;" + "DROP TABLE unit;";
        Statement statement = connectionDB.getConnection().createStatement();
        statement.executeUpdate(query);
    }
}
