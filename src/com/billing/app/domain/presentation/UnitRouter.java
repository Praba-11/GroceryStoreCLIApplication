package com.billing.app.domain.presentation;

import com.billing.app.domain.entity.Product;
import com.billing.app.domain.entity.Unit;
import com.billing.app.domain.exceptions.CodeNotFoundException;
import com.billing.app.domain.exceptions.unit.CodeNullException;
import com.billing.app.domain.exceptions.unit.TemplateMismatchException;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.Scanner;

public class UnitRouter {
    Formatter formatter = new Formatter();
    public void execute(ArrayList<String> arrayList) {
        String action = arrayList.get(1);
        Scanner scanner = new Scanner(System.in);
        UnitParser unitParser = new UnitParser();

        switch (action) {

            case "create":
                try {
                    Unit unitCreated = null;
                    unitCreated = unitParser.create(arrayList);
                    System.out.println(unitCreated);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
                break;



            case "edit":
                try {
                    Unit unitEdited = unitParser.edit(arrayList);
                    System.out.println(unitEdited);
                } catch (CodeNullException exception) {
                    System.out.println("Unit Code is unique, cannot be edited. " + exception.getMessage());
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
                    isDeleted = unitParser.delete(arrayList);
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
                    ArrayList<Unit> unitArrayList = unitParser.list();
                    System.out.println("List returned successfully.");
                    for (Unit unit : unitArrayList) {
                        System.out.println("id: " + unit.getId() + ", name: " + unit.getName() + ", code: " + unit.getCode() + ", description " + unit.getDescription() + ", isdividable: " + unit.isDividable());
                    }
                    System.out.println(formatter.toString());
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
        }
    }
}
