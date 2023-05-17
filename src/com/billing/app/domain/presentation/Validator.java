package com.billing.app.domain.presentation;

import com.billing.app.domain.entity.Store;
import com.billing.app.domain.entity.Unit;
import com.billing.app.domain.entity.User;
import com.billing.app.domain.exceptions.IllegalArgumentException;
import com.billing.app.domain.exceptions.TemplateMismatchException;
import com.billing.app.domain.exceptions.TypeMismatchException;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Validator {

    public boolean validateValues(List<String> values) throws IllegalArgumentException, TypeMismatchException {
        String code = values.get(0);
        String name = values.get(1);
        String unitCode = values.get(2);
        String type = values.get(3);
        if (code.length() < 2 || code.length() > 6) {
            throw new IllegalArgumentException("Product code: " + code + " is incompatible. Provide a product code of valid length.");
        }
        if (code.trim().length() == 0) {
            throw new IllegalArgumentException("Product code is mandatory. Please provide a valid product code.");
        }
        if (name.length() < 3 || name.length() > 30) {
            throw new IllegalArgumentException("Product name: " + name + " is incompatible. Provide a product code of valid length.");
        }
        if (name.trim().length() == 0) {
            throw new IllegalArgumentException("Product name cannot be empty. Please provide a valid product name.");
        }
        if (unitCode.trim().length() == 0) {
            throw new IllegalArgumentException("Product unit code cannot be empty. Please provide a valid product unit code.");
        }
        if (type.trim().length() == 0) {
            throw new IllegalArgumentException("Product type cannot be empty. Please provide a valid product type.");
        }
        try {
            String price = values.get(4);
            float castedPrice = Float.parseFloat(price);
            if (castedPrice < 0) {
                throw new IllegalArgumentException("Product price cannot be negative.");
            }
        } catch (NumberFormatException exception) {
            throw new TypeMismatchException("'" + values.get(4) + "'. Provided input is incompatible.");
        }
        try {
            String stock = values.get(5);
            float castedStock = Float.parseFloat(stock);
            if (castedStock < 0) {
                throw new IllegalArgumentException("Product stock cannot be negative.");
            }
        } catch(NumberFormatException exception) {
            throw new TypeMismatchException("'" + values.get(5) + "'. Provided input is incompatible.");
        }
        return true;
    }

    public String validateSQLState(SQLException exception) {
        String sqlState = exception.getSQLState();
        if (sqlState.equals("23505"))
            return "Provided product code already exists. \n" + exception.getMessage();
        if (sqlState.equals("23503")) {
            return "Invalid unit provided, please ensure whether the unit is already created (or) present. \n" + exception.getMessage();
        } else {
            throw new RuntimeException(exception);
        }
    }


    public boolean validateMap(Map<String, String> productMap) throws IllegalArgumentException, TypeMismatchException {

        if (productMap.containsKey("id")) {
            String identifier = productMap.get("id");
            try {
                int id = Integer.parseInt(identifier);;
                if (id < 0) {
                    throw new IllegalArgumentException("Product id cannot be negative.");
                }
            } catch (NumberFormatException exception) {
                throw new TypeMismatchException("'" + identifier + "'. Provided input is incompatible.");
            }
        } else {
            throw new IllegalArgumentException("Product Id not entered.");
        }
        if (productMap.containsKey("code")) {
            String code = productMap.get("code");
            if (code.length() < 2 || code.length() > 6) {
                throw new IllegalArgumentException("Product code: " + code + " is incompatible. Provide a product code of valid length.");
            }
            if (code.trim().length() == 0) {
                throw new IllegalArgumentException("Product code is mandatory. Please provide a valid product code.");
            }
        } else {
            throw new IllegalArgumentException("Product code not entered.");
        }
        if (productMap.containsKey("name")) {
            String name = productMap.get("name");
            if (name.length() < 3 || name.length() > 30) {
                throw new IllegalArgumentException("Product name: " + name + " is incompatible. Provide a product code of valid length.");
            }
            if (name.trim().length() == 0) {
                throw new IllegalArgumentException("Product name cannot be empty. Please provide a valid product name.");
            }
        } else {
            throw new IllegalArgumentException("Product name not entered.");
        }
        if (productMap.containsKey("unitcode")) {
            String unitCode = productMap.get("unitcode");
            if (unitCode.trim().length() == 0) {
                throw new IllegalArgumentException("Product unit code cannot be empty. Please provide a valid product unit code.");
            }
        } else {
            throw new IllegalArgumentException("Product unit code not entered.");
        }
        if (productMap.containsKey("type")) {
            String type = productMap.get("type");
            if (type.trim().length() == 0) {
                throw new IllegalArgumentException("Product type cannot be empty. Please provide a valid product type.");
            }
        } else {
            throw new IllegalArgumentException("Product type not entered.");
        }
        if (productMap.containsKey("price")) {
            try {
                String price = productMap.get("price");
                float castedPrice = Float.parseFloat(price);
                if (castedPrice < 0) {
                    throw new IllegalArgumentException("Product price cannot be negative.");
                }
            } catch (NumberFormatException exception) {
                throw new TypeMismatchException("'" + productMap.get("price") + "'. Provided input is incompatible.");
            }
        } else {
            throw new IllegalArgumentException("Product price not entered.");
        }
        if (productMap.containsKey("stock")) {
            try {
                String stock = productMap.get("stock");
                float castedStock = Float.parseFloat(stock);
                if (castedStock < 0) {
                    throw new IllegalArgumentException("Product stock cannot be negative.");
                }
            } catch(NumberFormatException exception) {
                throw new TypeMismatchException("'" + productMap.get("stock") + "'. Provided input is incompatible.");
            }
        } else {
            throw new IllegalArgumentException("Product stock not entered.");
        }
        return true;
    }



    public Map<String, Object> validateProductList(List<String> values) throws IllegalArgumentException, TemplateMismatchException {
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
                throw new IllegalArgumentException("Invalid notation provided. Provide a valid list notation.");
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
                throw new IllegalArgumentException("Invalid notation provided. Provide a valid list notation.");
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
            throw new TemplateMismatchException("Invalid number of parameters provided. Please provide with right attributes according to the template.");
        }

        parseMap.put("range", range);
        parseMap.put("page", page);
        parseMap.put("attribute", attribute);
        parseMap.put("searchtext", searchText);


        return parseMap;
    }


//    --------------------------------------------------------------------------------------------------------------------

    public void unitEditValidate(Unit unit, String key, String value) throws TemplateMismatchException {

        if (key.equals("name")) {
            unit.setName(value);
        } else if (key.equals("code")) {
            unit.setCode(value);
        } else if (key.equals("description")) {
            unit.setDescription(value);
        } else if (key.equals("isdividable")) {
            unit.setDividable(Boolean.parseBoolean(value));
        } else {
            throw new TemplateMismatchException("Replace the string '" + key + "' with proper attribute string according to the template.");
        }
    }



    public void storeEditValidate(Store store, String key, String value) throws TemplateMismatchException {
        if (key.equals("gstnumber")) {
            store.setGstNumber(Long.parseLong(value));
        } else if (key.equals("name")) {
            store.setName(value);
        } else if (key.equals("phonenumber")) {
            store.setPhoneNumber(Long.parseLong(value));
        } else if (key.equals("address")) {
            store.setAddress(value);
        } else {
            throw new TemplateMismatchException("Replace the string '" + key + "' with proper attribute string according to the template.");
        }
    }

    public void userEditValidate(User user, String key, String value) throws TemplateMismatchException {
        if (key.equals("type") && (value.equals("admin") && value.equals("purchase") && value.equals("sales"))) {
            user.setType(value);
        } else if (key.equals("username") && (value.length() > 2 && value.length() < 30)) {
            user.setUsername(value);
        } else if (key.equals("password") && (value.length() > 8)) {
            user.setPassword(value);
        } else if (key.equals("firstname") && (value.length() > 2 && value.length() < 30)) {
            user.setFirstName(value);
        } else if (key.equals("lastname")) {
            user.setLastName(value);
        } else if (key.equals("phonenumber") && (value.length() == 10)) {
            user.setPhoneNumber(Long.parseLong(value));
        } else {
            throw new TemplateMismatchException("Replace the string '" + key + "' with proper attribute string according to the template.");
        }
    }




    public boolean userDeleteValidate(String key) throws TemplateMismatchException {
        if (key.equals("username") || key.equals("id")) {
            return true;
        } else {
            throw new TemplateMismatchException("Invalid attribute '" + key + "'. Provide appropriate attribute for deletion.");
        }
    }




    public boolean validateUserDetails(ArrayList<String> stringArrayList) throws IllegalArgumentException {
        if (stringArrayList.size() != 6) {
            throw new IllegalArgumentException("Incompatible user details provided. Make sure all details are provided with --help");
        } else {
            if (!stringArrayList.get(0).equals("admin") && !stringArrayList.get(0).equals("purchase") && !stringArrayList.get(0).equals("sales")) {
                throw new IllegalArgumentException("'" + stringArrayList.get(0) + "'. Invalid user type. Please provide valid user type.");
            }
            if (stringArrayList.get(1).length() < 3 || stringArrayList.get(1).length() > 30) {
                throw new IllegalArgumentException("Invalid constraint length provided. " + stringArrayList.get(1) + " exceeds required username constraint length.");
            }
            if (stringArrayList.get(2).length() < 8) {
                throw new IllegalArgumentException("Invalid constraint length provided. Please provide password with valid constraint length.");
            }
            if (stringArrayList.get(3).length() < 3 || stringArrayList.get(3).length() > 30) {
                throw new IllegalArgumentException("Invalid constraint length provided. " + stringArrayList.get(3) + " exceeds required firstname constraint length.");
            }
            if (stringArrayList.get(5).length() != 10) {
                throw new IllegalArgumentException("Invalid constraint length provided. " + stringArrayList.get(5) + " is incompatible phone number.");
            }
            return true;
        }
    }

}


