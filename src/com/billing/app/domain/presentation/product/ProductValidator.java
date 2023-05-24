package com.billing.app.domain.presentation.product;

import com.billing.app.domain.entity.Store;
import com.billing.app.domain.exceptions.InvalidArgumentException;
import com.billing.app.domain.exceptions.TemplateMismatchException;
import com.billing.app.domain.exceptions.TypeMismatchException;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ProductValidator {

    public boolean validateValues(List<String> values) throws InvalidArgumentException, TypeMismatchException {
        String code = values.get(0);
        String name = values.get(1);
        String unitCode = values.get(2);
        String type = values.get(3);
        if (code.length() < 2 || code.length() > 6) {
            throw new InvalidArgumentException("Product code: " + code + " is incompatible. Provide a product code of valid length.");
        }
        if (code.trim().length() == 0) {
            throw new InvalidArgumentException("Product code is mandatory. Please provide a valid product code.");
        }
        if (name.length() < 3 || name.length() > 30) {
            throw new InvalidArgumentException("Name: '" + name + "' is incompatible. Provide a product name of valid length.");
        }
        if (name.trim().length() == 0) {
            throw new InvalidArgumentException("Name cannot be empty. Please provide a valid product name.");
        }
        if (unitCode.trim().length() == 0) {
            throw new InvalidArgumentException("Unit code cannot be empty. Please provide a valid product unit code.");
        }
        if (type.trim().length() == 0) {
            throw new InvalidArgumentException("Type cannot be empty. Please provide a valid product type.");
        }
        try {
            String price = values.get(4);
            float castedPrice = Float.parseFloat(price);
            if (castedPrice < 0) {
                throw new InvalidArgumentException("Price cannot be negative.");
            }
        } catch (NumberFormatException exception) {
            throw new TypeMismatchException("'" + values.get(4) + "'. Provided input is incompatible.");
        }
        try {
            String stock = values.get(5);
            float castedStock = Float.parseFloat(stock);
            if (castedStock < 0) {
                throw new InvalidArgumentException("Stock cannot be negative.");
            }
        } catch (NumberFormatException exception) {
            throw new TypeMismatchException("'" + values.get(5) + "'. Provided input is incompatible.");
        }
        return true;
    }

    public String validateSQLState(SQLException exception) {
        String sqlState = exception.getSQLState();
        if (sqlState.equals("23505")) {
            return "Provided code already exists. \nPlease create a product of different product code.\n";
        }
        if (sqlState.equals("23503")) {
            return "Invalid unit provided, please ensure whether the unit is already created (or) present. \n";
        }
        if (sqlState.equals("42703")) {
            return "Provided 'attribute' column does not exist.\n" +
                    "Please provide a valid attribute for listing the products.";
        }
        return "Unrecognised SQL state error.";
    }


    public boolean validateMap(Map<String, String> productMap) throws InvalidArgumentException, TypeMismatchException {

        if (productMap.containsKey("id")) {
            String identifier = productMap.get("id");
            try {
                int id = Integer.parseInt(identifier);
                ;
                if (id < 0) {
                    throw new InvalidArgumentException("Id cannot be negative. Please provide a valid Id.");
                }
            } catch (NumberFormatException exception) {
                throw new TypeMismatchException("'" + identifier + "'. Provided input is incompatible.");
            }
        } else {
            throw new InvalidArgumentException("Missing Id field. Id not entered.");
        }
        if (productMap.containsKey("code")) {
            String code = productMap.get("code");
            if (code.length() < 2 || code.length() > 6) {
                throw new InvalidArgumentException("Code: " + code + " is incompatible. Provide a product code of valid length.");
            }
            if (code.trim().length() == 0) {
                throw new InvalidArgumentException("Code is mandatory. Please provide a valid product code.");
            }
        } else {
            throw new InvalidArgumentException("Missing code field. Code not entered.");
        }
        if (productMap.containsKey("name")) {
            String name = productMap.get("name");
            if (name.length() < 3 || name.length() > 30) {
                throw new InvalidArgumentException("Name: " + name + " is incompatible. Provide a product code of valid length.");
            }
            if (name.trim().length() == 0) {
                throw new InvalidArgumentException("Name cannot be empty. Please provide a valid product name.");
            }
        } else {
            throw new InvalidArgumentException("Missing name field. Name not entered.");
        }
        if (productMap.containsKey("unitcode")) {
            String unitCode = productMap.get("unitcode");
            if (unitCode.trim().length() == 0) {
                throw new InvalidArgumentException("Unit code cannot be empty. Please provide a valid product unit code.");
            }
        } else {
            throw new InvalidArgumentException("Missing unit code field. Unit code not entered.");
        }
        if (productMap.containsKey("type")) {
            String type = productMap.get("type");
            if (type.trim().length() == 0) {
                throw new InvalidArgumentException("Type cannot be empty. Please provide a valid product type.");
            }
        } else {
            throw new InvalidArgumentException("Missing type field. Type not entered.");
        }
        if (productMap.containsKey("price")) {
            try {
                String price = productMap.get("price");
                float castedPrice = Float.parseFloat(price);
                if (castedPrice < 0) {
                    throw new InvalidArgumentException("Price cannot be negative.");
                }
            } catch (NumberFormatException exception) {
                throw new TypeMismatchException("'" + productMap.get("price") + "'. Provided input is incompatible.");
            }
        } else {
            throw new InvalidArgumentException("Missing price field. Price not entered.");
        }
        if (productMap.containsKey("stock")) {
            try {
                String stock = productMap.get("stock");
                float castedStock = Float.parseFloat(stock);
                if (castedStock < 0) {
                    throw new InvalidArgumentException("Stock cannot be negative.");
                }
            } catch (NumberFormatException exception) {
                throw new TypeMismatchException("'" + productMap.get("stock") + "'. Provided input is incompatible.");
            }
        } else {
            throw new InvalidArgumentException("Missing stock field. Stock not entered.");
        }
        return true;
    }


    public Map<String, Object> validateProductList(List<String> values) throws InvalidArgumentException, TemplateMismatchException {
        Map<String, Object> parseMap = new HashMap<>();
        int range = 0, page = 0;
        String attribute = null, searchText = null;
        if (values.size() == 0) {
            range = 0;
            page = 0;
            attribute = null;
            searchText = null;
        } else if (values.size() == 2) {
            String notation = values.get(0);
            if (notation.equals("-p")) {
                range = Integer.parseInt(values.get(1));
            } else if (notation.equals("-s")) {
                searchText = values.get(1);
            } else {
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

}