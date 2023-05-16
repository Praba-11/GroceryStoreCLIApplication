package com.billing.app.domain.database;

import com.billing.app.domain.entity.PurchaseItem;
import com.billing.app.domain.entity.SalesItem;

import java.sql.SQLException;

public interface SalesItemDAO {
    void create(SalesItem salesItem) throws SQLException, ClassNotFoundException;
}
