package com.billing.app.domain.presentation.product;

import com.billing.app.domain.entity.Product;
import com.billing.app.domain.exceptions.*;
import com.billing.app.domain.exceptions.IllegalArgumentException;
import com.billing.app.domain.exceptions.TemplateMismatchException;
import com.billing.app.domain.presentation.Main;
import com.billing.app.domain.presentation.Validator;

import java.sql.SQLException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ProductCLI {
    Validator validator;
    ArrayList<String> values;
    ProductHelp productHelp;
    Main main = new Main();
    List<String> splitBySpaces;

    public void execute(String command) throws TemplateMismatchException {
        splitBySpaces = main.splitBySpaces(command);
        String action = splitBySpaces.get(0);
        System.out.println(action);
        ProductController productController = new ProductController();
        switch (action) {
            case "create":
                try {
                    List<String> arraylist;
                    if (arraylist.size() == 1 && arraylist.get(0).equals("help")) {
                        productHelp = new ProductHelp();
                        productHelp.createProduct();
                    } else {
                        System.out.println(arraylist);
                        Product productCreated = null;
                        productCreated = productController.create(arraylist);
                        System.out.println("Product created successfully.");
                        System.out.println("Created product: " + productCreated);
                        break;
                    }
                } catch (SQLException exception) {
                    validator = new Validator();
                    System.out.print("Unable to create product. ");
                    String sqlMessage = validator.validateSQLState(exception);
                    System.out.println(sqlMessage);
                } catch (TemplateMismatchException exception) {
                    System.out.println("Template mismatch. " + exception.getMessage());
                } catch (ClassNotFoundException exception) {
                    throw new RuntimeException(exception);
                } catch (IllegalArgumentException exception) {
                    System.out.println("Invalid argument provided. " + exception.getMessage());
                } catch (TypeMismatchException exception) {
                    System.out.println("Type mismatch occurred at " + exception.getMessage());
                } catch (ObjectNullPointerException exception) {
                    System.out.println("Unable to create product." + exception.getMessage());
                }
                break;



            case "edit":
                try {
                    if (arraylist.size() == 3 && arraylist.get(2).equals("help")) {
                        productHelp = new ProductHelp();
                        productHelp.editProduct();
                    } else {
                        Product productEdited = productController.edit(arraylist);
                        System.out.println("Product edited successfully.");
                        System.out.println(productEdited);
                    }
                } catch (SQLException exception) {
                    validator = new Validator();
                    System.out.print("Unable to edit product. ");
                    String sqlMessage = validator.validateSQLState(exception);
                    System.out.println(sqlMessage);
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                } catch (TemplateMismatchException exception) {
                    System.out.println("Template mismatch. " + exception.getMessage());
                } catch (TypeMismatchException exception) {
                    System.out.println("Type mismatch occurred at " + exception.getMessage());
                } catch (IllegalArgumentException exception) {
                    System.out.println("Incompatible argument. " + exception.getMessage());
                } catch (ObjectNullPointerException exception) {
                    System.out.println("Unable to edit product. " + exception.getMessage());
                } catch (CodeNotFoundException exception) {
                    System.out.println("Invalid product id. " + exception.getMessage());
                }
                break;



            case "delete":
                try {
                    if (arraylist.size() == 1 && arraylist.get(0).equals("help")) {
                        productHelp = new ProductHelp();
                        productHelp.deleteProduct();
                    } else {
                        boolean isDeleted = false;
                        isDeleted = productController.delete(arraylist);
                        System.out.println(isDeleted);
                    }
                } catch (TemplateMismatchException exception) {
                    System.out.println("Template mismatch. " + exception.getMessage());
                } catch (SQLException exception) {
                    validator = new Validator();
                    System.out.print("Unable to delete product. ");
                    String sqlMessage = validator.validateSQLState(exception);
                    System.out.println(sqlMessage);
                } catch (CodeNotFoundException exception) {
                    System.out.println("Provided Code (or) Id not found. " + exception.getMessage());
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
                break;



            case "list":
                List<String> arrayList = list(command);
                System.out.println(arrayList);
//                if (command.size() == 3 && command.get(2).equals("help")) {
//                    productHelp = new ProductHelp();
//                    productHelp.listProduct();
//                } else {
//                    values = new ArrayList<>(command.subList(2, command.size()));
//                    try {
//                        List<Product> productArray = productController.list(values);
//                        System.out.println("List returned successfully.");
//                        for (Product products : productArray) {
//                            System.out.println("id: " + products.getId() + ", code: " + products.getCode() + ", name: " + products.getName() + ", unitcode: " + products.getUnitCode() + ", type: " + products.getType() + ", price: " + products.getPrice() + ", stock: " + products.getStock());
//                        }
//                    } catch (IllegalArgumentException exception) {
//                        System.out.println("Incompatible argument. " + exception.getMessage());
//                    } catch (TemplateMismatchException exception) {
//                        System.out.println("Template mismatch. " + exception.getMessage());
//                    } catch (SQLException e) {
//                        throw new RuntimeException(e);
//                    } catch (ClassNotFoundException e) {
//                        throw new RuntimeException(e);
//                    }
//                }
//                break;
        }
    }
}
