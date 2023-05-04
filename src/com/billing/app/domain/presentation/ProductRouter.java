package com.billing.app.domain.presentation;

import com.billing.app.domain.entity.Product;
import com.billing.app.domain.entity.Unit;
import com.billing.app.domain.exceptions.ProductException;
import com.billing.app.domain.exceptions.ProductNullConstraintException;
import com.billing.app.domain.exceptions.ProductPrimaryKeyException;
import com.billing.app.domain.exceptions.ProductUnitException;

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



            case "edit":
//                try {
                    if (productParser.edit(arrayList) != null)
                        System.out.println("Product edited successfully!");
//                } catch (ProductException exception) {
//                    System.out.println("Error editing record into database. \n" + exception.getMessage());
//                } catch (Throwable e) {
//                    System.out.println("Unexpected error occurred. ");
//                }
                break;


            case "delete":
                try {
                    System.out.println("Are you sure you want to delete the product? y/n");
                    String choice = scanner.next();
                    if (choice.equals("y")) {
                        if (productParser.delete(arrayList))
                            System.out.println("Product deleted successfully!");
                    } else if (choice.equals("n"))
                        System.out.println("Product not deleted.");
                    else
                        System.out.println("Invalid choice, please try again.");
                } catch (Throwable exception) {
                    System.out.println("No records deleted in database. \n" + exception.getMessage());
                }
                break;


            case "list":
                try {
                    ArrayList<Product> productArray = productParser.list(arrayList);
                    System.out.println("List returned successfully.");
                    formatter.format("%-15s %15s %15s %15s %15s %15s\n", "Code", "Name", "Unit Code", "Type", "Price", "Stock");
                    formatter.format("%-15s %15s %15s %15s %15s %15s\n", "----", "----", "---------", "----", "-----", "-----");
                    for (Product products : productArray) {
                        formatter.format("%-15s %15s %15s %15s %15.2f %15d\n", products.getCode(), products.getName(), products.getUnitCode(), products.getType(), products.getPrice(), products.getStock());
                    }
                    System.out.println(formatter.toString());
                }
                catch (Throwable exception) {
                    System.out.println("Cannot list the records! \n" + exception.getMessage());
                }
        }
    }
}
