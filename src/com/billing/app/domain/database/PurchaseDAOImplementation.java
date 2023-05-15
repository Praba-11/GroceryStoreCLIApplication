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
        String query = "INSERT INTO purchase (invoice, date, grand_total) VALUES (?, ?, ?)";
        PreparedStatement preparedStatement = connectionDB.getConnection().prepareStatement(query);
        preparedStatement.setInt(1, purchase.getInvoice());
        preparedStatement.setDate(2, purchase.getDate());
        preparedStatement.setFloat(3, purchase.getGrandTotal());

        preparedStatement.execute();
        return purchase;
    }

    public boolean delete(int invoice) throws SQLException, ClassNotFoundException {
        String query = "DELETE * FROM purchase WHERE invoice = " + invoice;
        Statement statement = connectionDB.getConnection().createStatement();
        int rowsAffected = statement.executeUpdate(query);
        return rowsAffected > 0;
    }

    public List<Purchase> list(int range, int page, String attribute, String searchText) throws SQLException, ClassNotFoundException {
        String query = "SELECT * FROM purchase WHERE CAST(" + attribute + " AS TEXT) ILIKE '%" + searchText + "%' LIMIT " + range + " OFFSET " + page;
        Statement statement = connectionDB.getConnection().createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        List<Purchase> purchases = listPurchases(resultSet);
        return purchases;
    }

    public List<Purchase> list(String searchText) throws SQLException, ClassNotFoundException {
        String query = "SELECT * FROM purchase WHERE CAST(id AS TEXT) ILIKE '%" + searchText + "%' OR code ILIKE '%" + searchText + "%' OR name ILIKE '%" + searchText + "%' OR unitcode ILIKE '%" + searchText + "%' OR type ILIKE '%" + searchText + "%' OR CAST(price AS TEXT) ILIKE '%" + searchText + "%' OR CAST(stock AS TEXT) ILIKE '%" + searchText + "%'";
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

    private Purchase setPurchase(Purchase purchase, ResultSet resultSet) throws SQLException {
        purchase.setId(resultSet.getInt(1));
        purchase.setInvoice(resultSet.getInt(2));
        purchase.setDate(resultSet.getDate(3));
        purchase.setGrandTotal(resultSet.getInt(4));
        return purchase;
    }
}
