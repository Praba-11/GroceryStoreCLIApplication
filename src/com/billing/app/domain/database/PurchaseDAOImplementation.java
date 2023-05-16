package com.billing.app.domain.database;

import com.billing.app.domain.entity.Product;
import com.billing.app.domain.entity.Purchase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PurchaseDAOImplementation implements PurchaseDAO {
    Purchase purchase;
    List<Purchase> purchaseList;
    ConnectionDB connectionDB = new ConnectionDB();
    public Purchase create(Purchase purchase) throws SQLException, ClassNotFoundException {
        String query = "INSERT INTO purchase (invoice_id, purchase_date, grand_total) VALUES (?, ?, ?)";
        PreparedStatement preparedStatement = connectionDB.getConnection().prepareStatement(query);
        PreparedStatement statement = setQuery(preparedStatement, purchase);
        statement.executeUpdate();
        return purchase;
    }

    public boolean delete(int invoice) throws SQLException, ClassNotFoundException {
        String query = "DELETE FROM purchase WHERE invoice_id = " + invoice;
        Statement statement = connectionDB.getConnection().createStatement();
        int rowsAffected = statement.executeUpdate(query);
        return rowsAffected > 0;
    }

    public List<Purchase> list(int range, int page, String attribute, String searchText) throws SQLException, ClassNotFoundException {
        System.out.println(attribute);
        String query = "SELECT * FROM purchase WHERE CAST(" + attribute + " AS TEXT) ILIKE '%" + searchText + "%' LIMIT " + range + " OFFSET " + page;
        Statement statement = connectionDB.getConnection().createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        List<Purchase> purchases = listPurchases(resultSet);
        return purchases;
    }

    public List<Purchase> list(String searchText) throws SQLException, ClassNotFoundException {
        String query = "SELECT * FROM purchase WHERE CAST(invoice_id AS TEXT) ILIKE '%" + searchText + "%' OR CAST(purchase_date AS TEXT) ILIKE '%" + searchText + "%' OR CAST(grand_total AS TEXT) ILIKE '%" + searchText + "%'";
        Statement statement = connectionDB.getConnection().createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        List<Purchase> purchases = listPurchases(resultSet);
        return purchases;
    }

    private List<Purchase> listPurchases(ResultSet resultSet) throws SQLException {
        purchaseList = new ArrayList<>();
        while (resultSet.next()) {
            purchase = new Purchase();
            Purchase setPurchase = setPurchase(purchase, resultSet);
            purchaseList.add(setPurchase);
        }
        return purchaseList;
    }

    public boolean find(String code) throws SQLException, ClassNotFoundException {
        boolean flag = false;
        String query = "SELECT EXISTS(SELECT 1 FROM product WHERE code = '" + code + "') AS code_exists";
        Statement statement = connectionDB.getConnection().createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        while (resultSet.next()) {
            flag = resultSet.getBoolean(1);
        }
        return flag;
    }

    public int count(String from, String to) throws SQLException, ClassNotFoundException {
        int count = 0;
        String query = "SELECT COUNT(*) FROM purchase WHERE purchase_date = BETWEEN '" + from + "' AND '" + to + "'";
        Statement statement = connectionDB.getConnection().createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        while (resultSet.next()) {
            count = resultSet.getInt(1);
        }
        return count;
    }

    private Purchase setPurchase(Purchase purchase, ResultSet resultSet) throws SQLException {
        purchase.setInvoice(resultSet.getInt(1));
        purchase.setDate(resultSet.getDate(2));
        purchase.setGrandTotal(resultSet.getFloat(3));
        return purchase;
    }


    private PreparedStatement setQuery(PreparedStatement preparedStatement, Purchase purchase) throws SQLException {
        preparedStatement.setInt(1, purchase.getInvoice());
        preparedStatement.setDate(2, purchase.getDate());
        preparedStatement.setFloat(3, purchase.getGrandTotal());
        return preparedStatement;
    }
}
