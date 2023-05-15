package com.billing.app.domain.service.purchase;

import com.billing.app.domain.database.*;
import com.billing.app.domain.entity.Purchase;
import com.billing.app.domain.entity.PurchaseItem;
import com.billing.app.domain.exceptions.CodeNotFoundException;

import java.sql.SQLException;

public class PurchaseService implements PurchaseServiceInterface {
    ProductDAO productDAO;
    PurchaseDAO purchaseDAO;
    PurchaseItemDAO purchaseItemDAO = new PurchaseItemDAOImplementation();
    Purchase result;
    public Purchase create(Purchase purchase) throws SQLException, ClassNotFoundException, CodeNotFoundException {
        int flag = 0;
        productDAO = new ProductDAOImplementation();
        for (PurchaseItem purchaseItem : purchase.getListOfPurchaseItem()) {
            purchaseItemDAO.create();
        }
        if (flag != purchase.getListOfPurchaseItem().size()) {
            purchaseDAO = new PurchaseDAOImplementation();
            result = purchaseDAO.create(purchase);
            return result;
        } else {
            throw new CodeNotFoundException("Provided code for purchase not found in Product relation table.");
        }
    }
}
