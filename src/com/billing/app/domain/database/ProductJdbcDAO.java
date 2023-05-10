package com.billing.app.domain.database;

import com.billing.app.domain.entity.Product;
import com.billing.app.domain.exceptions.*;
import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;

public class ProductJdbcDAO implements ProductDAO {
    ConnectionDB connectionDB = new ConnectionDB();

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
        int rowsAffected = preparedStatement.executeUpdate();
        if (rowsAffected == 0) {
            throw new CodeNotFoundException("Code not found in the product relation table.");
        }
        return product;
    }


    @Override
    public boolean delete(String key, String value) throws SQLException, ClassNotFoundException {
        ConnectionDB connectionDB = new ConnectionDB();
        String query = "UPDATE product SET isdeleted = " + true + " WHERE " + key + " = '" + value + "'";
        PreparedStatement preparedStatement = connectionDB.getConnection().prepareStatement(query);
        int rowsAffected = preparedStatement.executeUpdate();
        preparedStatement.close();
        return rowsAffected > 0;
    }
}
