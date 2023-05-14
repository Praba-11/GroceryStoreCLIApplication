package com.billing.app.domain.presentation.unit;

import com.billing.app.domain.entity.Unit;
import com.billing.app.domain.exceptions.CodeNotFoundException;
import com.billing.app.domain.exceptions.CodeNullException;
import com.billing.app.domain.exceptions.TemplateMismatchException;
import com.billing.app.domain.presentation.Validator;
import com.billing.app.domain.presentation.store.StoreHelp;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.Scanner;

public class UnitCLI {
    UnitValidator unitValidator = new UnitValidator();
    ArrayList<String> values;
    UnitHelp unitHelp;
    public void execute(ArrayList<String> arrayList) {
        String action = arrayList.get(1);
        Scanner scanner = new Scanner(System.in);
        UnitController unitController = new UnitController();

        switch (action) {

            case "create":
                try {
                    if (arrayList.size() == 3 && arrayList.get(2).equals("help")) {
                        unitHelp = new UnitHelp();
                        unitHelp.createUnit();
                    } else {
                        values = new ArrayList<>(arrayList.subList(2, arrayList.size()));
                        Unit unitCreated = null;
                        unitCreated = unitController.create(values);
                        System.out.println("Unit created successfully.");
                        System.out.println("Created unit: " + unitCreated);
                    }

                } catch (SQLException exception) {
                    System.out.print("Unable to create unit. ");
                    String sqlMessage = unitValidator.validateSQLState(exception);
                    System.out.println(sqlMessage);
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
                break;



            case "edit":
                try {
                    if (arrayList.size() == 3 && arrayList.get(2).equals("help")) {
                        unitHelp = new UnitHelp();
                        unitHelp.editUnit();
                    } else {
                        Unit unitEdited = unitController.edit(arrayList);
                        System.out.println(unitEdited);
                }

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
                    if (arrayList.size() == 3 && arrayList.get(2).equals("help")) {
                        unitHelp = new UnitHelp();
                        unitHelp.deleteUnit();
                    } else {
                        boolean isDeleted = false;
                        isDeleted = unitController.delete(arrayList);
                        System.out.println(isDeleted);
                }

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
                    if (arrayList.size() == 3 && arrayList.get(2).equals("help")) {
                        unitHelp = new UnitHelp();
                        unitHelp.listUnit();
                    } else {
                        ArrayList<Unit> unitArrayList = unitController.list();
                        System.out.println("List returned successfully.");
                        for (Unit unit : unitArrayList) {
                            System.out.println("id: " + unit.getId() + ", name: " + unit.getName() + ", code: " + unit.getCode() + ", description: " + unit.getDescription() + ", isdividable: " + unit.isDividable());
                        }
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
        }
    }
}
