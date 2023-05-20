package com.billing.app.domain.presentation.purchase;

import com.billing.app.domain.entity.Purchase;
import com.billing.app.domain.entity.PurchaseItem;
import com.billing.app.domain.exceptions.CodeNotFoundException;
import com.billing.app.domain.exceptions.InvalidArgumentException;
import com.billing.app.domain.exceptions.TemplateMismatchException;
import com.billing.app.domain.service.purchase.PurchaseService;
import com.billing.app.domain.service.purchase.PurchaseServiceInterface;

import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
    public Purchase create(List<String> stringArrayList) throws ParseException, SQLException, ClassNotFoundException, CodeNotFoundException, TemplateMismatchException {

        List<String> create = new ArrayList<>(stringArrayList.subList(0, 2));
        List<String> purchaseItemDetails = new ArrayList<>(stringArrayList.subList(2, stringArrayList.size()));


        purchase = new Purchase();
        purchaseItems = new ArrayList<>();
        float grandTotal = 0;

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
        purchase.setInvoice(Integer.parseInt(create.get(0)));
        purchase.setListOfPurchaseItem(purchaseItems);
        purchase.setGrandTotal(grandTotal);
        System.out.println(purchase);
        return purchaseServiceInterface.create(purchase);
    }



    public boolean delete(String value) throws TemplateMismatchException, SQLException, CodeNotFoundException, ClassNotFoundException, InvalidArgumentException {
        boolean flag = false;
        int invoice;
        try {
            invoice = Integer.parseInt(value);
        } catch (NumberFormatException exception) {
            throw new InvalidArgumentException("Incompatible invoice number. Invoice cannot be string.");
        }
        flag = purchaseServiceInterface.delete(invoice);
        return flag;
    }

    public List<Purchase> list(List<String> values) throws InvalidArgumentException, TemplateMismatchException, SQLException, ClassNotFoundException {

        int range, page;
        String attribute, searchText;
        Map<String, Object> parameters = purchaseCLIValidator.validateList(values);

        range = Integer.parseInt(parameters.get("range").toString());
        page = Integer.parseInt(parameters.get("page").toString());
        attribute = (String) parameters.get("attribute");
        searchText = (String) parameters.get("searchtext");

        return purchaseServiceInterface.list(range, page, attribute, searchText);

    }

    public int count(List<String> values) throws SQLException, ClassNotFoundException, TemplateMismatchException {
        int actualLength;
        actualLength = values.size();
        if (actualLength == 0) {
            return purchaseServiceInterface.count("1970-01-01", "40000-01-01");
        }
        else if (actualLength == 1) {
            String date = values.get(0);
            return purchaseServiceInterface.count(date, date);
        } else if (actualLength == 2) {
            String from, to;
            from = values.get(0);
            to = values.get(1);
            return purchaseServiceInterface.count(from, to);
        } else {
            throw new TemplateMismatchException("Invalid argument length. Provided: " + actualLength);
        }

    }
}
