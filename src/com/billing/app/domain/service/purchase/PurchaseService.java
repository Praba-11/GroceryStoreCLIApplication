package com.billing.app.domain.service.purchase;

import com.billing.app.domain.database.*;
import com.billing.app.domain.entity.Product;
import com.billing.app.domain.entity.Purchase;
import com.billing.app.domain.entity.PurchaseItem;
import com.billing.app.domain.exceptions.CodeNotFoundException;
import com.billing.app.domain.exceptions.IllegalArgumentException;

import java.sql.SQLException;
import java.util.List;

public class PurchaseService implements PurchaseServiceInterface {
    PurchaseDAO purchaseDAO = new PurchaseDAOImplementation();
    PurchaseItemDAO purchaseItemDAO = new PurchaseItemDAOImplementation();
    PurchaseValidator purchaseValidator = new PurchaseValidator();
    Purchase result;
    public Purchase create(Purchase purchase) throws SQLException, ClassNotFoundException, CodeNotFoundException {
        if (purchaseValidator.valid(purchase)) {
            purchaseDAO.create(purchase);
            for (PurchaseItem purchaseItem : purchase.getListOfPurchaseItem()) {
                purchaseItem.setInvoice(purchase.getInvoice());
                purchaseItemDAO.create(purchaseItem);
            }
        }
        return purchase;
    }

    public boolean delete(int invoice) throws CodeNotFoundException, SQLException, ClassNotFoundException {
        boolean isDeleted;
        isDeleted = purchaseDAO.delete(invoice);
        if (!isDeleted) {
            throw new CodeNotFoundException("(Invoice no: " + invoice + ") not present in purchase relational table.");
        }
        return true;
    }

    public List<Purchase> list(int range, int page, String attribute, String searchText) throws SQLException, ClassNotFoundException, IllegalArgumentException {
        List<Purchase> list;
        if (attribute == null && searchText != null && range == 0 && page == 0) {
            list = purchaseDAO.list(searchText);
        } else {
            if (attribute == null && searchText == null && range == 0 && page == 0) {
                attribute = "invoice_id";
                searchText = "";
                range = purchaseDAO.count("01-01-1970", "01-01-40000");
            } else if (attribute == null && searchText == null && range > 0 && page == 0) {
                attribute = "invoice_id";
                searchText = "";
            } else if (attribute == null && searchText == null && range > 0 && page > 0) {
                attribute = "invoice_id";
                searchText = "";
                page = (page - 1) * range;
            } else if (attribute != null && searchText != null && range == 0 && page == 0) {
                range = purchaseDAO.count("01-01-1970", "01-01-40000");
            } else if (attribute != null && searchText != null && range > 0 && page > 0) {
                page = (page - 1) * range;
            } else {
                throw new IllegalArgumentException("Invalid argument provided. Please provide valid arguments as per template.");
            }
            list = purchaseDAO.list(range, page, attribute, searchText);
        }
        return list;
    }

    public int count(String from, String to) throws SQLException, ClassNotFoundException {
        int count = purchaseDAO.count(from, to);
        return count;
    }
}
