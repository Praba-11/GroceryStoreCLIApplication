package com.billing.app.domain.database;

import com.billing.app.domain.entity.Purchase;
import com.billing.app.domain.entity.Sales;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SalesDAOImplementation {
    ConnectionDB connectionDB = new ConnectionDB();
    public Sales create(Sales sales) throws SQLException, ClassNotFoundException {
        String query = "INSERT INTO purchase (invoice, date, grand_total) VALUES (?, ?, ?)";
        PreparedStatement preparedStatement = connectionDB.getConnection().prepareStatement(query);
        preparedStatement.setInt(1, sales.getInvoice());
        preparedStatement.setDate(2, sales.getDate());
        preparedStatement.setFloat(3, sales.getGrandTotal());

        preparedStatement.execute();
        return sales;
    }
}
