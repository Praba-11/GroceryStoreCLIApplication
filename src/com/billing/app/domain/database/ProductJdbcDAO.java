package com.billing.app.domain.database;

import com.billing.app.domain.entity.Product;
import com.billing.app.domain.exceptions.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductJdbcDAO implements ProductDAO {
    private ConnectionDB connectionDB = new ConnectionDB();
    private Product product;
    private List<Product> productList;

    @Override
    public Product create(Product product) throws ClassNotFoundException, SQLException {
        String query = "INSERT INTO product (code, name, unitcode, type, price, stock, isdeleted) VALUES (?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement preparedStatement = connectionDB.getConnection().prepareStatement(query);
        PreparedStatement statement = setQuery(preparedStatement, product);
        statement.executeUpdate();
        return product;
    }


    @Override
    public Product edit(Product product) throws ClassNotFoundException, SQLException, CodeNotFoundException {
        String query = "UPDATE product SET code = ?, name = ?, unitcode = ?, type = ?, price = ?, stock = ?, isdeleted = ? WHERE id = ?";
        PreparedStatement preparedStatement = connectionDB.getConnection().prepareStatement(query);
        PreparedStatement statement = setQuery(preparedStatement, product);
        statement.setInt(8, product.getId());
        statement.executeUpdate();
        return product;
    }


    @Override
    public boolean delete(int id) throws SQLException, ClassNotFoundException {
        String query = "UPDATE product SET isdeleted = " + true + " WHERE id = " + id;
        PreparedStatement preparedStatement = connectionDB.getConnection().prepareStatement(query);
        int rowsAffected = preparedStatement.executeUpdate();
        preparedStatement.close();
        return rowsAffected > 0;
    }


    public List<Product> list(int range, int page, String attribute, String searchText) throws SQLException, ClassNotFoundException {
        String query = "SELECT * FROM product WHERE CAST(" + attribute + " AS TEXT) ILIKE '%" + searchText + "%' LIMIT " + range + " OFFSET " + page;
        Statement statement = connectionDB.getConnection().createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        List<Product> products = listProducts(resultSet);
        return products;
    }

    public List<Product> list(String searchText) throws SQLException, ClassNotFoundException {
        String query = "SELECT * FROM product WHERE CAST(id AS TEXT) ILIKE '%" + searchText + "%' OR code ILIKE '%" + searchText + "%' OR name ILIKE '%" + searchText + "%' OR unitcode ILIKE '%" + searchText + "%' OR type ILIKE '%" + searchText + "%' OR CAST(price AS TEXT) ILIKE '%" + searchText + "%' OR CAST(stock AS TEXT) ILIKE '%" + searchText + "%'";
        Statement statement = connectionDB.getConnection().createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        List<Product> products = listProducts(resultSet);
        return products;
    }

    public Product find(int id) throws SQLException, ClassNotFoundException {
        Product productFound = null;
        String query = "SELECT * FROM product WHERE id = '" + id + "'";
        Statement statement = connectionDB.getConnection().createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        product = new Product();
        while (resultSet.next()) {
            productFound = setProduct(product, resultSet);
        }
        return productFound;
    }

    public int count() throws SQLException, ClassNotFoundException {
        int count = 0;
        String query = "SELECT COUNT(*) FROM product";
        Statement statement = connectionDB.getConnection().createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        while (resultSet.next()) {
            count = resultSet.getInt(1);
        }
        return count;
    }

    private List<Product> listProducts(ResultSet resultSet) throws SQLException {
        productList = new ArrayList<>();
        while (resultSet.next()) {
            product = new Product();
            Product setProduct = setProduct(product, resultSet);
            productList.add(setProduct);
        }
        return productList;
    }

    private Product setProduct(Product product, ResultSet resultSet) throws SQLException {
        product.setId(resultSet.getInt(1));
        product.setCode(resultSet.getString(2));
        product.setName(resultSet.getString(3));
        product.setUnitCode(resultSet.getString(4));
        product.setType(resultSet.getString(5));
        product.setPrice(resultSet.getFloat(6));
        product.setStock(resultSet.getFloat(7));
        product.setDeleted(resultSet.getBoolean(8));
        return product;
    }

    private PreparedStatement setQuery(PreparedStatement preparedStatement, Product product) throws SQLException {
        preparedStatement.setString(1, product.getCode());
        preparedStatement.setString(2, product.getName());
        preparedStatement.setString(3, product.getUnitCode());
        preparedStatement.setString(4, product.getType());
        preparedStatement.setFloat(5, product.getPrice());
        preparedStatement.setFloat(6, product.getStock());
        preparedStatement.setBoolean(7, product.isDeleted());
        return preparedStatement;
    }
}
