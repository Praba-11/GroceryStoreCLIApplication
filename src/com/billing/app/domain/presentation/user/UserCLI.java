package com.billing.app.domain.presentation.user;

import com.billing.app.domain.entity.User;
import com.billing.app.domain.exceptions.*;
import com.billing.app.domain.exceptions.InvalidArgumentException;
import com.billing.app.domain.presentation.Main;

import java.sql.SQLException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserCLI {
    UserValidator userValidator = new UserValidator();
    Main main = new Main();
    List<String> splitBySpaces;
    Scanner scanner = new Scanner(System.in);
    UserController userController = new UserController();
    UserHelp userHelp = new UserHelp();

    public void execute(String userCommand) {

        splitBySpaces = main.splitBySpaces(userCommand);
        String action = splitBySpaces.get(0);

        switch (action) {

            case "create":
                splitBySpaces = main.splitBySpaces(userCommand);
                String create;
                if (splitBySpaces.size() == 1) {
                    create = scanner.nextLine();
                    creator(create);
                } else {
                    create = userCommand.substring(userCommand.indexOf(splitBySpaces.get(1)));
                    creator(create);
                }
                break;


            case "edit":
                splitBySpaces = main.splitBySpaces(userCommand);
                String edit;
                if (splitBySpaces.size() == 1) {
                    edit = scanner.nextLine();
                    editor(edit);
                } else {
                    edit = userCommand.substring(userCommand.indexOf(splitBySpaces.get(1)));
                    editor(edit);
                }
                break;


            case "delete":
                splitBySpaces = main.splitBySpaces(userCommand);
                String delete;
                if (splitBySpaces.size() == 1) {
                    System.out.println("Template mismatch. Please provide a valid command.");
                } else {
                    delete = userCommand.substring(userCommand.indexOf(splitBySpaces.get(1)));
                    deleter(delete);
                }
                break;


            case "list":
                splitBySpaces = main.splitBySpaces(userCommand);
                String list;
                if (splitBySpaces.size() == 1) {
                    list = "";
                    lister(list);
                } else {
                    list = userCommand.substring(userCommand.indexOf(splitBySpaces.get(1)));
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
                userHelp.createUser();
            } else {
                User userCreated = null;
                userCreated = userController.create(createCommand);
                System.out.println("User created successfully.");
                System.out.println("Created user: " + userCreated);
            }
        } catch (SQLException exception) {
            System.out.print("Unable to create user. ");
            String sqlMessage = userValidator.validateSQLState(exception);
            System.out.println(sqlMessage);
        } catch (TemplateMismatchException exception) {
            System.out.println("Template mismatch. " + exception.getMessage());
        } catch (InvalidArgumentException exception) {
            System.out.println("Incompatible argument provided. " + exception.getMessage());
        } catch (TypeMismatchException exception) {
            System.out.println("Type mismatch occurred at " + exception.getMessage());
        } catch (ObjectNullPointerException exception) {
            System.out.println("Unable to create user." + exception.getMessage());
        }
    }



    private void editor(String edit) {
        try {
            String formattedInput = edit.replaceAll("\\s*:\\s*", ":");
            String[] keyValuePairs = formattedInput.split("\\s*,\\s*");
            if (keyValuePairs.length == 1 && keyValuePairs[0].equals("help")) {
                userHelp.editUser();
            } else {
                List<String> pairs = new ArrayList<>(Arrays.asList(keyValuePairs));
                LinkedHashMap<String, String> editCommand = new LinkedHashMap<>();
                if (pairs.size() == 7) {
                    for (String pair : pairs) {
                        String[] keyValue = pair.split(":");
                        String key = keyValue[0].trim();
                        String value = keyValue[1].trim();
                        editCommand.put(key, value);
                    }
                    if (editCommand.size() == 3 && editCommand.get(2).equals("help")) {
                        userHelp.editUser();
                    } else {
                        User userEdited = userController.edit(editCommand);
                        System.out.println("User edited successfully.");
                        System.out.println(userEdited);
                    }
                } else {
                    System.out.println("Template mismatch. Please provide a valid command.");
                }
            }
        } catch (SQLException exception) {
            System.out.print("Unable to edit user. ");
            String sqlMessage = userValidator.validateSQLState(exception);
            System.out.println(sqlMessage);
        } catch (TemplateMismatchException exception) {
            System.out.println("Template mismatch. " + exception.getMessage());
        } catch (TypeMismatchException exception) {
            System.out.println("Type mismatch occurred at " + exception.getMessage());
        } catch (InvalidArgumentException exception) {
            System.out.println("Incompatible argument. " + exception.getMessage());
        } catch (ObjectNullPointerException exception) {
            System.out.println("Unable to edit user. " + exception.getMessage());
        } catch (NotFoundException exception) {
            System.out.println("Invalid User id. " + exception.getMessage());
        } catch (ArrayIndexOutOfBoundsException exception) {
            System.out.println("Template mismatch. Please provide a valid command.");
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }



    private void deleter(String delete) {
        try {
            if (delete.equals("help")) {
                        userHelp.deleteUser();
            } else {
                boolean isDeleted = false;
                isDeleted = userController.delete(delete);
                System.out.println(isDeleted);
                System.out.println("User '" + delete + "' deleted successfully.");
            }
        } catch (SQLException exception) {
            System.out.print("Unable to delete user. ");
            String sqlMessage = userValidator.validateSQLState(exception);
            System.out.println(sqlMessage);
        } catch (NotFoundException exception) {
            System.out.println("Provided username not found. " + exception.getMessage());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (InvalidArgumentException exception) {
            System.out.println("Invalid argument. " + exception.getMessage());
        }
    }




    private void lister(String list) {
        String regex = "\\s*-\\w+|\\b\\w+\\b";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(list);
        List<String> listCommand = new ArrayList<>();
        while (matcher.find()) {
            String part = matcher.group().trim();
            listCommand.add(part);
        }

        if (listCommand.size() == 1 && listCommand.get(0).equals("help")) {
            userHelp.listUser();
        } else {
            try {
                List<User> userArray = userController.list(listCommand);
                System.out.println("List returned successfully.");
                for (User user : userArray) {
                    String output = String.format("id: %-4d, type: %-15s, username: %-15s, password: %-20s, firstname: %-15s, lastname: %-15s, phonenumber: %-15s, isavailable: %s",
                            user.getId(), user.getUserType(), user.getUsername(), user.getPassword(),
                            user.getFirstName(), user.getLastName(), user.getPhoneNumber(), user.isAvailable());
                    System.out.println(output);
                }
            } catch (InvalidArgumentException exception) {
                System.out.println("Incompatible argument. " + exception.getMessage());
            } catch (TemplateMismatchException exception) {
                System.out.println("Template mismatch. " + exception.getMessage());
            } catch (SQLException exception) {
                System.out.print("Unable to list user. ");
                String sqlMessage = userValidator.validateSQLState(exception);
                System.out.println(sqlMessage);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
