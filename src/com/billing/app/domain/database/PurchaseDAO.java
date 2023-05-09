package com.billing.app.domain.database;

import com.billing.app.domain.entity.Purchase;

import java.sql.SQLException;

public interface PurchaseDAO {
    Purchase create(Purchase purchase) throws SQLException, ClassNotFoundException;
}
