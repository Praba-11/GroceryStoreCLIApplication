package com.billing.app.domain.presentation.purchase;

import com.billing.app.domain.exceptions.CodeNotFoundException;
import com.billing.app.domain.exceptions.TemplateMismatchException;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;

public class PurchaseCLI {
    PurchaseController purchaseController = new PurchaseController();
    public void execute(ArrayList<String> stringArrayList) throws ParseException {

        try {
            purchaseController.create(stringArrayList);
        } catch (SQLException exception) {
            System.out.println("Cannot purchase products." + exception.getMessage());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (CodeNotFoundException exception) {
            System.out.println("Invalid product code. " + exception.getMessage());
        } catch (TemplateMismatchException exception) {
            System.out.println("Template mismatch. " + exception.getMessage());
        }
    }
}
