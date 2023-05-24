package com.billing.app.domain.presentation;

import com.billing.app.domain.entity.User;
import com.billing.app.domain.exceptions.InvalidArgumentException;
import com.billing.app.domain.presentation.user.UserCLI;
import com.billing.app.domain.presentation.user.UserController;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class CLI {
    UserController userController = new UserController();
    Scanner scanner = new Scanner(System.in);
    Router router = new Router();
    Main main = new Main();
    public void execute() throws InvalidArgumentException {
        while (true) {
            if (!userController.adminExists()) {
                setup();
            } else {

                String username, password;
                User loginUser;
                System.out.println("\nHey there! Please verify yourself.");
                System.out.println("Please enter your user credentials to log-in.\n");
                System.out.print("Username: ");
                username = scanner.nextLine();
                System.out.print("Password: ");
                password = scanner.nextLine();
                try {
                    loginUser = userController.find(username, password);
                    if (loginUser != null && loginUser.isAvailable()) {
                        switch (loginUser.getUserType().getValue()) {
                            case "Administrator" -> callAdmin(loginUser);
                            case "Purchase User" -> callPurchaseUser(loginUser);
                            case "Sales User" -> callSalesUser(loginUser);
                            default -> System.out.println("User not found, please provide a valid user.");
                        }
                    } else {
                        System.out.println("Invalid log-in credentials. Ensure whether username and password are correct.\n");
                    }
                } catch (InvalidArgumentException | SQLException exception) {
                    System.out.println("Sorry, Cannot login. " + exception.getMessage());
                }
            }
        }
    }

    private void setup() {
        System.out.println("No users present. For initial setup, please sign-up as an user.");
        UserCLI userCLI = new UserCLI();
        String command = scanner.nextLine();
        String[] splitCommand = command.trim().split("\\s+");
        String module = splitCommand[0].trim();
        String moduleCommand = null;
        List<String> splitBySpaces = main.splitBySpaces(command);
        moduleCommand = command.substring(command.indexOf(splitBySpaces.get(1)));
        if (module.equals("user")) {
            userCLI.execute(moduleCommand);
        } else {
            System.out.println("Invalid module named '" + module + "'. Please provide a valid command.");
        }
    }

    private void callAdmin(User loginUser) {
        System.out.println("\nWelcome, " + loginUser.getUsername() + "!");
        System.out.println("What would you prefer to do?");
        while (true) {
            System.out.print("\n>> ");
            String input = scanner.nextLine();
            if (input.equals("exit")) {
                System.out.println("Redirecting to login page..");
                break;
            } else {
                try {
                    router.admin(input);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    private void callPurchaseUser(User loginUser) {
        System.out.println("\nWelcome, " + loginUser.getUsername() + "!");
        System.out.println("What would you prefer to do?");
        while (true) {
            System.out.print(">> ");
            String input = scanner.nextLine();
            if (input.equals("exit")) {
                System.out.println("Redirecting to login page..");
                break;
            } else {
                router.purchaseUser(input);
            }
        }
    }

    private void callSalesUser(User loginUser) {
        System.out.println("\nWelcome, " + loginUser.getUsername() + "!");
        System.out.println("What would you prefer to do?");
        while (true) {
            System.out.print(">> ");
            String input = scanner.nextLine();
            if (input.equals("exit")) {
                System.out.println("Redirecting to login page..");
                break;
            } else {
                router.salesUser(input);
            }
        }
    }
}
