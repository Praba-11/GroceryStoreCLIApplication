package com.billing.app.domain.presentation.sale;

import com.billing.app.domain.entity.Purchase;
import com.billing.app.domain.entity.Sales;
import com.billing.app.domain.exceptions.CodeNotFoundException;
import com.billing.app.domain.exceptions.IllegalArgumentException;
import com.billing.app.domain.exceptions.TemplateMismatchException;
import com.billing.app.domain.presentation.purchase.PurchaseController;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SalesCLI {
    SalesController salesController = new SalesController();
    ArrayList<String> values;
    public void execute(ArrayList<String> stringArrayList) throws ParseException {
        String action = stringArrayList.get(1);
        switch (action) {
            case "products":
                try {
                    salesController.create(stringArrayList);
                } catch (SQLException exception) {
                    throw new RuntimeException(exception);
//                    System.out.println("Cannot sell products." + exception.getMessage());
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
                        salesController.delete(values);
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
                    System.out.println("sales help");
                } else {
                    values = new ArrayList<>(command.subList(2, command.size()));
                    try {
                        List<Sales> salesArray = salesController.list(values);
                        System.out.println("List returned successfully.");
                        for (Sales sales : salesArray) {
                            System.out.println(sales);
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
                    salesController.count(values);
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
