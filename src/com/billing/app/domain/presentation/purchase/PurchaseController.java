package com.billing.app.domain.presentation.purchase;

import com.billing.app.domain.entity.Product;
import com.billing.app.domain.entity.Purchase;
import com.billing.app.domain.entity.PurchaseItem;
import com.billing.app.domain.exceptions.CodeNotFoundException;
import com.billing.app.domain.exceptions.IllegalArgumentException;
import com.billing.app.domain.exceptions.TemplateMismatchException;
import com.billing.app.domain.service.purchase.PurchaseService;
import com.billing.app.domain.service.purchase.PurchaseServiceInterface;

import java.sql.Array;
import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class PurchaseController {
    Purchase purchase;
    PurchaseItem purchaseItem;
    ArrayList<String> purchaseItemDetails;
    ArrayList<PurchaseItem> purchaseItems;
    PurchaseServiceInterface purchaseServiceInterface;
    SimpleDateFormat format = new SimpleDateFormat("yyyy-dd-MM");
    public Purchase create(ArrayList<String> stringArrayList) throws ParseException, SQLException, ClassNotFoundException, CodeNotFoundException, TemplateMismatchException {

        List<String> create = new ArrayList<>(stringArrayList.subList(0, 4));
        List<String> items = new ArrayList<>(stringArrayList.subList(4, stringArrayList.size()));
        for (String item : items) {
            item = item.replaceAll("\\[|\\]", "");
            create.add(item);
        }


        purchase = new Purchase();
        purchaseItems = new ArrayList<>();
        float grandTotal = 0;
        purchaseItemDetails = new ArrayList<>(create.subList(3, create.size()));

        if (purchaseItemDetails.size() % 3 == 0) {
            for (int index = 0; index < purchaseItemDetails.size(); index += 3) {
                purchaseItem = new PurchaseItem();
                purchaseItem.setCode(purchaseItemDetails.get(index));
                purchaseItem.setQuantity(Float.parseFloat(purchaseItemDetails.get(index + 1)));
                purchaseItem.setCostPrice(Float.parseFloat(purchaseItemDetails.get(index + 2)));
                grandTotal += (purchaseItem.getQuantity() * purchaseItem.getCostPrice());
                purchaseItems.add(purchaseItem);

            }
        } else {
            throw new TemplateMismatchException("Incompatible purchase item details. Please provide according to the template.");
        }
        purchase.setDate(new Date(format.parse(create.get(1)).getTime()));
        purchase.setInvoice(Integer.parseInt(create.get(2)));
        purchase.setListOfPurchaseItem(purchaseItems);
        purchase.setGrandTotal(grandTotal);
        purchaseServiceInterface = new PurchaseService();
        return purchaseServiceInterface.create(purchase);
    }
}
