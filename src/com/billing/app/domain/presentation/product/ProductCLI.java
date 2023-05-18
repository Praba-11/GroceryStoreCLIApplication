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
    ProductHelp productHelp;
    Main main = new Main();
    List<String> splitBySpaces;
    ProductController productController = new ProductController();
    Scanner scanner = new Scanner(System.in);

    public void execute(String productCommand) {
        splitBySpaces = main.splitBySpaces(productCommand);
        String action = splitBySpaces.get(0);
        switch (action) {
            case "create":
                splitBySpaces = main.splitBySpaces(productCommand);
                String create;
                if (splitBySpaces.size() == 1) {
                    create = scanner.nextLine();
                    creator(create);
                } else {
                    create = productCommand.substring(productCommand.indexOf(splitBySpaces.get(1)));
                    creator(create);
                }
                break;

            case "edit":
                splitBySpaces = main.splitBySpaces(productCommand);
                String edit;
                if (splitBySpaces.size() == 1) {
                    edit = scanner.nextLine();
                    editor(edit);
                } else {
                    edit = productCommand.substring(productCommand.indexOf(splitBySpaces.get(1)));
                    editor(edit);
                }
                break;

            case "delete":
                try {
                    String deleteCommand = productCommand.substring(productCommand.indexOf(splitBySpaces.get(1)));
                    if (deleteCommand.equals("help")) {
                        productHelp = new ProductHelp();
                        productHelp.deleteProduct();
                    } else {
                        boolean isDeleted = false;
                        isDeleted = productController.delete(deleteCommand);
                        System.out.println(isDeleted);
                    }
                } catch (SQLException exception) {
                    validator = new Validator();
                    System.out.print("Unable to delete product. ");
                    String sqlMessage = validator.validateSQLState(exception);
                    System.out.println(sqlMessage);
                } catch (CodeNotFoundException exception) {
                    System.out.println("Provided id not found. " + exception.getMessage());
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                } catch (IllegalArgumentException exception) {
                    System.out.println("Invalid argument. " + exception.getMessage());
                }
                break;

            case "list":
                String list = productCommand.substring(productCommand.indexOf(splitBySpaces.get(1)));
                String regex = "\\s*-\\w+|\\b\\w+\\b";
                Pattern pattern = Pattern.compile(regex);
                Matcher matcher = pattern.matcher(list);
                List<String> listCommand = new ArrayList<>();
                while (matcher.find()) {
                    String part = matcher.group().trim();
                    listCommand.add(part);
                }

                if (listCommand.size() == 1 && listCommand.get(0).equals("help")) {
                    productHelp = new ProductHelp();
                    productHelp.listProduct();
                } else {
                    try {
                        List<Product> productArray = productController.list(listCommand);
                        System.out.println("List returned successfully.");
                        System.out.println(productArray);
                        for (Product products : productArray) {
                            System.out.println("id: " + products.getId() + ", code: " + products.getCode() + ", name: " + products.getName() + ", unitcode: " + products.getUnitCode() + ", type: " + products.getType() + ", price: " + products.getPrice() + ", stock: " + products.getStock());
                        }
                    } catch (IllegalArgumentException exception) {
                        System.out.println("Incompatible argument. " + exception.getMessage());
                    } catch (TemplateMismatchException exception) {
                        System.out.println("Template mismatch. " + exception.getMessage());
                    } catch (SQLException exception) {
                        validator = new Validator();
                        System.out.print("Unable to list product. ");
                        String sqlMessage = validator.validateSQLState(exception);
                        System.out.println(sqlMessage);
                    } catch (ClassNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                }
                break;

            default:
                System.out.println("Invalid action provided. Please provide a valid command.\n" +
                        "For queries, please use command 'help'");
        }
    }

    private void creator(String create) {
        try {
            String regex = "\\s*,\\s*";
            String[] created = create.trim().split(regex);
            List<String> createCommand = Arrays.asList(created);
            if (createCommand.size() == 1 && createCommand.get(0).equals("help")) {
                productHelp = new ProductHelp();
                productHelp.createProduct();
            } else {
                Product productCreated = null;
                productCreated = productController.create(createCommand);
                System.out.println("Product created successfully.");
                System.out.println("Created product: " + productCreated);
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
    }


    private void editor(String edit) {
        try {
            String formattedInput = edit.replaceAll("\\s*:\\s*", ":");
            String[] keyValuePairs = formattedInput.split("\\s*,\\s*");
            System.out.println(Arrays.toString(keyValuePairs));
            ArrayList<String> pairs = new ArrayList<>(Arrays.asList(keyValuePairs));
            LinkedHashMap<String, String> editCommand = new LinkedHashMap<>();
            if (pairs.size() == 7) {
                for (String pair : pairs) {
                    String[] keyValue = pair.split(":");
                    String key = keyValue[0].trim();
                    String value = keyValue[1].trim();
                    editCommand.put(key, value);
                }
                if (editCommand.size() == 3 && editCommand.get(2).equals("help")) {
                    productHelp = new ProductHelp();
                    productHelp.editProduct();
                } else {
                    Product productEdited = productController.edit(editCommand);
                    System.out.println("Product edited successfully.");
                    System.out.println(productEdited);
                }
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
        } catch (ArrayIndexOutOfBoundsException exception) {
            System.out.println("Template mismatch. Please provide a valid command.");
        }
    }
}
