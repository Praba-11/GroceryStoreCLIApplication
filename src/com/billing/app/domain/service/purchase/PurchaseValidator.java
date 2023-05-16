package com.billing.app.domain.service.purchase;

import com.billing.app.domain.database.PurchaseDAO;
import com.billing.app.domain.database.PurchaseDAOImplementation;
import com.billing.app.domain.entity.Purchase;
import com.billing.app.domain.entity.PurchaseItem;
import com.billing.app.domain.exceptions.CodeNotFoundException;

import java.sql.SQLException;

public class PurchaseValidator {
    PurchaseDAO purchaseDAO = new PurchaseDAOImplementation();
    public boolean valid(Purchase purchase) throws SQLException, ClassNotFoundException, CodeNotFoundException {
        int flag = 0;
        for (PurchaseItem purchaseItem : purchase.getListOfPurchaseItem()) {
            if (purchaseDAO.find(purchaseItem.getCode())) {
               flag++;
            } else {
                throw new CodeNotFoundException("Provided code for purchase not found in Product relation table.");
            }
        }
        return flag == purchase.getListOfPurchaseItem().size();
    }
}
