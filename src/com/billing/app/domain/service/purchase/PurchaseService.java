package com.billing.app.domain.service.purchase;

import com.billing.app.domain.database.ProductDAO;
import com.billing.app.domain.database.ProductJdbcDAO;
import com.billing.app.domain.database.PurchaseDAO;
import com.billing.app.domain.database.PurchaseJdbcDAO;
import com.billing.app.domain.entity.Purchase;
import com.billing.app.domain.entity.PurchaseItem;
import com.billing.app.domain.exceptions.CodeNotFoundException;
import com.billing.app.domain.exceptions.ProductException;

import java.sql.SQLException;

public class PurchaseService implements PurchaseServiceInterface {
    ProductDAO productDAO;
    PurchaseDAO purchaseDAO;
    Purchase result;
    public Purchase create(Purchase purchase) throws SQLException, ClassNotFoundException, CodeNotFoundException {
        int flag = 0;
        productDAO = new ProductJdbcDAO();
        for (PurchaseItem purchaseItem : purchase.getListOfPurchaseItem()) {
            if (productDAO.isCodePresent(purchaseItem.getCode())) {
                flag++;
            }
        }
        if (flag == purchase.getListOfPurchaseItem().size()) {
            purchaseDAO = new PurchaseJdbcDAO();
            result = purchaseDAO.create(purchase);
            return result;
        } else {
            throw new CodeNotFoundException("Provided code for purchase not found in Product relation table.");
        }
    }
}