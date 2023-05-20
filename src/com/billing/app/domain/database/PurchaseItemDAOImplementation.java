package com.billing.app.domain.database;

import com.billing.app.domain.entity.Purchase;
import com.billing.app.domain.entity.PurchaseItem;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PurchaseItemDAOImplementation implements PurchaseItemDAO {
    ConnectionDB connectionDB = new ConnectionDB();
    public void create(PurchaseItem purchaseItem) throws SQLException, ClassNotFoundException {
        String query = "INSERT INTO purchase_item (invoice_id, product_code, quantity, price) VALUES (?, ?, ?, ?)";
        PreparedStatement preparedStatement = connectionDB.getConnection().prepareStatement(query);
        PreparedStatement statement = setQuery(preparedStatement, purchaseItem);
        statement.executeUpdate();
    }

    private PreparedStatement setQuery(PreparedStatement preparedStatement, PurchaseItem purchaseItem) throws SQLException {
        preparedStatement.setInt(1, purchaseItem.getInvoice());
        preparedStatement.setString(2, purchaseItem.getCode());
        preparedStatement.setFloat(3, purchaseItem.getQuantity());
        preparedStatement.setFloat(4, purchaseItem.getCostPrice());
        return preparedStatement;
    }


}
