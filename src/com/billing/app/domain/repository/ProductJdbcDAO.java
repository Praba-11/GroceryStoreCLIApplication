package com.billing.app.domain.repository;

import com.billing.app.domain.entity.Product;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;

public class ProductJdbcDAO implements ProductDAO {
    ConnectionDB connectionDB = new ConnectionDB();
    ArrayList<Product> arrayList = new ArrayList<>();
    Product product;
    @Override
    public boolean create(Product product) throws CustomException {

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
            return rowsAffected > 0;
        }
        catch (ClassNotFoundException | SQLException exception) {
            throw new CustomException(exception.getMessage());
        }
    }



    @Override
    public boolean edit(Product product) throws CustomException {

        // Edit Product in Database table
        try {
            Statement statement = connectionDB.getConnection().createStatement();
            int rowsAffected = 0;
            Field[] fields = product.getClass().getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                Object value = field.get(product);
                String query = "UPDATE products SET " + field.getName() + " = '" + value + "' WHERE code = '" + product.getCode() + "'";
                rowsAffected = statement.executeUpdate(query);
            }
            return rowsAffected > 0;
        }
        catch (SQLException | ClassNotFoundException | IllegalAccessException exception) {
            throw new CustomException(exception.getMessage());
        }

    }



    @Override
    public boolean delete(String code) throws CustomException {

        // Delete Product in Database table
        try {
            int rowsAffected;
            String query = "UPDATE products SET isDeleted = " + true + " WHERE code = '" + code + "'";
            PreparedStatement preparedStatement = connectionDB.getConnection().prepareStatement(query);
            preparedStatement.executeUpdate();
            rowsAffected = preparedStatement.executeUpdate();
            preparedStatement.close();
            return rowsAffected > 0;
        }
        catch (SQLException | ClassNotFoundException exception) {
            throw new CustomException(exception.getMessage());
        }
    }




    public ArrayList<Product> list() throws CustomException {

        // List's first 20 Products from Database table
        try {
            String query = "SELECT * FROM products LIMIT 20";
            Statement statement = connectionDB.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                String code = resultSet.getString(1);
                String name = resultSet.getString(2);
                String unitCode = resultSet.getString(3);
                String type = resultSet.getString(4);
                float price = resultSet.getFloat(5);
                int stock = resultSet.getInt(6);
                boolean isDeleted = resultSet.getBoolean(7);
                product = new Product(code, name, unitCode, type, price, stock, isDeleted);
                arrayList.add(product);
            }

            return arrayList;
        }
        catch (SQLException | ClassNotFoundException exception) {
            throw new CustomException(exception.getMessage());
        }
    }




    @Override
    public ArrayList<Product> list(int range) throws CustomException {

        // List's number of Products over a specified range from Database table
        try {
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
                boolean isDeleted = resultSet.getBoolean(7);
                product = new Product(code, name, unitCode, type, price, stock, isDeleted);
                arrayList.add(product);
            }
            return arrayList;
        }
        catch (SQLException | ClassNotFoundException exception) {
            throw new CustomException(exception.getMessage());
        }
    }




    @Override
    public ArrayList<Product> list(int range, int page) throws CustomException {
        // List's range of Products by pagination from Database table
        try {
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
                boolean isDeleted = resultSet.getBoolean(7);
                product = new Product(code, name, unitCode, type, price, stock, isDeleted);
                arrayList.add(product);
            }
            return arrayList;
        }
        catch (SQLException | ClassNotFoundException exception) {
            throw new CustomException(exception.getMessage());
        }
    }





    @Override
    public ArrayList<Product> list(String searchText) throws CustomException {

        // Search for instances of searchText in Database table
        try {
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
                boolean isDeleted = resultSet.getBoolean(7);
                product = new Product(code, name, unitCode, type, price, stock, isDeleted);
                arrayList.add(product);
            }
            return arrayList;
        }
        catch (SQLException | ClassNotFoundException exception) {
            throw new CustomException(exception.getMessage());
        }
    }





    @Override
    public ArrayList<Product> list(String attribute, String searchText) throws CustomException {

        // Search for instances of searchText using attribute in the Database table
        try {
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
                boolean isDeleted = resultSet.getBoolean(7);
                product = new Product(code, name, unitCode, type, price, stock, isDeleted);
                arrayList.add(product);
            }
            return arrayList;
        }
        catch (SQLException | ClassNotFoundException exception) {
            throw new CustomException(exception.getMessage());
        }
    }




    @Override
    public ArrayList<Product> list(String attribute, String searchText, int range, int page) throws CustomException {

        // Search for instances of searchText using attribute, range and pagination
        try {
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
                boolean isDeleted = resultSet.getBoolean(7);
                product = new Product(code, name, unitCode, type, price, stock, isDeleted);
                arrayList.add(product);
            }
            return arrayList;
        }
        catch (SQLException | ClassNotFoundException exception) {
            throw new CustomException(exception.getMessage());
        }
    }




    public int getStock(String code) throws CustomException {

        // Returns the stock of the product based on product code provided
        try {
            int stock = 0;
            String query = "SELECT stock FROM products WHERE code = '" + code + "'";
            Statement statement = connectionDB.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                stock = resultSet.getInt(1);
            }
            return stock;
        }
        catch (SQLException | ClassNotFoundException exception) {
            throw new CustomException(exception.getMessage());
        }
    }




    public Product getProduct(String code) throws CustomException {

        // Returns the product based on the product code provided
        try {
            String query = "SELECT * FROM products WHERE code = '" + code + "'";
            Statement statement = connectionDB.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                String id = resultSet.getString(1);
                String name = resultSet.getString(2);
                String unitCode = resultSet.getString(3);
                String type = resultSet.getString(4);
                float price = resultSet.getFloat(5);
                int stock = resultSet.getInt(6);
                boolean isDeleted = resultSet.getBoolean(7);
                product = new Product(id, name, unitCode, type, price, stock, isDeleted);
            }
            return product;
        }
        catch (SQLException | ClassNotFoundException exception) {
            throw new CustomException(exception.getMessage());
        }
    }




    public int getCount() throws CustomException {

        // Returns the count of the products in the database table
        try {
            int count = 0;
            String query = "SELECT COUNT(code) FROM products";
            Statement statement = connectionDB.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next())
                count = resultSet.getInt(1);
            return count;
        }
        catch (SQLException | ClassNotFoundException exception) {
            throw new CustomException(exception.getMessage());
        }
    }

}
