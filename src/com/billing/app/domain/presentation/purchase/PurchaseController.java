package com.billing.app.domain.presentation.purchase;

import com.billing.app.domain.entity.Product;
import com.billing.app.domain.entity.Purchase;
import com.billing.app.domain.entity.PurchaseItem;
import com.billing.app.domain.exceptions.CodeNotFoundException;
import com.billing.app.domain.exceptions.IllegalArgumentException;
import com.billing.app.domain.exceptions.TemplateMismatchException;
import com.billing.app.domain.presentation.Validator;
import com.billing.app.domain.service.purchase.PurchaseService;
import com.billing.app.domain.service.purchase.PurchaseServiceInterface;
import com.billing.app.domain.service.purchase.PurchaseValidator;

import java.sql.Array;
import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;


public class PurchaseController {
    Purchase purchase;
    PurchaseItem purchaseItem;
    ArrayList<String> purchaseItemDetails;
    ArrayList<PurchaseItem> purchaseItems;
    PurchaseServiceInterface purchaseServiceInterface = new PurchaseService();
    PurchaseCLIValidator purchaseCLIValidator = new PurchaseCLIValidator();
    SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
    public Purchase create(ArrayList<String> stringArrayList) throws ParseException, SQLException, ClassNotFoundException, CodeNotFoundException, TemplateMismatchException {

        List<String> create = new ArrayList<>(stringArrayList.subList(1, 3));
        List<String> items = new ArrayList<>(stringArrayList.subList(3, stringArrayList.size()));
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
        return purchaseServiceInterface.create(purchase);
    }

    public boolean delete(List<String> values) throws TemplateMismatchException, SQLException, CodeNotFoundException, ClassNotFoundException {
        boolean flag = false;
        int expectedLength = 1;
        int actualLength = values.size();

        if (actualLength == expectedLength) {
            int invoice = Integer.parseInt(values.get(0));
            flag = purchaseServiceInterface.delete(invoice);
            return flag;
        } else {
            throw new TemplateMismatchException("Invalid argument length. Expected: " + expectedLength + ", Provided: " + actualLength);
        }
    }

    public List<Purchase> list(List<String> values) throws IllegalArgumentException, TemplateMismatchException, SQLException, ClassNotFoundException {

        int range, page;
        String attribute, searchText;
        Map<String, Object> parameters = purchaseCLIValidator.validateList(values);

        range = Integer.parseInt(parameters.get("range").toString());
        page = Integer.parseInt(parameters.get("page").toString());
        attribute = (String) parameters.get("attribute");
        searchText = (String) parameters.get("searchtext");

        return purchaseServiceInterface.list(range, page, attribute, searchText);

    }


}
