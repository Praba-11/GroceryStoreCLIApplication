package com.billing.app.domain.service.purchase;

import com.billing.app.domain.database.*;
import com.billing.app.domain.entity.Product;
import com.billing.app.domain.entity.Purchase;
import com.billing.app.domain.entity.PurchaseItem;
import com.billing.app.domain.exceptions.CodeOrIDNotFoundException;
import com.billing.app.domain.exceptions.InvalidArgumentException;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PurchaseServiceImplementation implements PurchaseService {
    PurchaseDAO purchaseDAO = new PurchaseDAOImplementation();
    PurchaseItemDAO purchaseItemDAO = new PurchaseItemDAOImplementation();
    PurchaseValidator purchaseValidator = new PurchaseValidator();
    ProductDAO productDAO = new ProductDAOImplementation();
    List<PurchaseItem> listOfPurchaseItems;
    public Purchase create(Purchase purchase) throws SQLException, CodeOrIDNotFoundException {
        if (purchaseValidator.valid(purchase)) {

            purchaseDAO.create(purchase);
            for (PurchaseItem purchaseItem : purchase.getListOfPurchaseItem()) {
                listOfPurchaseItems = new ArrayList<>();
                purchaseItem.setInvoice(purchase.getInvoice());
                purchaseItem.setName(productDAO.findByCode(purchaseItem.getCode()).getName());

                Product product = productDAO.findByCode(purchaseItem.getCode());
                product.setStock(productDAO.findByCode(purchaseItem.getCode()).getStock() + purchaseItem.getQuantity());
                productDAO.edit(product);

                purchaseItemDAO.create(purchaseItem);
                listOfPurchaseItems.add(purchaseItem);
            }
        }
        purchase.setListOfPurchaseItem(listOfPurchaseItems);
        return purchase;
    }

    public boolean delete(int invoice) throws CodeOrIDNotFoundException, SQLException {
        boolean isDeleted;
        isDeleted = purchaseDAO.delete(invoice);
        if (!isDeleted) {
            throw new CodeOrIDNotFoundException("(Invoice no: " + invoice + ") not present in purchase relational table.");
        }
        return true;
    }

    public List<Purchase> list(int range, int page, String attribute, String searchText) throws SQLException, InvalidArgumentException {
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
                throw new InvalidArgumentException("Invalid argument provided. Please provide valid arguments as per template.");
            }
            System.out.println(range);
            System.out.println(page);
            System.out.println(attribute);
            System.out.println(searchText);
            list = purchaseDAO.list(range, page, attribute, searchText);
        }
        return list;
    }

    public int count(String from, String to) throws SQLException {
        int count = purchaseDAO.count(from, to);
        return count;
    }
}
