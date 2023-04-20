package com.billing.app.domain.repository;

import com.billing.app.domain.entity.Product;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class JdbcProductDAO implements ProductDAO {
    ConnectionDB connectionDB = new ConnectionDB();

    @Override
    public void create(Product product) throws Throwable {

        // Storing Product in Database table
        try {
            String query = "INSERT INTO products (code, name, unit_code, type, price, stock) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = connectionDB.getConnection().prepareStatement(query);
            preparedStatement.setString(1, product.getCode());
            preparedStatement.setString(2, product.getName());
            preparedStatement.setString(3, product.getUnitCode());
            preparedStatement.setString(4, product.getType());
            preparedStatement.setFloat(5, product.getPrice());
            preparedStatement.setInt(6, 0);
            int rowsAffected = preparedStatement.executeUpdate();
            preparedStatement.close();
            if (rowsAffected > 0) throw new CustomException("Product created successfully!");
            else throw new CustomException("Product creation failed");
        }
        catch (SQLException exception) {
            throw new CustomException("Error creating record into database: " + exception.getMessage());
        }

    }

    @Override
    public void edit(String code, ArrayList arrayList) throws SQLException, ClassNotFoundException {

        // Edit Product in Database table
        Statement statement = connectionDB.getConnection().createStatement();
        for (int index = 0; index < arrayList.size()-1; index++) {
            String query = "UPDATE products SET " + arrayList.get(index) + " = '" + arrayList.get(index + 1) + "' WHERE code = '" + code + "'";
            statement.executeUpdate(query);
            index = index + 1;
        }
    }


    @Override
    public void delete(String code) throws SQLException, ClassNotFoundException {

        // Delete Product in Database table
        String query = "DELETE FROM products WHERE code = '" + code + "'";
        PreparedStatement preparedStatement = connectionDB.getConnection().prepareStatement(query);
        preparedStatement.executeUpdate();
        preparedStatement.close();

        System.out.println("Product deleted.");
    }

    @Override
    public void list(int range) throws SQLException, ClassNotFoundException {

        // List's range of Products as default in Database table
        String query = "SELECT * FROM products LIMIT '" + range + "'";
        Statement statement = connectionDB.getConnection().createStatement();
        ResultSet resultSet = statement.executeQuery(query);

        while (resultSet.next()) {
            String code = resultSet.getString(1);
            String name = resultSet.getString(2);
            String unitCode = resultSet.getString(3);
            String type = resultSet.getString(4);
            float price = resultSet.getFloat(5);
            int stock = resultSet.getInt(6);
            System.out.println(code + ": " + name + ": " + unitCode + ": " + type + ": " + price + ": " + stock);
        }

    }

    @Override
    public void list(int range, int page) throws SQLException, ClassNotFoundException {

        // List's range of Products by pagination in Database table
        String query = "SELECT * FROM products OFFSET '" + (range * (page - 1)) + "'" + "LIMIT '" + range + "'";
        Statement statement = connectionDB.getConnection().createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        while (resultSet.next()) {
            String code = resultSet.getString(1);
            String name = resultSet.getString(2);
            String unitCode = resultSet.getString(3);
            String type = resultSet.getString(4);
            float price = resultSet.getFloat(5);
            int stock = resultSet.getInt(6);
            System.out.println(code + ": " + name + ": " + unitCode + ": " + type + ": " + price + ": " + stock);
        }

    }

    @Override
    public void list(String searchText) throws SQLException, ClassNotFoundException {

        // Search for instances of searchText in Database table
        String query = "SELECT * FROM products WHERE code || name || unit_code || type || price || stock LIKE '%" + searchText + "%'";
        Statement statement = connectionDB.getConnection().createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        while (resultSet.next()) {
            String code = resultSet.getString(1);
            String name = resultSet.getString(2);
            String unitCode = resultSet.getString(3);
            String type = resultSet.getString(4);
            float price = resultSet.getFloat(5);
            int stock = resultSet.getInt(6);
            System.out.println(code + ": " + name + ": " + unitCode + ": " + type + ": " + price + ": " + stock);
        }

    }

    @Override
    public void list(String attribute, String searchText) throws SQLException, ClassNotFoundException {

        // Search for instances of searchText using attribute in the Database table
        String query = "SELECT * FROM products WHERE " + attribute + " LIKE '%" + searchText + "%'";
        Statement statement = connectionDB.getConnection().createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        while (resultSet.next()) {
            String code = resultSet.getString(1);
            String name = resultSet.getString(2);
            String unitCode = resultSet.getString(3);
            String type = resultSet.getString(4);
            float price = resultSet.getFloat(5);
            int stock = resultSet.getInt(6);
            System.out.println(code + ": " + name + ": " + unitCode + ": " + type + ": " + price + ": " + stock);
        }

    }

    @Override
    public void list(String attribute, String searchText, int range, int page) throws SQLException, ClassNotFoundException {

        // Search for instances of searchText using attribute, range and pagination
        String query = "SELECT * FROM (SELECT * FROM products OFFSET " + range + " * (" + page + " - 1) LIMIT " + range + " ) subquery WHERE " + attribute + " = '" + searchText + "'";
        Statement statement = connectionDB.getConnection().createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        while (resultSet.next()) {
            String code = resultSet.getString(1);
            String name = resultSet.getString(2);
            String unitCode = resultSet.getString(3);
            String type = resultSet.getString(4);
            float price = resultSet.getFloat(5);
            int stock = resultSet.getInt(6);
            System.out.println(code + ": " + name + ": " + unitCode + ": " + type + ": " + price + ": " + stock);
        }

    }

}
