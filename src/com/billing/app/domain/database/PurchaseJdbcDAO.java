package com.billing.app.domain.database;

import com.billing.app.domain.entity.Purchase;

import java.sql.*;

public class PurchaseJdbcDAO implements PurchaseDAO {
    ConnectionDB connectionDB = new ConnectionDB();
    public Purchase create(Purchase purchase) throws SQLException, ClassNotFoundException {
        String query = "INSERT INTO purchase (invoice_id, purchase_date, grand_total) VALUES (?, ?, ?)";
        PreparedStatement preparedStatement = connectionDB.getConnection().prepareStatement(query);
        preparedStatement.setInt(1, purchase.getInvoice());
        preparedStatement.setDate(2, purchase.getDate());
        preparedStatement.setFloat(3, purchase.getGrandTotal());

        preparedStatement.execute();
        return purchase;
    }
}
