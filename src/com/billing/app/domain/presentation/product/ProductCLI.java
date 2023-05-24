package com.billing.app.domain.presentation.product;

import com.billing.app.domain.entity.Product;
import com.billing.app.domain.exceptions.*;
import com.billing.app.domain.exceptions.InvalidArgumentException;
import com.billing.app.domain.exceptions.TemplateMismatchException;
import com.billing.app.domain.presentation.Main;

import java.sql.SQLException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ProductCLI {
    ProductValidator productValidator = new ProductValidator();
    ProductHelp productHelp = new ProductHelp();
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
                splitBySpaces = main.splitBySpaces(productCommand);
                String delete;
                if (splitBySpaces.size() == 1) {
                    System.out.println("Template mismatch. Please provide a valid command.");
                } else {
                    delete = productCommand.substring(productCommand.indexOf(splitBySpaces.get(1)));
                    deleter(delete);
                }
                break;

            case "list":
                list(productCommand);
                break;

            default:
                System.out.println("Invalid action provided. Please provide a valid command.\n" +
                        "For queries, please use command 'help'");
        }
    }

    public void list(String productCommand) {
        String list;
        splitBySpaces = main.splitBySpaces(productCommand);
        if (splitBySpaces.size() == 1) {
            list = "";
            lister(list);
        } else {
            list = productCommand.substring(productCommand.indexOf(splitBySpaces.get(1)));
            lister(list);
        }
    }


    private void creator(String create) {
        try {
            String regex = "\\s*,\\s*";
            String[] created = create.trim().split(regex);
            List<String> createCommand = Arrays.asList(created);
            if (createCommand.size() == 1 && createCommand.get(0).equals("help")) {
                productHelp.createProduct();
            } else {
                Product productCreated = null;
                productCreated = productController.create(createCommand);
                System.out.println("Product created successfully.");
                System.out.println("Created product: " + productCreated);
            }
        } catch (SQLException exception) {
            productValidator = new ProductValidator();
            System.out.print("Unable to create product. ");
            String sqlMessage = productValidator.validateSQLState(exception);
            System.out.println(sqlMessage);
        } catch (TemplateMismatchException exception) {
            System.out.println("Template mismatch. " + exception.getMessage());
        } catch (ClassNotFoundException exception) {
            throw new RuntimeException(exception);
        } catch (InvalidArgumentException exception) {
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
            if (keyValuePairs.length == 1 && keyValuePairs[0].equals("help")) {
                productHelp = new ProductHelp();
                productHelp.editProduct();
            } else {
                ArrayList<String> pairs = new ArrayList<>(Arrays.asList(keyValuePairs));
                LinkedHashMap<String, String> editCommand = new LinkedHashMap<>();
                if (pairs.size() == 7) {
                    for (String pair : pairs) {
                        String[] keyValue = pair.split(":");
                        String key = keyValue[0].trim();
                        String value = keyValue[1].trim();
                        editCommand.put(key, value);
                    }
                    Product productEdited = productController.edit(editCommand);
                    System.out.println("Product edited successfully.");
                    System.out.println("Edited product: " + productEdited);
                } else {
                    System.out.println("Template mismatch. Please provide a valid command for editing the product.");
                }
            }
        } catch (SQLException exception) {
            productValidator = new ProductValidator();
            System.out.print("Unable to edit product. ");
            String sqlMessage = productValidator.validateSQLState(exception);
            System.out.println(sqlMessage);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (TemplateMismatchException exception) {
            System.out.println("Template mismatch. " + exception.getMessage());
        } catch (TypeMismatchException exception) {
            System.out.println("Type mismatch occurred at " + exception.getMessage());
        } catch (InvalidArgumentException exception) {
            System.out.println("Incompatible argument. " + exception.getMessage());
        } catch (ObjectNullPointerException exception) {
            System.out.println("Unable to edit product. " + exception.getMessage());
        } catch (NotFoundException exception) {
            System.out.println("Invalid product for editing\n. " + exception.getMessage());
        } catch (ArrayIndexOutOfBoundsException exception) {
            System.out.println("Template mismatch. Please provide a valid command.");
        }
    }

    private void deleter(String delete) {
        try {
            if (delete.equals("help")) {
                productHelp.deleteProduct();
            } else {
                boolean isDeleted = false;
                isDeleted = productController.delete(delete);
                if (isDeleted) {
                    System.out.println("Product ID: '" + delete + "' deleted successfully.");
                } else {
                    System.out.println("Product ID: '" + delete + "' deletion unsuccessful.");
                }
            }
        } catch (SQLException exception) {
            System.out.print("Unable to delete product. ");
            String sqlMessage = productValidator.validateSQLState(exception);
            System.out.println(sqlMessage);
        } catch (NotFoundException exception) {
            System.out.println("Provided Id not found. " + exception.getMessage());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (InvalidArgumentException exception) {
            System.out.println("Invalid argument. " + exception.getMessage());
        }
    }

    private void lister(String delete) {

        String regex = "\\s*-\\w+|\\b[^\s,]+\\b";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(delete);
        List<String> listCommand = new ArrayList<>();
        while (matcher.find()) {
            String part = matcher.group().trim();
            listCommand.add(part);
        }

        if (listCommand.size() == 1 && listCommand.get(0).equals("help")) {
            productHelp.listProduct();
        } else {
            try {
                List<Product> productArray = productController.list(listCommand);
                if (!productArray.isEmpty()) {
                    System.out.println("Products are enlisted as follows.");
                    for (Product product : productArray) {
                        String output = String.format("id: %-4d, code: %-12s, name: %-22s, unitcode: %-14s, type: %-15s, price: %-15.2f, stock: %-15.2f, isdeleted: %b",
                                product.getId(), product.getCode(), product.getName(), product.getUnitCode(),
                                product.getType(), product.getPrice(), product.getStock(), product.isDeleted());
                        System.out.println(output);
                    }
                } else {
                    System.out.println("No Products to list here. Provided command matches no existing products.");
                }
            } catch (InvalidArgumentException exception) {
                System.out.println("Incompatible argument. " + exception.getMessage());
            } catch (TemplateMismatchException exception) {
                System.out.println("Template mismatch. " + exception.getMessage());
            } catch (SQLException exception) {
                System.out.print("Unable to list product. ");
                String sqlMessage = productValidator.validateSQLState(exception);
                System.out.println(sqlMessage);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }
}


