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

    public void execute(String productCommand) {
        splitBySpaces = main.splitBySpaces(productCommand);
        String action = splitBySpaces.get(0);
        System.out.println(action);



        ProductController productController = new ProductController();
        switch (action) {
            case "create":
                try {
                    String create = productCommand.substring(productCommand.indexOf(splitBySpaces.get(1)));
                    System.out.println(create);

                    String regex = "\\s*,\\s*";

                    String[] created = create.trim().split(regex);
                    List<String> createCommand = Arrays.asList(created);
                    System.out.println(createCommand);
                    if (createCommand.size() == 1 && createCommand.get(0).equals("help")) {
                        productHelp = new ProductHelp();
                        productHelp.createProduct();
                    } else {
                        System.out.println(createCommand);
                        Product productCreated = null;
                        productCreated = productController.create(createCommand);
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
                    String edit = productCommand.substring(productCommand.indexOf(splitBySpaces.get(1)));
                    System.out.println(edit);
                    String formattedInput = edit.replaceAll("\\s*:\\s*", ":");
                    String[] keyValuePairs = formattedInput.split("\\s*,\\s*");
                    ArrayList<String> pairs = new ArrayList<>(Arrays.asList(keyValuePairs));
                    LinkedHashMap<String, String> editCommand = new LinkedHashMap<>();
                    for (String pair : pairs) {
                        String[] keyValue = pair.split(":");
                        String key = keyValue[0].trim();
                        String value = keyValue[1].trim();
                        editCommand.put(key, value);
                    }
                    System.out.println(editCommand);
                    if (editCommand.size() == 3 && editCommand.get(2).equals("help")) {
                        productHelp = new ProductHelp();
                        productHelp.editProduct();
                    } else {
                        Product productEdited = productController.edit(editCommand);
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
//
//
//
            case "delete":
                try {
                    String delete = productCommand.substring(productCommand.indexOf(splitBySpaces.get(1)));
                    System.out.println(delete);
                    List<String> deleteCommand = new ArrayList<>();
                    deleteCommand.add(productCommand);

                    if (deleteCommand.size() == 1 && deleteCommand.get(0).equals("help")) {
                        productHelp = new ProductHelp();
                        productHelp.deleteProduct();
                    } else {
                        boolean isDeleted = false;
                        isDeleted = productController.delete(deleteCommand);
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
                String list = productCommand.substring(productCommand.indexOf(splitBySpaces.get(1)));
                System.out.println(list);
                String regex = "\\s*-\\w+|\\b\\w+\\b";
                Pattern pattern = Pattern.compile(regex);
                Matcher matcher = pattern.matcher(list);
                List<String> listCommand = new ArrayList<>();
                while (matcher.find()) {
                    String part = matcher.group().trim();
                    listCommand.add(part);
                }

                System.out.println(listCommand);
                if (listCommand.size() == 3 && listCommand.get(2).equals("help")) {
                    productHelp = new ProductHelp();
                    productHelp.listProduct();
                } else {
                    values = new ArrayList<>(listCommand.subList(2, listCommand.size()));
                    try {
                        List<Product> productArray = productController.list(values);
                        System.out.println("List returned successfully.");
                        for (Product products : productArray) {
                            System.out.println("id: " + products.getId() + ", code: " + products.getCode() + ", name: " + products.getName() + ", unitcode: " + products.getUnitCode() + ", type: " + products.getType() + ", price: " + products.getPrice() + ", stock: " + products.getStock());
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
        }
    }
}
