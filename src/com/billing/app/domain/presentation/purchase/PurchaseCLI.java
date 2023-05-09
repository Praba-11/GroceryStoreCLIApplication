package com.billing.app.domain.presentation.purchase;

import java.text.ParseException;
import java.util.ArrayList;

public class PurchaseCLI {
    PurchaseController purchaseController;
    public void execute(ArrayList<String> stringArrayList) throws ParseException {
        purchaseController = new PurchaseController();
        purchaseController.create(stringArrayList);

    }
}
