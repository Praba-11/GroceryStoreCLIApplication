package com.billing.app.domain.entity.repository.jdbc;

import com.billing.app.domain.entity.Product;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class JdbcProductDAO implements ProductDAO {

    @Override
    public void create(Product product) throws SQLException, ClassNotFoundException {

        // Storing Product in Database
        ConnectionDB connectionDB = new ConnectionDB();
        String sql = "INSERT INTO products (code, name, unit_code, type, price, stock) VALUES (?, ?, ?, ?, ?, ?)";
        PreparedStatement preparedStatement = connectionDB.getConnection().prepareStatement(sql);
        preparedStatement.setString(1, product.getCode());
        preparedStatement.setString(2, product.getName());
        preparedStatement.setString(3, product.getUnitCode());
        preparedStatement.setString(4, product.getType());
        preparedStatement.setFloat(5, product.getPrice());
        preparedStatement.setInt(6, 0);
        preparedStatement.executeUpdate();
        preparedStatement.close();

        System.out.println("Product entry added.");

    }

    @Override
    public void delete(String code) throws SQLException, ClassNotFoundException {

        // Delete Product in Database
        ConnectionDB connectionDB = new ConnectionDB();
        String sql = "DELETE FROM products WHERE code = '" + code + "'";
        PreparedStatement preparedStatement = connectionDB.getConnection().prepareStatement(sql);
        preparedStatement.executeUpdate();

        preparedStatement.close();

        System.out.println("Product deleted.");
    }

    @Override
    public void list(int range) throws SQLException, ClassNotFoundException {

        ConnectionDB connectionDB = new ConnectionDB();


    }

    @Override
    public void list(int range, int page) throws SQLException, ClassNotFoundException {

    }

    @Override
    public void list(String searchText) throws SQLException, ClassNotFoundException {

    }

    @Override
    public void list(String attribute, String searchText) throws SQLException, ClassNotFoundException {

    }

    @Override
    public void list(String attribute, String searchText, int range, int page) throws SQLException, ClassNotFoundException {

    }


}
