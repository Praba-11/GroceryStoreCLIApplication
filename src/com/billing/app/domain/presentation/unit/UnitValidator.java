package com.billing.app.domain.presentation.unit;

import com.billing.app.domain.exceptions.InvalidArgumentException;
import com.billing.app.domain.exceptions.TypeMismatchException;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class UnitValidator {
    public String validateSQLState(SQLException exception) {
        String sqlState = exception.getSQLState();
        if (sqlState.equals("23505")) {
            return "Provided unit code already exists. \n" + exception.getMessage();
        } else {
            throw new RuntimeException(exception);
        }
    }



    public boolean validateDetails(List<String> details) throws InvalidArgumentException {

        String name = details.get(0);
        String code = details.get(1);
        String isDividable = details.get(3);

        if (!isDividable.equals("true") && !isDividable.equals("false")) {
            throw new InvalidArgumentException("'isdividable' should only be true or false.");
        }
        if (code.length() < 1 || code.length() > 4) {
            throw new InvalidArgumentException("Unit code: " + code + " is incompatible. Provide a unit code of valid length.");
        }
        if (code.trim().length() == 0) {
            throw new InvalidArgumentException("Unit code is mandatory. Please provide a valid unit code.");
        }
        if (name.length() < 3 || name.length() > 30) {
            throw new InvalidArgumentException("Unit name: " + name + " is incompatible. Provide a unit code of valid length.");
        }
        if (name.trim().length() == 0) {
            throw new InvalidArgumentException("Unit name cannot be empty. Please provide a valid unit name.");
        }

        return true;
    }
    public boolean validateMap(Map<String, String> unitMap) throws InvalidArgumentException, TypeMismatchException {

        if (unitMap.containsKey("id")) {
            String identifier = unitMap.get("id");
            try {
                int id = Integer.parseInt(identifier);;
                if (id < 0) {
                    throw new InvalidArgumentException("Unit id cannot be negative.");
                }
            } catch (NumberFormatException exception) {
                throw new TypeMismatchException("'" + identifier + "'. Provided input is incompatible.");
            }
        } else {
            throw new InvalidArgumentException("Unit id not entered.");
        }
        if (unitMap.containsKey("code")) {
            String code = unitMap.get("code");
            if (code.length() < 1 || code.length() > 4) {
                throw new InvalidArgumentException("Unit code: " + code + " is incompatible. Provide a unit code of valid length.");
            }
            if (code.trim().length() == 0) {
                throw new InvalidArgumentException("Unit code is mandatory. Please provide a valid unit code.");
            }
        } else {
            throw new InvalidArgumentException("Unit code not entered.");
        }
        if (unitMap.containsKey("name")) {
            String name = unitMap.get("name");
            if (name.length() < 3 || name.length() > 30) {
                throw new InvalidArgumentException("Unit name: " + name + " is incompatible. Provide a unit code of valid length.");
            }
            if (name.trim().length() == 0) {
                throw new InvalidArgumentException("Unit 'name' cannot be empty. Please provide a valid unit name.");
            }
        } else {
            throw new InvalidArgumentException("Unit 'name' not entered.");
        }
        if (unitMap.containsKey("description")) {
            String unitCode = unitMap.get("description");
            if (unitCode.trim().length() == 0) {
                throw new InvalidArgumentException("Unit 'description' cannot be empty. Please provide a valid unit description.");
            }
        } else {
            throw new InvalidArgumentException("Unit 'description' not entered.");
        }
        if (unitMap.containsKey("isdividable")) {
            String isDividable = unitMap.get("isdividable");
            if (!isDividable.equals("true") && !isDividable.equals("false")) {
                throw new InvalidArgumentException("'isdividable' should only be true or false.");
            }
        } else {
            throw new InvalidArgumentException("Unit 'is dividable' not entered.");
        }
        return true;
    }
}
