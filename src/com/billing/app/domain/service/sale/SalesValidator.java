package com.billing.app.domain.service.sale;

import com.billing.app.domain.database.PurchaseDAO;
import com.billing.app.domain.database.PurchaseDAOImplementation;
import com.billing.app.domain.database.SalesDAO;
import com.billing.app.domain.database.SalesDAOImplementation;
import com.billing.app.domain.entity.Purchase;
import com.billing.app.domain.entity.PurchaseItem;
import com.billing.app.domain.entity.Sales;
import com.billing.app.domain.entity.SalesItem;
import com.billing.app.domain.exceptions.CodeNotFoundException;

import java.sql.SQLException;

public class SalesValidator {
    SalesDAO salesDAO = new SalesDAOImplementation();
    public boolean valid(Sales sales) throws SQLException, ClassNotFoundException, CodeNotFoundException {
        int flag = 0;
        for (SalesItem salesItem : sales.getListOfSalesItem()) {
            if (salesDAO.find(salesItem.getCode())) {
                flag++;
            } else {
                throw new CodeNotFoundException("Provided code for purchase not found in Product relation table.");
            }
        }
        return flag == sales.getListOfSalesItem().size();
    }
}
