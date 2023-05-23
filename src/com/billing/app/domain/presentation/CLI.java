package com.billing.app.domain.presentation;

import com.billing.app.domain.entity.User;
import com.billing.app.domain.exceptions.InvalidArgumentException;
import com.billing.app.domain.presentation.user.UserController;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CLI {
    public void execute() {
        UserController userController = new UserController();
        Router router = new Router();
        Scanner scanner = new Scanner(System.in);

        System.out.println("Hey there! Please verify yourself.");
        String username;
        String password;
        User loginUser;
        while (true) {
            System.out.println("Please enter your user credentials to log-in.");
            System.out.print("Username: ");
            username = scanner.nextLine();
            System.out.print("Password: ");
            password = scanner.nextLine();
            try {
                loginUser = userController.find(username, password);
                if (loginUser != null) {
                    if (loginUser.getUserType().getValue().equals("Administrator") && loginUser.isAvailable()) {
                        System.out.println("Welcome, " + loginUser.getUsername() + "!");
                        System.out.println("What would you prefer to do?");
                        while (true) {
                            System.out.print(">> ");
                            String input = scanner.nextLine();
                            if (input.equals("exit")) {
                                System.out.println("Redirecting to login page..");
                                break;
                            } else {
                                router.admin(input);
                            }
                        }
                    } else if (loginUser.getUserType().getValue().equals("Purchase User") && loginUser.isAvailable()) {
                        System.out.println("Welcome, " + loginUser.getUsername() + "!");
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
                    } else if (loginUser.getUserType().getValue().equals("Sales User") && loginUser.isAvailable()) {
                        System.out.println("Welcome, " + loginUser.getUsername() + "!");
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
                    } else {
                        System.out.println("User not found, please provide a valid user.");
                    }
                } else {
                    System.out.println("Invalid log-in credentials. Ensure whether username and password are correct.");
                }
            } catch (InvalidArgumentException | SQLException exception) {
                System.out.println("Sorry, Cannot login. " + exception.getMessage());
            }
        }
    }
}
