package com.billing.app.domain.presentation.user;

import com.billing.app.domain.entity.User;
import com.billing.app.domain.exceptions.InvalidArgumentException;
import com.billing.app.domain.exceptions.TemplateMismatchException;
import com.billing.app.domain.exceptions.TypeMismatchException;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserValidator {
    private static final String PASSWORD_PATTERN = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=!])(?=.*[a-zA-Z" +
            "\\d@#$%^&+=!]).{8,}$";

    public boolean validateValues(List<String> values) throws InvalidArgumentException, TypeMismatchException {

        String userType = values.get(0);
        String userName = values.get(1);
        String password = values.get(2);
        String firstName = values.get(3);
        String lastName = values.get(4);
        String phoneNumber = values.get(5);

        if (!isValidUserType(userType)) {
            throw new InvalidArgumentException("Incompatible user type. Please provide a valid user type.");
        }
        if (userName.length() < 3 || userName.length() > 30) {
            throw new InvalidArgumentException("User name: " + userName + " is incompatible. Provide a user name of valid length.");
        }
        if (userName.trim().length() == 0) {
            throw new InvalidArgumentException("User name cannot be empty. Please provide a valid product name.");
        }
        if (password.trim().length() == 0) {
            throw new InvalidArgumentException("Password cannot be empty. Please provide a valid password.");
        }
        if (!isValidPassword(password)) {
            throw new InvalidArgumentException("Invalid password. Please try again.");
        }
        if (firstName.length() < 3 || firstName.length() > 30) {
            throw new InvalidArgumentException("First name: " + firstName + " is incompatible. Provide a user name of valid length.");
        }
        if (firstName.trim().length() == 0) {
            throw new InvalidArgumentException("First name cannot be empty. Please provide a valid product name.");
        }
        if (lastName.length() < 3 || lastName.length() > 30) {
            throw new InvalidArgumentException("Last name: " + lastName + " is incompatible. Provide a user name of valid length.");
        }
        if (lastName.trim().length() == 0) {
            throw new InvalidArgumentException("Last name cannot be empty. Please provide a valid product name.");
        }
        try {
            if (phoneNumber.length() == 10) {
                long castedPhoneNumber = Long.parseLong(phoneNumber);
                if (castedPhoneNumber < 0) {
                    throw new InvalidArgumentException("Incompatible phone number. Please provide a valid phone number.");
                }
            } else {
                throw new InvalidArgumentException("Incompatible phone number. Please provide a valid phone number.");
            }

        } catch (NumberFormatException exception) {
            throw new TypeMismatchException("'" + phoneNumber + "'. Provided input is incompatible.");
        }
        return true;
    }



    public boolean validateMap(Map<String, String> userMap) throws InvalidArgumentException, TypeMismatchException {

        if (userMap.containsKey("id")) {
            String identifier = userMap.get("id");
            try {
                int id = Integer.parseInt(identifier);;
                if (id < 0) {
                    throw new InvalidArgumentException("User id cannot be negative.");
                }
            } catch (NumberFormatException exception) {
                throw new TypeMismatchException("'" + identifier + "'. Provided input is incompatible.");
            }
        } else {
            throw new InvalidArgumentException("User id not entered.");
        }
        if (userMap.containsKey("type")) {
            String type = userMap.get("type");
            if (!isValidUserType(type)) {
                throw new InvalidArgumentException("Incompatible user type. Please provide a valid user type.");
            }
        } else {
            throw new InvalidArgumentException("User type not entered.");
        }
        if (userMap.containsKey("username")) {
            String userName = userMap.get("username");
            if (!isValidUsername(userName)) {
                throw new InvalidArgumentException("User name: " + userName + " is incompatible. Provide a username of valid length.");
            }
        } else {
            throw new InvalidArgumentException("User name not entered.");
        }
        if (userMap.containsKey("password")) {
            String password = userMap.get("password");
            if (!isValidPassword(password)) {
                throw new InvalidArgumentException("Invalid password. Please try again.");
            }
        } else {
            throw new InvalidArgumentException("Password not entered. Please try again.");
        }
        if (userMap.containsKey("firstname")) {
            String firstName = userMap.get("firstname");
            if (firstName.length() < 3 || firstName.length() > 30) {
                throw new InvalidArgumentException("First name: " + firstName + " is incompatible. Provide a product code of valid length.");
            }
            if (firstName.trim().length() == 0) {
                throw new InvalidArgumentException("First name cannot be empty. Please provide a valid product name.");
            }
        } else {
            throw new InvalidArgumentException("First name not entered.");
        }
        if (userMap.containsKey("lastname")) {
            String lastName = userMap.get("lastname");
            if (lastName.length() < 3 || lastName.length() > 30) {
                throw new InvalidArgumentException("Last name: " + lastName + " is incompatible. Provide a product code of valid length.");
            }
            if (lastName.trim().length() == 0) {
                throw new InvalidArgumentException("Last name cannot be empty. Please provide a valid product name.");
            }
        } else {
            throw new InvalidArgumentException("Last name not entered.");
        }
        if (userMap.containsKey("phonenumber")) {
            try {
                String phoneNumber = userMap.get("phonenumber");
                float castedPhoneNumber = Float.parseFloat(phoneNumber);
                if (castedPhoneNumber < 0) {
                    throw new InvalidArgumentException("Incompatible phone number. Please provide a valid phone number.");
                }
            } catch (NumberFormatException exception) {
                throw new InvalidArgumentException("Incompatible phone number. Please provide a valid phone number.");
            }
        } else {
            throw new InvalidArgumentException("Phone number not entered.");
        }

        return true;
    }


    public Map<String, Object> validateUserList(List<String> values) throws InvalidArgumentException,
            TemplateMismatchException {

        Map<String, Object> parseMap = new HashMap<>();
        int range = 0, page = 0;
        String attribute = null, searchText = null;
        if (values.size() == 0) {
            range = 0; page = 0;
            attribute = null; searchText = null;
        } else if (values.size() == 2) {
            String notation = values.get(0);
            if (notation.equals("-p")) {
                range = Integer.parseInt(values.get(1));
            }
            else if (notation.equals("-s")) {
                searchText = values.get(1);
            }
            else {
                throw new InvalidArgumentException("Invalid notation provided. Provide a valid list notation.");
            }

        } else if (values.size() == 3) {
            System.out.println(values);
            String notation = values.get(0);
            if (notation.equals("-p")) {
                range = Integer.parseInt(values.get(1));
                page = Integer.parseInt(values.get(2));
            } else if (notation.equals("-s")) {
                attribute = values.get(1);
                searchText = values.get(2);
            } else {
                throw new InvalidArgumentException("Invalid notation provided. Provide a valid list notation.");
            }

        } else if (values.size() == 6) {
            String firstNotation = values.get(0);
            String secondNotation = values.get(3);
            if (firstNotation.equals("-s") && secondNotation.equals("-p")) {
                range = Integer.parseInt(values.get(4));
                page = Integer.parseInt(values.get(5));
                attribute = values.get(1);
                searchText = values.get(2);
            } else {
                throw new InvalidArgumentException("Invalid notation provided. Provide a valid list notation.");
            }
        } else {
            throw new TemplateMismatchException("Invalid number of parameters provided. Please provide with right attributes according to the template.");
        }

        parseMap.put("range", range);
        parseMap.put("page", page);
        parseMap.put("attribute", attribute);
        parseMap.put("searchtext", searchText);


        return parseMap;
    }

    private boolean isValidUsername(String userName) {
        if ((userName.length() < 3 || userName.length() > 30) && userName.trim().length() == 0) {
            return false;
        }
        return true;
    }

    public String validateSQLState(SQLException exception) {
        String sqlState = exception.getSQLState();
        if (sqlState.equals("23505"))
            return "Provided product code already exists. \n" + exception.getMessage();
        if (sqlState.equals("23503")) {
            return "Invalid unit provided, please ensure whether the unit is already created (or) present. \n" + exception.getMessage();
        }
        if (sqlState.equals("42703")) {
            return "Provided 'attribute' column does not exist.";
        }
        return "Unrecognised SQL state error.";
    }

    public static boolean isValidPassword(String password) {
        Pattern pattern = Pattern.compile(PASSWORD_PATTERN);
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }

    public static boolean isValidUserType(String userType) {
        for (User.UserType value : User.UserType.values()) {
            if (value.toString().equals(userType.toUpperCase())) {
                return true;
            }
        }
        return false;
    }

    public boolean validateLogin(String username, String password) throws InvalidArgumentException {
        if (isValidUsername(username) && isValidPassword(password)) {
            return true;
        }
        throw new InvalidArgumentException("Invalid username and password. Please try again.");
    }
}
