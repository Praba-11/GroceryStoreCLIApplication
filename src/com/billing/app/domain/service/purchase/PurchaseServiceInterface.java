package com.billing.app.domain.service.purchase;

import com.billing.app.domain.entity.Purchase;
import com.billing.app.domain.exceptions.CodeNotFoundException;

import java.sql.SQLException;

public interface PurchaseServiceInterface {
    Purchase create(Purchase purchase) throws SQLException, ClassNotFoundException, CodeNotFoundException;
}
