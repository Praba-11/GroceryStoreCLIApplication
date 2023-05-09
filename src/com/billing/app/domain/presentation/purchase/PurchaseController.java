package com.billing.app.domain.presentation.purchase;

import com.billing.app.domain.entity.Product;
import com.billing.app.domain.entity.Purchase;
import com.billing.app.domain.entity.PurchaseItem;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class PurchaseController {
    Purchase purchase;
    PurchaseItem purchaseItem;
    ArrayList<String> purchaseItemDetails;
    ArrayList<PurchaseItem> purchaseItems;
    SimpleDateFormat format = new SimpleDateFormat("yyyy-dd-MM");
    public void create(ArrayList<String> stringArrayList) throws ParseException {
        purchase = new Purchase();
        purchaseItems = new ArrayList<>();
        purchaseItemDetails = new ArrayList<>(stringArrayList.subList(3, stringArrayList.size()));

        if (purchaseItemDetails.size() % 3 == 0) {
            for (int index = 0; index < purchaseItemDetails.size(); index+=3) {
                purchaseItem = new PurchaseItem();
                purchaseItem.setCode(purchaseItemDetails.get(index));
                purchaseItem.setQuantity(Float.parseFloat(purchaseItemDetails.get(index+1)));
                purchaseItem.setCostPrice(Float.parseFloat(purchaseItemDetails.get(index+2)));
                purchaseItems.add(purchaseItem);

            }
        } else {
            System.out.println("Invalid.");
        }
        purchase.setDate(format.parse(stringArrayList.get(1)));
        purchase.setInvoice(Integer.parseInt(stringArrayList.get(2)));
        purchase.setListOfPurchaseItem(purchaseItems);

        System.out.println(purchase);

    }
}
