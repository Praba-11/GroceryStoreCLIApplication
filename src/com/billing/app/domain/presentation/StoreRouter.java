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
                    Store storeCreated = null;
                    storeCreated = storeParser.create(arrayList);
                    System.out.println(storeCreated);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }

            case "edit":
                try {
                    Store storeEdited = storeParser.edit(arrayList);
                    System.out.println(storeEdited);
                } catch (TemplateMismatchException exception) {
                    System.out.println("Template mismatch. Incompatible attribute provided. " + exception.getMessage());
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
                break;


            case "delete":
                try {
                    boolean isDeleted = false;
                    isDeleted = storeParser.delete(arrayList);
                    System.out.println(isDeleted);
                } catch (TemplateMismatchException exception) {
                    System.out.println("Template mismatch. " + exception.getMessage());
                } catch (SQLException exception) {
                    throw new RuntimeException(exception);
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
                break;

        }
    }
}
