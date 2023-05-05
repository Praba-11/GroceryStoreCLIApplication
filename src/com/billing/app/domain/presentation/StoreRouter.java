package com.billing.app.domain.presentation;

import com.billing.app.domain.entity.Store;
import com.billing.app.domain.entity.Store;
import com.billing.app.domain.exceptions.CodeNotFoundException;
import com.billing.app.domain.exceptions.unit.CodeNullException;
import com.billing.app.domain.exceptions.unit.TemplateMismatchException;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class StoreRouter {
    public void execute(ArrayList<String> arrayList) {
        String action = arrayList.get(1);
        Scanner scanner = new Scanner(System.in);
        StoreParser storeParser = new StoreParser();

        switch (action) {
            case "create":
                try {
                    Store storeCreated;
                    storeCreated = storeParser.create(arrayList);
                    System.out.println(storeCreated.toString());
                } catch (SQLException exception) {
                    if (exception.getSQLState().equals("23514")) {
                        System.out.println("Store already exists. " + exception.getMessage());
                    } else {
                        System.out.println(exception.getMessage());
                    }
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }

            case "edit":
                try {
                    Store storeEdited;
                    storeEdited = storeParser.edit(arrayList);
                    System.out.println(storeEdited.toString());
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
                break;


            case "delete":
                try {
                    boolean isDeleted = false;
                    isDeleted = storeParser.delete();
                    System.out.println(isDeleted);
                } catch (SQLException exception) {
                    throw new RuntimeException(exception);
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
                break;

        }
    }
}
