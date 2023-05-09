package com.billing.app.domain.presentation.store;

import com.billing.app.domain.entity.Store;
import com.billing.app.domain.exceptions.TemplateMismatchException;
import com.billing.app.domain.presentation.product.ProductHelp;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class StoreCLI {
    public void execute(ArrayList<String> arrayList) {
        StoreHelp storeHelp;
        String action = arrayList.get(1);
        Scanner scanner = new Scanner(System.in);
        StoreController storeController = new StoreController();

        switch (action) {
            case "create":
                try {
                    if (arrayList.size() == 3 && arrayList.get(2).equals("help")) {
                        storeHelp = new StoreHelp();
                        storeHelp.createStore();
                    } else {
                        Store storeCreated;
                        storeCreated = storeController.create(arrayList);
                        if(storeCreated != null) {
                            System.out.println(storeCreated);
                        } else {
                            System.out.println("Multiple Stores cannot be created. Please try again.");
                        }
                    }
                } catch (SQLException exception) {
                    if (exception.getSQLState().equals("23514")) {
                        System.out.println("Store already exists. " + exception.getMessage());
                    } else {
                        System.out.println(exception.getMessage());
                    }
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
                break;


            case "edit":
                try {
                    if (arrayList.size() == 3 && arrayList.get(2).equals("help")) {
                        storeHelp = new StoreHelp();
                        storeHelp.editStore();
                    } else {
                        Store storeEdited;
                        storeEdited = storeController.edit(arrayList);
                        System.out.println(storeEdited.toString());
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                } catch (TemplateMismatchException exception) {
                    System.out.println("Template mismatch. Incompatible attribute provided. " + exception.getMessage());
                }
                break;


            case "delete":
                try {
                    if (arrayList.size() == 3 && arrayList.get(2).equals("help")) {
                        storeHelp = new StoreHelp();
                        storeHelp.deleteStore();
                    } else {
                        boolean isDeleted = false;
                        System.out.print("Admin password: ");
                        String password = scanner.nextLine();
                        isDeleted = storeController.delete();
                        System.out.println(isDeleted);
                    }
                } catch (SQLException exception) {
                    throw new RuntimeException(exception);
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
                break;

            case "view":
                try {
                    if (arrayList.size() == 3 && arrayList.get(2).equals("help")) {
                        storeHelp = new StoreHelp();
                        storeHelp.viewStore();
                    } else {
                        Store store;
                        store = storeController.view();
                        System.out.println("id: " + store.getId() + ", name: " + store.getName() + ", gstnumber: " + store.getGstNumber() + ", phonenumber: " + store.getPhoneNumber() + ", address: " + store.getAddress());
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }

        }
    }
}
