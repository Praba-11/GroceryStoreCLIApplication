package com.billing.app.domain.presentation.unit;

import com.billing.app.domain.exceptions.IllegalArgumentException;
import com.billing.app.domain.exceptions.TypeMismatchException;

import java.sql.SQLException;
import java.util.List;

public class UnitValidator {
    public String validateSQLState(SQLException exception) {
        String sqlState = exception.getSQLState();
        if (sqlState.equals("23505")) {
            return "Provided unit code already exists. \n" + exception.getMessage();
        } else {
            throw new RuntimeException(exception);
        }
    }



    public boolean validateDetails(List<String> details) throws IllegalArgumentException, TypeMismatchException {

        String name = details.get(0);
        String code = details.get(1);

        try {
            boolean isDividable = Boolean.parseBoolean(details.get(3));
        } catch (NumberFormatException exception) {
            throw new TypeMismatchException("'" + details.get(3) + "'. Provided input is incompatible.");
        }


        if (code.length() < 1 || code.length() > 4) {
            throw new IllegalArgumentException("Unit code: " + code + " is incompatible. Provide a unit code of valid length.");
        }
        if (code.trim().length() == 0) {
            throw new IllegalArgumentException("Unit code is mandatory. Please provide a valid unit code.");
        }
        if (name.length() < 3 || name.length() > 30) {
            throw new IllegalArgumentException("Unit name: " + name + " is incompatible. Provide a unit code of valid length.");
        }
        if (name.trim().length() == 0) {
            throw new IllegalArgumentException("Unit name cannot be empty. Please provide a valid unit name.");
        }

        return true;
    }


    public boolean validateId(String key, String identifier) throws IllegalArgumentException, TypeMismatchException {
        try {
            int id = Integer.parseInt(identifier);;
            if (id < 0) {
                throw new IllegalArgumentException("Unit id cannot be negative.");
            }
        } catch (NumberFormatException exception) {
            throw new TypeMismatchException("'" + identifier + "'. Provided input is incompatible.");
        }
        if (key.trim().length() == 0) {
            throw new IllegalArgumentException(key + " cannot be empty.");
        }
        return true;
    }


    public boolean validateKeys(List<String> productKeys) throws IllegalArgumentException {

        String nameKey = productKeys.get(0);
        String codeKey = productKeys.get(1);
        String descriptionKey = productKeys.get(2);
        String isDividableKey = productKeys.get(3);

        if (codeKey.trim().length() == 0 || nameKey.trim().length() == 0 || descriptionKey.trim().length() == 0 || isDividableKey.trim().length() == 0) {
            throw new IllegalArgumentException("(key) cannot be empty.");
        }
        if (!codeKey.equals("code")) {
            throw new IllegalArgumentException("Invalid key provided. " + codeKey + " doesn't exist.");
        }
        if (!nameKey.equals("name")) {
            throw new IllegalArgumentException("Invalid key provided. " + nameKey + " doesn't exist.");
        }
        if (!descriptionKey.equals("description")) {
            throw new IllegalArgumentException("Invalid key provided. " + descriptionKey + " doesn't exist.");
        }
        if (!isDividableKey.equals("isdividable")) {
            throw new IllegalArgumentException("Invalid key provided. " + isDividableKey + " doesn't exist.");
        }
        return true;

    }
}
