package com.billing.app.domain.database;

import com.billing.app.domain.entity.Product;
import com.billing.app.domain.exceptions.*;
import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductJdbcDAO implements ProductDAO {
    ConnectionDB connectionDB = new ConnectionDB();
    Product product;
    List<Product> productList;

    @Override
    public Product create(Product product) throws ClassNotFoundException, SQLException {

        String query = "INSERT INTO product (code, name, unitcode, type, price, stock, isdeleted) VALUES (?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement preparedStatement = connectionDB.getConnection().prepareStatement(query);
        preparedStatement.setString(1, product.getCode());
        preparedStatement.setString(2, product.getName());
        preparedStatement.setString(3, product.getUnitCode());
        preparedStatement.setString(4, product.getType());
        preparedStatement.setFloat(5, product.getPrice());
        preparedStatement.setFloat(6, product.getStock());
        preparedStatement.setBoolean(7, product.isDeleted());
        preparedStatement.executeUpdate();
        preparedStatement.close();
        return product;
    }


    @Override
    public Product edit(Product product) throws ClassNotFoundException, SQLException, CodeNotFoundException {

        String query = "UPDATE product SET name = ?, unitcode = ?, type = ?, price = ?, stock = ? WHERE code = ?";
        PreparedStatement preparedStatement = connectionDB.getConnection().prepareStatement(query);
        preparedStatement.setString(1, product.getName());
        preparedStatement.setString(2, product.getUnitCode());
        preparedStatement.setString(3, product.getType());
        preparedStatement.setFloat(4, product.getPrice());
        preparedStatement.setFloat(5, product.getStock());
        preparedStatement.setString(6, product.getCode());
        preparedStatement.executeUpdate();
        return product;
    }


    @Override
    public boolean delete(String key, String value) throws SQLException, ClassNotFoundException {
        String query = "UPDATE product SET isdeleted = " + true + " WHERE " + key + " = '" + value + "'";
        PreparedStatement preparedStatement = connectionDB.getConnection().prepareStatement(query);
        int rowsAffected = preparedStatement.executeUpdate();
        preparedStatement.close();
        return rowsAffected > 0;
    }


    public List<Product> list(int range, int page, String attribute, String searchText) throws SQLException, ClassNotFoundException {
        productList = new ArrayList<>();
        String query = "SELECT * FROM product WHERE (" + attribute + " ILIKE '%" + searchText + "%') LIMIT " + range + " OFFSET " + (page - 1)*range;
        Statement statement = connectionDB.getConnection().createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        while (resultSet.next()) {
            product = new Product();
            product.setId(resultSet.getInt(1));
            product.setCode(resultSet.getString(2));
            product.setName(resultSet.getString(3));
            product.setUnitCode(resultSet.getString(4));
            product.setType(resultSet.getString(5));
            product.setPrice(resultSet.getFloat(6));
            product.setStock(resultSet.getFloat(7));
            product.setDeleted(resultSet.getBoolean(8));
            productList.add(product);
        }
        return productList;
    }


    public Product getByCode(String code) throws SQLException, ClassNotFoundException {
        String query = "SELECT * FROM product WHERE id = '" + code + "'";
        Statement statement = connectionDB.getConnection().createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        product = new Product();
        while (resultSet.next()) {
            product.setId(resultSet.getInt(1));
            product.setCode(resultSet.getString(2));
            product.setName(resultSet.getString(3));
            product.setUnitCode(resultSet.getString(4));
            product.setType(resultSet.getString(5));
            product.setPrice(resultSet.getFloat(6));
            product.setStock(resultSet.getFloat(7));
            product.setDeleted(resultSet.getBoolean(8));
        }
        return product;
    }
}
