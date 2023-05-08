package com.billing.app.domain.presentation.user;

import com.billing.app.domain.entity.User;
import com.billing.app.domain.exceptions.AnonymousException;
import com.billing.app.domain.exceptions.CodeNotFoundException;
import com.billing.app.domain.exceptions.IllegalArgumentException;
import com.billing.app.domain.exceptions.PrimaryKeyException;
import com.billing.app.domain.exceptions.TemplateMismatchException;
import com.billing.app.domain.presentation.Validator;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class UserCLI {
    public void execute(ArrayList<String> arrayList) {
        Validator validator;
        String action = arrayList.get(1);
        Scanner scanner = new Scanner(System.in);
        UserController userController = new UserController();

        switch (action) {

            case "create":
                try {
                    User userCreated = null;
                    ArrayList<String> values = new ArrayList<>(arrayList.subList(2, arrayList.size()));
                    validator = new Validator();
                    if (validator.validateUserDetails(values)) {
                        userCreated = userController.create(values);
                        System.out.println(userCreated);
                    }
                } catch (PrimaryKeyException exception) {
                    System.out.println("Unable to create user. " + exception.getMessage());
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                } catch (AnonymousException exception) {
                    System.out.println("Unknown error occurred. " + exception.getMessage());;
                } catch (IllegalArgumentException exception) {
                    System.out.println("Unable to create user. " + exception.getMessage());;
                }
                break;

            case "edit":
                try {
                    User userEdited = null;
                    userEdited = userController.edit(arrayList);
                    System.out.println(userEdited);
                } catch (TemplateMismatchException exception) {
                    System.out.println("Template Mismatch. " + exception.getMessage());
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                } catch (AnonymousException exception) {
                    System.out.println("Unknown error occurred. " + exception.getMessage());
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
                break;

            case "delete":
                try {
                    boolean isDeleted = false;
                    isDeleted = userController.delete(arrayList);
                    System.out.println(isDeleted);
                } catch (TemplateMismatchException exception) {
                    System.out.println("Template mismatch. " + exception.getMessage());
                } catch (SQLException exception) {
                    throw new RuntimeException(exception);
                } catch (CodeNotFoundException exception) {
                    System.out.println("Provided Username (or) Id not found. " + exception.getMessage());
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
                break;

            case "list":
                try {
                    ArrayList<User> userArrayList = userController.list();
                    System.out.println("List returned successfully.");
                    for (User user : userArrayList) {
                        System.out.println("id: " + user.getId() + ", type: " + user.getType() + ", username: " + user.getUsername() + ", password: " + user.getPassword() + ", firstname: " + user.getFirstName() + ", lastname: " + user.getLastName() + ", phonenumber: " + user.getPhoneNumber() + ", isavailable: " + user.isAvailable());
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
        }
    }
}
