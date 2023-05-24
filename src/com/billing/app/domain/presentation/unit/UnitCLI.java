package com.billing.app.domain.presentation.unit;

import com.billing.app.domain.entity.Unit;
import com.billing.app.domain.exceptions.*;
import com.billing.app.domain.exceptions.InvalidArgumentException;
import com.billing.app.domain.presentation.Main;
import com.billing.app.domain.presentation.product.ProductHelp;

import java.sql.SQLException;
import java.util.*;

public class UnitCLI {
    UnitValidator unitValidator = new UnitValidator();
    Scanner scanner = new Scanner(System.in);
    UnitHelp unitHelp = new UnitHelp();
    Main main = new Main();
    List<String> splitBySpaces;
    UnitController unitController = new UnitController();
    public void execute(String unitCommand) {
        splitBySpaces = main.splitBySpaces(unitCommand);
        String action = splitBySpaces.get(0);
        switch (action) {

            case "create":

                splitBySpaces = main.splitBySpaces(unitCommand);
                String create;
                if (splitBySpaces.size() == 1) {
                    create = scanner.nextLine();
                    creator(create);
                } else {
                    create = unitCommand.substring(unitCommand.indexOf(splitBySpaces.get(1)));
                    creator(create);
                }
                break;



            case "edit":

                splitBySpaces = main.splitBySpaces(unitCommand);
                String edit;
                if (splitBySpaces.size() == 1) {
                    edit = scanner.nextLine();
                    editor(edit);
                } else {
                    edit = unitCommand.substring(unitCommand.indexOf(splitBySpaces.get(1)));
                    editor(edit);
                }
                break;



            case "delete":
                splitBySpaces = main.splitBySpaces(unitCommand);
                String delete;
                if (splitBySpaces.size() == 1) {
                    System.out.println("Template mismatch. Please provide a valid command.");
                } else {
                    delete = unitCommand.substring(unitCommand.indexOf(splitBySpaces.get(1)));
                    deleter(delete);
                }
                break;



            case "list":
                String list;
                splitBySpaces = main.splitBySpaces(unitCommand);
                if (splitBySpaces.size() == 1) {
                    list = "";
                    lister(list);
                } else {
                    list = unitCommand.substring(unitCommand.indexOf(splitBySpaces.get(1)));
                    lister(list);
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
    }


    private void editor(String edit) {
        try {
            String formattedInput = edit.replaceAll("\\s*:\\s*", ":");
            String[] keyValuePairs = formattedInput.split("\\s*,\\s*");
            if (keyValuePairs.length == 1 && keyValuePairs[0].equals("help")) {
                unitHelp.editUnit();
            } else {
                ArrayList<String> pairs = new ArrayList<>(Arrays.asList(keyValuePairs));
                LinkedHashMap<String, String> editCommand = new LinkedHashMap<>();
                if (pairs.size() == 5) {
                    for (String pair : pairs) {
                        String[] keyValue = pair.split(":");
                        String key = keyValue[0].trim();
                        String value = keyValue[1].trim();
                        editCommand.put(key, value);
                    }
                    Unit unitEdited = unitController.edit(editCommand);
                    System.out.println("Unit edited successfully.");
                    System.out.println("Edited unit: " + unitEdited);
                } else {
                    System.out.println("Template mismatch. Please provide a valid command for editing the product.");
                }
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
        } catch (NotFoundException exception) {
            System.out.println("Invalid unit id. " + exception.getMessage());
        } catch (InvalidArgumentException exception) {
            System.out.println("Invalid argument provided. " + exception.getMessage());
        }
    }

    private void deleter(String delete) {
        try {
            if (delete.equals("help")) {
                unitHelp.deleteUnit();
            } else {
                boolean isDeleted = false;
                isDeleted = unitController.delete(delete);
                if (isDeleted) {
                    System.out.println("Unit ID: '" + delete + "' deleted successfully.");
                } else {
                    System.out.println("Unit ID: '" + delete + "' deletion unsuccessful.");
                }
            }
        } catch (SQLException exception) {
            unitValidator = new UnitValidator();
            System.out.print("Unable to delete unit. ");
            String sqlMessage = unitValidator.validateSQLState(exception);
            System.out.println(sqlMessage);
        } catch (NotFoundException exception) {
            System.out.println("Provided Id not found. " + exception.getMessage());
        } catch (InvalidArgumentException exception) {
            System.out.println("Invalid argument. " + exception.getMessage());
        }
    }


    private void lister(String list) {
        try {
            if (list.equals("help")) {
                unitHelp.listUnit();
            } else if (list.equals("")) {
                List<Unit> unitArrayList = unitController.list();
                if (!unitArrayList.isEmpty()) {
                    System.out.println("Units are enlisted as follows.");
                    for (Unit unit : unitArrayList) {
                        System.out.println("id: " + unit.getId() + ", name: " + unit.getName() + ", code: " + unit.getCode() + ", description: " + unit.getDescription() + ", isdividable: " + unit.isDividable());
                    }
                } else {
                    System.out.println("No Products to list here. Provided command matches no existing products.");
                }
            } else {
                System.out.println("Template mismatch. Please provide a valid command for listing the units.");
            }
        } catch (SQLException exception) {
            unitValidator = new UnitValidator();
            System.out.print("Unable to list unit. ");
            String sqlMessage = unitValidator.validateSQLState(exception);
            System.out.println(sqlMessage);
        }
    }
}
