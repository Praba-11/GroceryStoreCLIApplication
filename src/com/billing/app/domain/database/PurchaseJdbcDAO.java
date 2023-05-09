package com.billing.app.domain.database;

import com.billing.app.domain.entity.Purchase;

import java.sql.*;

public class PurchaseJdbcDAO {
    ConnectionDB connectionDB = new ConnectionDB();
    public void create(Purchase purchase) throws SQLException, ClassNotFoundException {
        String query = "INSERT INTO purchase (invoice_id, purchase_date, grand_total) VALUES (?, ?, ?)";
        PreparedStatement preparedStatement = connectionDB.getConnection().prepareStatement(query);
        preparedStatement.setInt(1, purchase.getInvoice());
        preparedStatement.setDate(2, (Date) purchase.getDate());
        ResultSet resultSet = preparedStatement.executeQuery();
    }
}
