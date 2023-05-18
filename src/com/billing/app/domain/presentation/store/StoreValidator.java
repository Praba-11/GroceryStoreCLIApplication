package com.billing.app.domain.presentation.store;

import com.billing.app.domain.exceptions.IllegalArgumentException;
import com.billing.app.domain.exceptions.TypeMismatchException;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StoreValidator {
    public String validateSQLState(SQLException exception) {
        String sqlState = exception.getSQLState();
        if (sqlState.equals("23505")) {
            return "Provided unit code already exists. \n" + exception.getMessage();
        } else {
            throw new RuntimeException(exception);
        }
    }

    public boolean validateValues(List<String> values) throws IllegalArgumentException, TypeMismatchException {
        String name = values.get(0);
        String phoneNumber = values.get(1);
        String address = values.get(2);
        String gstNumber = values.get(3);


        if (name.length() < 3 || name.length() > 30) {
            throw new IllegalArgumentException("Name: " + name + " is incompatible. Provide a name of valid length.");
        }
        if (name.trim().length() == 0) {
            throw new IllegalArgumentException("Name cannot be empty. Please provide a valid name.");
        }
        if (address.trim().length() == 0) {
            throw new IllegalArgumentException("Address cannot be empty. Please provide a valid address.");
        }
        if (!isGSTNumberValid(gstNumber)) {
            throw new IllegalArgumentException("Invalid GST number. Please provide a valid number.");
        }
        try {
            long castedPhoneNumber = Long.parseLong(phoneNumber);
            if (castedPhoneNumber < 0) {
                throw new IllegalArgumentException("Invalid phone number. Please provide a valid phone number.");
            }
        } catch (NumberFormatException exception) {
            throw new TypeMismatchException("'" + values.get(1) + "'. Provided phone number is incompatible.");
        }
        return true;
    }

    public boolean validateMap(Map<String, String> productMap) throws IllegalArgumentException, TypeMismatchException {

        if (productMap.containsKey("name")) {
            String name = productMap.get("name");
            if (name.length() < 3 || name.length() > 30) {
                throw new IllegalArgumentException("Store name: " + name + " is incompatible. Provide a store name of valid length.");
            }
            if (name.trim().length() == 0) {
                throw new IllegalArgumentException("Store name cannot be empty. Please provide a valid store name.");
            }
        } else {
            throw new IllegalArgumentException("Store name not entered.");
        }
        if (productMap.containsKey("gstnumber")) {
            String gstNumber = productMap.get("gstnumber");
            if (gstNumber.trim().length() == 0) {
                throw new IllegalArgumentException("GST number cannot be empty. Please provide a valid GST number.");
            }
            if (!isGSTNumberValid(gstNumber)) {
                throw new IllegalArgumentException("Incompatible GST number. Please provide a valid GST number.");
            }
        } else {
            throw new IllegalArgumentException("GST number not entered.");
        }
        if (productMap.containsKey("phonenumber")) {
            try {
                String phoneNumber = productMap.get("phonenumber");
                if (phoneNumber.length() == 10) {
                    long castedPhoneNumber = Long.parseLong(phoneNumber);
                    if (castedPhoneNumber < 0) {
                        throw new IllegalArgumentException("Incompatible phone number. Please provide a valid number.");
                    }
                } else {
                    throw new IllegalArgumentException("Incompatible phone number. Please provide a valid number.");
                }
            } catch (NumberFormatException exception) {
                throw new TypeMismatchException("'" + productMap.get("phonenumber") + "'. Provided input is incompatible.");
            }
        } else {
            throw new IllegalArgumentException("Phone number not entered.");
        }
        if (productMap.containsKey("address")) {
            String address = productMap.get("address");
            if (address.trim().length() == 0) {
                throw new IllegalArgumentException("Address cannot be empty. Please provide a valid address.");
            }
        } else {
            throw new IllegalArgumentException("GST number not entered.");
        }
        return true;
    }

    private static boolean isGSTNumberValid(String gstNumber) {
        String regex = "\\d{2}[A-Z]{5}\\d{4}[A-Z]{1}[A-Z\\d]{3}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(gstNumber);
        return matcher.matches();
    }
}
