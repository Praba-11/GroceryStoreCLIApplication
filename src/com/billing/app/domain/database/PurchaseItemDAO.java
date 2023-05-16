package com.billing.app.domain.database;

import com.billing.app.domain.entity.PurchaseItem;

import java.sql.SQLException;

public interface PurchaseItemDAO {
    void create(PurchaseItem purchaseItem) throws SQLException, ClassNotFoundException;
}
