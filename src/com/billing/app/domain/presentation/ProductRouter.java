package com.billing.app.domain.presentation;

import com.billing.app.domain.entity.Product;
import com.billing.app.domain.entity.Unit;
import com.billing.app.domain.exceptions.*;
import com.billing.app.domain.exceptions.unit.CodeNullException;
import com.billing.app.domain.exceptions.unit.TemplateMismatchException;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.Scanner;

public class ProductRouter {
    Formatter formatter = new Formatter();
    public void execute(ArrayList<String> arrayList) throws Throwable {
        String action = arrayList.get(1);
        Scanner scanner = new Scanner(System.in);
        ProductParser productParser = new ProductParser();

        switch (action) {
            case "create":
                try {
                    Product productCreated = null;
                    productCreated = productParser.create(arrayList);
                    System.out.println(productCreated);
                    break;
                } catch (SQLException exception) {
                    if (exception.getSQLState().equals("23505")) {
                        System.out.println("Unable to modify the primary key. " + exception.getMessage());
                    } else if (exception.getSQLState().equals("23502")) {
                        System.out.println("Provided constraint cannot be null in relational table. " + exception.getMessage());
                    } else if (exception.getSQLState().equals("23503")) {
                        System.out.println("Provided unit not present in Unit relation table. " + exception.getMessage());
                    }
                    System.out.println(exception.getMessage());
                }
                break;



            case "edit":
                try {
                    Product productEdited = productParser.edit(arrayList);
                    System.out.println(productEdited);
                } catch (SQLException exception) {
                    if (exception.getSQLState().equals("23502")) {
                        throw new ProductNullConstraintException("Provided constraint cannot be null. " + exception.getMessage());
                    } else if (exception.getSQLState().equals("23503")) {
                        throw new ProductUnitException("Provided unit not present in Unit relation table. " + exception.getMessage());
                    }
                    throw new ProductException("Incompatible edit attributes. " + exception.getMessage());
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
                break;


            case "delete":
                try {
                    boolean isDeleted = false;
                    isDeleted = productParser.delete(arrayList);
                    System.out.println(isDeleted);
                } catch (TemplateMismatchException exception) {
                    System.out.println("Template mismatch. " + exception.getMessage());
                } catch (SQLException exception) {
                    throw new RuntimeException(exception);
                } catch (CodeNotFoundException exception) {
                    System.out.println("Provided Code (or) Id not found. " + exception.getMessage());
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
                break;


            case "list":
                try {
                    ArrayList<Product> productArray = productParser.list(arrayList);
                    System.out.println(productArray);
                    System.out.println("List returned successfully.");
                    formatter.format("%-15s %15s %15s %15s %15s %15s\n", "Code", "Name", "Unit Code", "Type", "Price", "Stock");
                    formatter.format("%-15s %15s %15s %15s %15s %15s\n", "----", "----", "---------", "----", "-----", "-----");
                    for (Product products : productArray) {
                        formatter.format("%-15s %15s %15s %15s %15.2f %15.2f\n", products.getCode(), products.getName(), products.getUnitCode(), products.getType(), products.getPrice(), products.getStock());
                    }
                    System.out.println(formatter.toString());
                }
                catch (Throwable exception) {
                    System.out.println("Cannot list the records! \n" + exception.getMessage());
                }
                break;
        }
    }
}
