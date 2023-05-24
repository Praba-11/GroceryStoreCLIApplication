package com.billing.app.domain.service.purchase;

import com.billing.app.domain.database.ProductDAO;
import com.billing.app.domain.database.ProductDAOImplementation;
import com.billing.app.domain.database.PurchaseDAO;
import com.billing.app.domain.database.PurchaseDAOImplementation;
import com.billing.app.domain.entity.Purchase;
import com.billing.app.domain.entity.PurchaseItem;
import com.billing.app.domain.exceptions.NotFoundException;

import java.sql.SQLException;

public class PurchaseValidator {
    PurchaseDAO purchaseDAO = new PurchaseDAOImplementation();
    ProductDAO productDAO = new ProductDAOImplementation();
    public boolean valid(Purchase purchase) throws SQLException, NotFoundException {
        int flag = 0;
        for (PurchaseItem purchaseItem : purchase.getListOfPurchaseItem()) {
            if (productDAO.findByCode(purchaseItem.getCode()) != null) {
               flag++;
            } else {
                throw new NotFoundException("Provided code for purchase not found in Product relation table.");
            }
        }
        return flag == purchase.getListOfPurchaseItem().size();
    }
}
