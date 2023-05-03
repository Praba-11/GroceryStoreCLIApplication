package com.billing.app.domain.database;

import com.billing.app.domain.entity.Product;
import com.billing.app.domain.exceptions.*;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;

public class ProductJdbcDAO implements ProductDAO {
    ConnectionDB connectionDB = new ConnectionDB();
    ArrayList<Product> productArrayList = new ArrayList<>();
    Product product;
    @Override
    public boolean create(Product product) throws ProductException, ClassNotFoundException {

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
        } catch (SQLException exception) {
            if (exception.getSQLState().equals("23505")) {
                throw new ProductPrimaryKeyException("Unable to modify the primary key. " + exception.getMessage());
            } else if (exception.getSQLState().equals("23502")) {
                throw new ProductNullConstraintException("Provided constraint cannot be null in relational table. " + exception.getMessage());
            } else if (exception.getSQLState().equals("23503")) {
                throw new ProductUnitException("Provided unit not present in Unit relation table. " + exception.getMessage());
            }
            throw new ProductException(exception.getMessage());
        }
    }



    @Override
    public boolean edit(Product product) throws ProductException, ClassNotFoundException, IllegalAccessException {

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
        catch (SQLException exception) {
            if (exception.getSQLState().equals("23502")) {
                throw new ProductNullConstraintException("Provided constraint cannot be null. " + exception.getMessage());
            } else if (exception.getSQLState().equals("23503")) {
                throw new ProductUnitException("Provided unit not present in Unit relation table. " + exception.getMessage());
            }
            throw new ProductException("Incompatible edit attributes. " + exception.getMessage());
        }
    }



    @Override
    public boolean delete(String id) throws ProductException, ClassNotFoundException {

        // Delete Product in Database table
        try {
            int rowsAffected;
            String query = "UPDATE products SET isDeleted = " + true + " WHERE id = '" + id + "'";
            PreparedStatement preparedStatement = connectionDB.getConnection().prepareStatement(query);
            preparedStatement.executeUpdate();
            rowsAffected = preparedStatement.executeUpdate();
            preparedStatement.close();
            return rowsAffected > 0;
        } catch (SQLException exception) {
            throw new ProductException(exception.getMessage());
        }
    }




    public ArrayList<Product> list() throws ProductException, ClassNotFoundException {

        // Returns arraylist of first 20 Products from Database table
        try {

            String query = "SELECT * FROM products LIMIT 20";
            Statement statement = connectionDB.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                String code = resultSet.getString(2);
                String name = resultSet.getString(3);
                String unitCode = resultSet.getString(4);
                String type = resultSet.getString(5);
                float price = resultSet.getFloat(6);
                int stock = resultSet.getInt(7);
                boolean isDeleted = resultSet.getBoolean(8);
                product = new Product(code, name, unitCode, type, price, stock, isDeleted);
                productArrayList.add(product);
            }
            return productArrayList;
        }
        catch (SQLException exception) {
            throw new ProductException(exception.getMessage());
        }
    }




    @Override
    public ArrayList<Product> list(int range) throws ProductException, ClassNotFoundException {

        // Returns arraylist of Products over a specified range from Database table
        try {
            String query = "SELECT * FROM products LIMIT '" + range + "'";
            Statement statement = connectionDB.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                String code = resultSet.getString(2);
                String name = resultSet.getString(3);
                String unitCode = resultSet.getString(4);
                String type = resultSet.getString(5);
                float price = resultSet.getFloat(6);
                int stock = resultSet.getInt(7);
                boolean isDeleted = resultSet.getBoolean(8);
                product = new Product(code, name, unitCode, type, price, stock, isDeleted);
                productArrayList.add(product);
            }
            return productArrayList;
        }
        catch (SQLException exception) {
            throw new ProductException(exception.getMessage());
        }
    }




    @Override
    public ArrayList<Product> list(int range, int page) throws ProductException {
        // Returns arraylist of Products by pagination from Database table
        try {
            String query = "SELECT * FROM products OFFSET '" + (range * (page - 1)) + "'" + "LIMIT '" + range + "'";
            Statement statement = connectionDB.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                String code = resultSet.getString(2);
                String name = resultSet.getString(3);
                String unitCode = resultSet.getString(4);
                String type = resultSet.getString(5);
                float price = resultSet.getFloat(6);
                int stock = resultSet.getInt(7);
                boolean isDeleted = resultSet.getBoolean(8);
                product = new Product(code, name, unitCode, type, price, stock, isDeleted);
                productArrayList.add(product);
            }
            return productArrayList;
        }
        catch (SQLException | ClassNotFoundException exception) {
            throw new ProductException(exception.getMessage());
        }
    }





    @Override
    public ArrayList<Product> list(String searchText) throws ProductException {

        // Returns arraylist of Products based on instances of searchText in Database table
        try {
            String query = "SELECT * FROM products WHERE code || name || unitcode || type || price || stock LIKE '%" + searchText + "%'";
            Statement statement = connectionDB.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                String code = resultSet.getString(2);
                String name = resultSet.getString(3);
                String unitCode = resultSet.getString(4);
                String type = resultSet.getString(5);
                float price = resultSet.getFloat(6);
                int stock = resultSet.getInt(7);
                boolean isDeleted = resultSet.getBoolean(8);
                product = new Product(code, name, unitCode, type, price, stock, isDeleted);
                productArrayList.add(product);
            }
            return productArrayList;
        }
        catch (SQLException | ClassNotFoundException exception) {
            throw new ProductException(exception.getMessage());
        }
    }





    @Override
    public ArrayList<Product> list(String attribute, String searchText) throws ProductException {

        // Returns arraylist of Products based on instances of searchText using attribute in the Database table
        try {
            String query = "SELECT * FROM products WHERE " + attribute + " LIKE '%" + searchText + "%'";
            Statement statement = connectionDB.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                String code = resultSet.getString(2);
                String name = resultSet.getString(3);
                String unitCode = resultSet.getString(4);
                String type = resultSet.getString(5);
                float price = resultSet.getFloat(6);
                int stock = resultSet.getInt(7);
                boolean isDeleted = resultSet.getBoolean(8);
                product = new Product(code, name, unitCode, type, price, stock, isDeleted);
                productArrayList.add(product);
            }
            return productArrayList;
        }
        catch (SQLException | ClassNotFoundException exception) {
            throw new ProductException(exception.getMessage());
        }
    }




    @Override
    public ArrayList<Product> list(String attribute, String searchText, int range, int page) throws ProductException {

        // Returns arraylist of Products based on instances of searchText using attribute, range and pagination
        try {
            String query = "SELECT * FROM (SELECT * FROM products OFFSET " + range + " * (" + page + " - 1) LIMIT " + range + " ) subquery WHERE " + attribute + " = '" + searchText + "'";
            Statement statement = connectionDB.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                String code = resultSet.getString(2);
                String name = resultSet.getString(3);
                String unitCode = resultSet.getString(4);
                String type = resultSet.getString(5);
                float price = resultSet.getFloat(6);
                int stock = resultSet.getInt(7);
                boolean isDeleted = resultSet.getBoolean(8);
                product = new Product(code, name, unitCode, type, price, stock, isDeleted);
                productArrayList.add(product);
            }
            return productArrayList;
        }
        catch (SQLException | ClassNotFoundException exception) {
            throw new ProductException(exception.getMessage());
        }
    }




    public int getStock(String code) throws ProductException {

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
            throw new ProductException(exception.getMessage());
        }
    }




    public Product getProductByCode(String code) throws ProductException {

        // Returns the product based on the product code provided
        try {
            String query = "SELECT * FROM products WHERE code = '" + code + "'";
            Statement statement = connectionDB.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                String id = resultSet.getString(2);
                String name = resultSet.getString(3);
                String unitCode = resultSet.getString(4);
                String type = resultSet.getString(5);
                float price = resultSet.getFloat(6);
                int stock = resultSet.getInt(7);
                boolean isDeleted = resultSet.getBoolean(8);
                product = new Product(id, name, unitCode, type, price, stock, isDeleted);
            }
            return product;
        }
        catch (SQLException | ClassNotFoundException exception) {
            throw new ProductException(exception.getMessage());
        }
    }

    public Product getProductById(String id) throws ProductException {

        // Returns the product based on the product code provided
        try {
            String query = "SELECT * FROM products WHERE id = '" + id + "'";
            Statement statement = connectionDB.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                String code = resultSet.getString(2);
                String name = resultSet.getString(3);
                String unitCode = resultSet.getString(4);
                String type = resultSet.getString(5);
                float price = resultSet.getFloat(6);
                int stock = resultSet.getInt(7);
                boolean isDeleted = resultSet.getBoolean(8);
                product = new Product(code, name, unitCode, type, price, stock, isDeleted);
            }
            return product;
        }
        catch (SQLException | ClassNotFoundException exception) {
            throw new ProductException(exception.getMessage());
        }
    }




    public int getCount() throws ProductException {

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
            throw new ProductException(exception.getMessage());
        }
    }



    public boolean isIdPresent(String id) throws ProductException {
        try {
            boolean flag = false;
            String query = "SELECT EXISTS(SELECT 1 FROM products WHERE id = '" + id + "')";
            Statement statement = connectionDB.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next())
                flag = resultSet.getBoolean(1);
            return flag;
        }
        catch (SQLException | ClassNotFoundException exception) {
            throw new ProductException(exception.getMessage());
        }

    }

    public boolean isCodePresent(String code) throws ProductException {
        try {
            boolean flag = false;
            String query = "SELECT EXISTS(SELECT 1 FROM products WHERE code = '" + code + "')";
            Statement statement = connectionDB.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next())
                flag = resultSet.getBoolean(1);
            return flag;
        }
        catch (SQLException | ClassNotFoundException exception) {
            throw new ProductException(exception.getMessage());
        }

    }
}