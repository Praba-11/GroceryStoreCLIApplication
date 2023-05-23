package com.billing.app.domain.presentation.unit;

import com.billing.app.domain.entity.Unit;
import com.billing.app.domain.exceptions.*;
import com.billing.app.domain.exceptions.InvalidArgumentException;
import com.billing.app.domain.presentation.Main;

import java.sql.SQLException;
import java.util.*;

public class UnitCLI {
    UnitValidator unitValidator = new UnitValidator();
    ArrayList<String> values;
    UnitHelp unitHelp;
    Main main = new Main();
    List<String> splitBySpaces;
    public void execute(String unitCommand) {
        splitBySpaces = main.splitBySpaces(unitCommand);
        String action = splitBySpaces.get(0);
        UnitController unitController = new UnitController();

        switch (action) {

            case "create":
                try {
                    System.out.println(unitCommand);
                    String create = unitCommand.substring(unitCommand.indexOf(splitBySpaces.get(1)));
                    String regex = "\\s*,\\s*";
                    String[] created = create.trim().split(regex);
                    List<String> createCommand = Arrays.asList(created);
                    System.out.println(createCommand);
                    if (createCommand.size() == 1 && createCommand.get(0).equals("help")) {
                        unitHelp = new UnitHelp();
                        unitHelp.createUnit();
                    } else {
                        Unit unitCreated = null;
                        unitCreated = unitController.create(createCommand);
                        System.out.println("Unit created successfully.");
                        System.out.println("Created unit: " + unitCreated);
                    }

                } catch (SQLException exception) {
                    System.out.print("Unable to create unit. ");
                    String sqlMessage = unitValidator.validateSQLState(exception);
                    System.out.println(sqlMessage);
                } catch (ObjectNullPointerException exception) {
                    System.out.println("Unable to edit unit. " + exception.getMessage());
                } catch (TypeMismatchException exception) {
                    System.out.println("Type mismatch occurred at " + exception.getMessage());
                } catch (TemplateMismatchException exception) {
                    System.out.println("Template mismatch. " + exception.getMessage());
                } catch (InvalidArgumentException exception) {
                    System.out.println("Invalid argument provided. " + exception.getMessage());
                }
                break;



            case "edit":
                try {
                    String edit = unitCommand.substring(unitCommand.indexOf(splitBySpaces.get(1)));
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
                    if (editCommand.size() == 3 && editCommand.get(2).equals("help")) {
                        unitHelp = new UnitHelp();
                        unitHelp.editUnit();
                    } else {
                        Unit unitEdited = unitController.edit(editCommand);
                        System.out.println("Unit edited successfully.");
                        System.out.println("Edited unit: " + unitEdited);
                }

                } catch (TemplateMismatchException exception) {
                    System.out.println("Template mismatch. Incompatible attribute provided. " + exception.getMessage());
                } catch (SQLException exception) {
                    unitValidator = new UnitValidator();
                    System.out.print("Unable to edit unit. ");
                    String sqlMessage = unitValidator.validateSQLState(exception);
                    System.out.println(sqlMessage);
                } catch (ObjectNullPointerException exception) {
                    System.out.println("Unable to edit unit. " + exception.getMessage());
                } catch (TypeMismatchException exception) {
                    System.out.println("Type mismatch occurred at " + exception.getMessage());
                } catch (CodeOrIDNotFoundException exception) {
                    System.out.println("Invalid unit id. " + exception.getMessage());
                } catch (InvalidArgumentException exception) {
                    System.out.println("Invalid argument provided. " + exception.getMessage());
                }
                break;



            case "delete":
                try {
                    String regex = "\\s+";
                    List<String> deleteCommand = Arrays.asList(unitCommand.split(regex));
                    if (deleteCommand.size() == 2) {
                        if (deleteCommand.get(1).equals("help")) {
                            unitHelp = new UnitHelp();
                            unitHelp.deleteUnit();
                        } else {
                            boolean isDeleted = false;
                            isDeleted = unitController.delete(deleteCommand.get(1));
                            System.out.println(isDeleted);
                        }
                    } else {
                        System.out.println(deleteCommand);
                        System.out.println("Template mismatch. Please provide a valid command.");
                    }
                } catch (SQLException exception) {
                    unitValidator = new UnitValidator();
                    System.out.print("Unable to delete unit. ");
                    String sqlMessage = unitValidator.validateSQLState(exception);
                    System.out.println(sqlMessage);
                } catch (CodeOrIDNotFoundException exception) {
                    System.out.println("Provided Id not found. " + exception.getMessage());
                } catch (InvalidArgumentException exception) {
                    System.out.println("Invalid argument. " + exception.getMessage());
                }
                break;



            case "list":
                try {
                    String regex = "\\s+";
                    List<String> listCommand = Arrays.asList(unitCommand.split(regex));
                    if (listCommand.size() == 2) {
                        if (listCommand.get(1).equals("help")) {
                            unitHelp = new UnitHelp();
                            unitHelp.listUnit();
                        } else {
                            System.out.println("Template mismatch. Please provide a valid command.");
                        }
                    } else if (listCommand.size() == 1) {
                        List<Unit> unitArrayList = unitController.list();
                        System.out.println("List returned successfully.");
                        for (Unit unit : unitArrayList) {
                            System.out.println("id: " + unit.getId() + ", name: " + unit.getName() + ", code: " + unit.getCode() + ", description: " + unit.getDescription() + ", isdividable: " + unit.isDividable());
                        }
                    } else {
                        System.out.println("Template mismatch. Please provide a valid command.");
                    }
                } catch (SQLException exception) {
                    unitValidator = new UnitValidator();
                    System.out.print("Unable to list unit. ");
                    String sqlMessage = unitValidator.validateSQLState(exception);
                    System.out.println(sqlMessage);
                }
                break;

            default:
                System.out.println("Invalid action provided. Please provide a valid command.\n" +
                        "For queries, please use command 'help'");
        }
    }
}
