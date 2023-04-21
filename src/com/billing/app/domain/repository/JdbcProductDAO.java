package com.billing.app.domain.repository;

import com.billing.app.domain.entity.Product;

import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class JdbcProductDAO implements ProductDAO {
    ConnectionDB connectionDB = new ConnectionDB();


    @Override
    public Product create(Product product) throws CustomException {

        // Storing Product in Database table
        try {
            String query = "INSERT INTO products (code, name, unitCode, type, price, stock, isDeleted) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = connectionDB.getConnection().prepareStatement(query);
            preparedStatement.setString(1, product.getCode());
            preparedStatement.setString(2, product.getName());
            preparedStatement.setString(3, product.getUnitCode());
            preparedStatement.setString(4, product.getType());
            preparedStatement.setFloat(5, product.getPrice());
            preparedStatement.setInt(6, product.getStock());
            preparedStatement.setBoolean(7, product.isDeleted());
            int rowsAffected = preparedStatement.executeUpdate();
            preparedStatement.close();
            if (rowsAffected > 0) return product;
        }
        catch (SQLException | ClassNotFoundException exception) {
            throw new CustomException("Error creating record into database. " + exception.getMessage());
        }
        return null;
    }



    @Override
    public Product edit(Product product) throws CustomException, SQLException, ClassNotFoundException, IllegalAccessException {

        // Edit Product in Database table
        Statement statement = connectionDB.getConnection().createStatement();
        int rowsAffected = 0;
        Field[] fields = product.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            Object value = field.get(product);
            if (value != product.getCode() && value != null) {
                System.out.println(field.getName() + " : " + value);
                String query = "UPDATE products SET " + field.getName() + " = '" + value + "' WHERE code = '" + product.getCode() + "'";
                rowsAffected = statement.executeUpdate(query);
            }
        }
        if (rowsAffected > 0) return product;
        else throw new CustomException("No records edited in database.");
    }



    @Override
    public void delete(String code) throws SQLException, ClassNotFoundException, CustomException {

        // Delete Product in Database table
        int rowsAffected = 0;
        String query = "DELETE FROM products WHERE code = '" + code + "'";
        PreparedStatement preparedStatement = connectionDB.getConnection().prepareStatement(query);
        preparedStatement.executeUpdate();
        preparedStatement.close();
        rowsAffected = preparedStatement.executeUpdate();
        if (rowsAffected > 0) throw new CustomException("Product deleted successfully!");
        else throw new CustomException("No records deleted in database.");
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
