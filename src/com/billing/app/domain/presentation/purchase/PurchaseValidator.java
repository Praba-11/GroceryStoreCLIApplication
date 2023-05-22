package com.billing.app.domain.presentation.purchase;

import com.billing.app.domain.exceptions.InvalidArgumentException;
import com.billing.app.domain.exceptions.TemplateMismatchException;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PurchaseValidator {
    public String validateSQLState(SQLException exception) {
        String sqlState = exception.getSQLState();
        if (sqlState.equals("23505"))
            return "Provided invoice ID already exists. \n" + exception.getMessage();
        if (sqlState.equals("23503")) {
            return "Invalid unit provided, please ensure whether the unit is already created (or) present. \n" + exception.getMessage();
        }
        if (sqlState.equals("42703")) {
            return "Provided 'attribute' column does not exist.";
        }
        if (sqlState.equals("22007")) {
            return "Invalid date provided. Please provide a valid date.";
        }
        return "Unrecognised SQL state error.";
    }

    public Map<String, Object> validateList(List<String> values) throws InvalidArgumentException, TemplateMismatchException {
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
            }
            else if (notation.equals("-s")) {
                attribute = values.get(1);
                searchText = values.get(2);
            }
            else {
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
            }
        } else {
            System.out.println(values);
            throw new TemplateMismatchException("Invalid number of parameters provided. Please provide with right attributes according to the template.");
        }

        parseMap.put("range", range);
        parseMap.put("page", page);
        parseMap.put("attribute", attribute);
        parseMap.put("searchtext", searchText);


        return parseMap;
    }
}
