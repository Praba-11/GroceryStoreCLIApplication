package com.billing.app.domain.database;

import com.billing.app.domain.entity.PurchaseItem;
import com.billing.app.domain.entity.SalesItem;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SalesItemDAOImplementation implements SalesItemDAO {
    ConnectionDB connectionDB = new ConnectionDB();
    public void create(SalesItem salesItem) throws SQLException {
        String query = "INSERT INTO sales_item (invoice_id, product_code, quantity, price) VALUES (?, ?, ?, ?)";
        PreparedStatement preparedStatement = connectionDB.getConnection().prepareStatement(query);
        PreparedStatement statement = setQuery(preparedStatement,salesItem);
        statement.executeUpdate();
    }

    private PreparedStatement setQuery(PreparedStatement preparedStatement, SalesItem salesItem) throws SQLException {
        preparedStatement.setInt(1, salesItem.getInvoice());
        preparedStatement.setString(2, salesItem.getCode());
        preparedStatement.setFloat(3, salesItem.getQuantity());
        preparedStatement.setFloat(4, salesItem.getCostPrice());
        return preparedStatement;
    }
}
