package com.billing.app.domain.presentation.store;

import com.billing.app.domain.entity.Product;
import com.billing.app.domain.entity.Store;
import com.billing.app.domain.entity.Unit;
import com.billing.app.domain.exceptions.*;
import com.billing.app.domain.exceptions.IllegalArgumentException;
import com.billing.app.domain.presentation.Main;
import com.billing.app.domain.presentation.Validator;
import com.billing.app.domain.presentation.product.ProductHelp;
import com.billing.app.domain.presentation.unit.UnitHelp;
import com.billing.app.domain.presentation.unit.UnitValidator;

import java.sql.SQLException;
import java.util.*;

public class StoreCLI {
    StoreHelp storeHelp;
    Main main = new Main();
    List<String> splitBySpaces;
    Scanner scanner = new Scanner(System.in);
    StoreValidator storeValidator = new StoreValidator();
    StoreController storeController = new StoreController();

    public void execute(String storeCommand) {
        splitBySpaces = main.splitBySpaces(storeCommand);
        String action = splitBySpaces.get(0);

        switch (action) {
            case "create":
                splitBySpaces = main.splitBySpaces(storeCommand);
                String create;
                if (splitBySpaces.size() == 1) {
                    create = scanner.nextLine();
                    creator(create);
                } else {
                    create = storeCommand.substring(storeCommand.indexOf(splitBySpaces.get(1)));
                    creator(create);
                }
                break;

            case "edit":
                splitBySpaces = main.splitBySpaces(storeCommand);
                String edit;
                if (splitBySpaces.size() == 1) {
                    edit = scanner.nextLine();
                    editor(edit);
                } else {
                    edit = storeCommand.substring(storeCommand.indexOf(splitBySpaces.get(1)));
                    editor(edit);
                }
                break;
//
//
//            case "delete":
//                try {
//                    String deleteCommand = storeCommand.substring(storeCommand.indexOf(splitBySpaces.get(1)));
//                    if (deleteCommand.equals("help")) {
//                        storeHelp = new StoreHelp();
//                        storeHelp.deleteStore();
//                    } else {
//                        boolean isDeleted = false;
//                        isDeleted = storeController.delete(deleteCommand);
//                        System.out.println(isDeleted);
//                    }
//                } catch (SQLException exception) {
//                    System.out.print("Unable to delete product. ");
//                    String sqlMessage = storeValidator.validateSQLState(exception);
//                    System.out.println(sqlMessage);
//                } catch (CodeNotFoundException exception) {
//                    System.out.println("Provided id not found. " + exception.getMessage());
//                } catch (ClassNotFoundException e) {
//                    throw new RuntimeException(e);
//                } catch (IllegalArgumentException exception) {
//                    System.out.println("Invalid argument. " + exception.getMessage());
//                }
//                break;
//
//
//            case "view":
//                try {
//                    String regex = "\\s+";
//                    List<String> listCommand = Arrays.asList(storeCommand.split(regex));
//                    if (listCommand.size() == 2) {
//                        if (listCommand.get(1).equals("help")) {
//                            storeHelp = new StoreHelp();
////                            storeHelp.listStore();
//                        } else {
//                            System.out.println("Template mismatch. Please provide a valid command.");
//                        }
//                    } else if (listCommand.size() == 1) {
//                        Store store = storeController.list();
//                        System.out.println("Store details returned successfully.");
////                        System.out.println("id: " + store.getId() + ", name: " + store.getName() + ", code: " + store.getCode() + ", description: " + store.getDescription() + ", isdividable: " + store.isDividable());
//                    } else {
//                        System.out.println("Template mismatch. Please provide a valid command.");
//                    }
//                } catch (SQLException exception) {
//                    System.out.print("Unable to list store. ");
//                    String sqlMessage = storeValidator.validateSQLState(exception);
//                    System.out.println(sqlMessage);
//                }

        }
    }


    private void creator(String create) {
        try {
            String regex = "\\s*,\\s*";
            String[] created = create.trim().split(regex);
            List<String> createCommand = Arrays.asList(created);
            if (createCommand.size() == 1 && createCommand.get(0).equals("help")) {
                storeHelp.createStore();
            } else {
                Store storeCreated = null;
                storeCreated = storeController.create(createCommand);
                if (storeCreated != null) {
                    System.out.println("Store created successfully.");
                    System.out.println("Created store details: " + storeCreated);
                } else {
                    System.out.println("Store already exists.");
                }
            }
        } catch (SQLException exception) {
            System.out.print("Unable to create store. ");
            String sqlMessage = storeValidator.validateSQLState(exception);
            System.out.println(sqlMessage);
        } catch (TemplateMismatchException exception) {
            System.out.println("Template mismatch. " + exception.getMessage());
        } catch (ClassNotFoundException exception) {
            throw new RuntimeException(exception);
        } catch (IllegalArgumentException exception) {
            System.out.println("Invalid argument provided. " + exception.getMessage());
        } catch (TypeMismatchException exception) {
            System.out.println("Type mismatch occurred at " + exception.getMessage());
        }
    }

    private void editor(String edit) {
        try {
            String formattedInput = edit.replaceAll("\\s*:\\s*", ":");
            String[] keyValuePairs = formattedInput.split("\\s*,\\s*");
            ArrayList<String> pairs = new ArrayList<>(Arrays.asList(keyValuePairs));
            System.out.println(pairs);
            LinkedHashMap<String, String> editCommand = new LinkedHashMap<>();
            System.out.println(pairs.size());

            if (pairs.size() == 4) {
                for (String pair : pairs) {
                    String[] keyValue = pair.split(":");
                    String key = keyValue[0].trim();
                    String value = keyValue[1].trim();
                    editCommand.put(key, value);
                }
                if (editCommand.size() == 3 && editCommand.get(2).equals("help")) {
                    storeHelp = new StoreHelp();
                    storeHelp.editStore();
                } else {
                    Store storeEdited = storeController.edit(editCommand);
                    System.out.println("Store edited successfully.");
                    System.out.println(storeEdited);
                }
            }

        } catch (SQLException exception) {
            System.out.print("Unable to edit store. ");
            String sqlMessage = storeValidator.validateSQLState(exception);
            System.out.println(sqlMessage);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (TemplateMismatchException exception) {
            System.out.println("Template mismatch. " + exception.getMessage());
        } catch (TypeMismatchException exception) {
            System.out.println("Type mismatch occurred at " + exception.getMessage());
        } catch (IllegalArgumentException exception) {
            System.out.println("Incompatible argument. " + exception.getMessage());
        } catch (ArrayIndexOutOfBoundsException exception) {
            System.out.println("Template mismatch. Please provide a valid command.");
        }
    }
}