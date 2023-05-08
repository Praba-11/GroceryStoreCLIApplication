package com.billing.app.domain.presentation.product;

import com.billing.app.domain.entity.Product;
import com.billing.app.domain.exceptions.*;
import com.billing.app.domain.exceptions.TemplateMismatchException;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.Scanner;

public class ProductCLI {
    Formatter formatter = new Formatter();
    public void execute(ArrayList<String> arrayList) throws Throwable {
        String action = arrayList.get(1);
        Scanner scanner = new Scanner(System.in);
        ProductController productController = new ProductController();

        switch (action) {
            case "create":
                try {
                    Product productCreated = null;
                    productCreated = productController.create(arrayList);
                    System.out.println(productCreated);
                    break;
                } catch (SQLException exception) {
                    if (exception.getSQLState().equals("23505")) {
                        System.out.println("Unable to create product. " + exception.getMessage());
                    }
                    System.out.println(exception.getMessage());
                }
                break;



            case "edit":
                try {
                    Product productEdited = productController.edit(arrayList);
                    System.out.println(productEdited);
                } catch (SQLException exception) {
                    throw new ProductException(exception.getMessage());
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
                break;


            case "delete":
                try {
                    boolean isDeleted = false;
                    isDeleted = productController.delete(arrayList);
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
                    ArrayList<Product> productArray = productController.list(arrayList);
                    System.out.println("List returned successfully.");
                    for (Product products : productArray) {
                        System.out.println("id: " + products.getId() + ", code: " + products.getCode() + ", name: " + products.getName() + ", unitcode: " + products.getUnitCode() + ", type: " + products.getType() + ", price: " + products.getPrice() + ", stock: " + products.getStock());
                    }
                }
                catch (Throwable exception) {
                    System.out.println("Cannot list the records! \n" + exception.getMessage());
                }
                break;
        }
    }
}
