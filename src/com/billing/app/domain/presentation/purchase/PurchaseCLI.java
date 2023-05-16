package com.billing.app.domain.presentation.purchase;

import com.billing.app.domain.entity.Product;
import com.billing.app.domain.entity.Purchase;
import com.billing.app.domain.exceptions.CodeNotFoundException;
import com.billing.app.domain.exceptions.IllegalArgumentException;
import com.billing.app.domain.exceptions.TemplateMismatchException;
import com.billing.app.domain.presentation.product.ProductHelp;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PurchaseCLI {
    PurchaseController purchaseController = new PurchaseController();
    ArrayList<String> values;

    public void execute(ArrayList<String> stringArrayList) throws ParseException {
        String action = stringArrayList.get(1);
        switch (action) {
            case "products":
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
                break;

            case "delete":
                try {
                    if (stringArrayList.size() == 3) {
                        List<String> values = new ArrayList<>(stringArrayList.subList(2, stringArrayList.size()));
                        System.out.println(values);
                        purchaseController.delete(values);
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                } catch (CodeNotFoundException e) {
                    throw new RuntimeException(e);
                } catch (TemplateMismatchException e) {
                    throw new RuntimeException(e);
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
                break;

            case "list":
                List<String> command = stringArrayList.subList(0, stringArrayList.size()-1);
                String[] lastSplit = stringArrayList.get(stringArrayList.size()-1).split("\\s+");
                command.addAll(Arrays.asList(lastSplit));
                if (command.size() == 3 && command.get(2).equals("help")) {
//                    productHelp = new ProductHelp();
//                    productHelp.listProduct();
                    System.out.println("purchase help");
                } else {
                    values = new ArrayList<>(command.subList(2, command.size()));
                    try {
                        List<Purchase> purchaseArray = purchaseController.list(values);
                        System.out.println("List returned successfully.");
                        for (Purchase purchases : purchaseArray) {
                            System.out.println(purchases);
                        }
                    } catch (IllegalArgumentException exception) {
                        System.out.println("Incompatible argument. " + exception.getMessage());
                    } catch (TemplateMismatchException exception) {
                        System.out.println("Template mismatch. " + exception.getMessage());
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    } catch (ClassNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                }
                break;

            case "count":
                try {
                    List<String> values = new ArrayList<>(stringArrayList.subList(2, stringArrayList.size()));
                    purchaseController.count(values);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                } catch (TemplateMismatchException e) {
                    throw new RuntimeException(e);
                }
        }

    }
}
