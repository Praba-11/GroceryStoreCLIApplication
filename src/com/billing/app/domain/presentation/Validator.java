package com.billing.app.domain.presentation;

import com.billing.app.domain.entity.Product;
import com.billing.app.domain.entity.Store;
import com.billing.app.domain.entity.Unit;
import com.billing.app.domain.entity.User;
import com.billing.app.domain.exceptions.IllegalArgumentException;
import com.billing.app.domain.exceptions.TemplateMismatchException;
import com.billing.app.domain.exceptions.TypeMismatchException;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class Validator {


    public boolean validateProductDetails(List<String> productDetails) throws IllegalArgumentException, TypeMismatchException {

        String code = productDetails.get(0);
        String name = productDetails.get(1);
        String unitCode = productDetails.get(2);
        String type = productDetails.get(3);

        try {
            float price = Float.parseFloat(productDetails.get(4));
            if (price < 0) {
                throw new IllegalArgumentException("Product price cannot be negative.");
            }
        } catch (NumberFormatException exception) {
            throw new TypeMismatchException("'" + productDetails.get(4) + "'. Provided input is incompatible.");
        }

        try {
            float stock = Float.parseFloat(productDetails.get(5));
            if (stock < 0) {
                throw new IllegalArgumentException("Product stock cannot be negative.");
            }
        } catch(NumberFormatException exception) {
            throw new TypeMismatchException("'" + productDetails.get(5) + "'. Provided input is incompatible.");
        }

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

        return true;
    }



    public String validateSQLState(SQLException exception) {
        String sqlState = exception.getSQLState();
        if (sqlState.equals("23505"))
            return "Provided product code already exists. \n" + exception.getMessage();
        if (sqlState.equals("23503")) {
            return "Provided unit not present in Unit relational table. \n" + exception.getMessage();
        } else {
            return "Unknown SQL Exception occurred.";
        }
    }


    public boolean validateProductKeys(List<String> productKeys) throws IllegalArgumentException {

        String codeKey = productKeys.get(0);
        String nameKey = productKeys.get(1);
        String unitCodeKey = productKeys.get(2);
        String typeKey = productKeys.get(3);
        String priceKey = productKeys.get(4);
        String stockKey = productKeys.get(5);

        if (codeKey.trim().length() == 0 || nameKey.trim().length() == 0 || unitCodeKey.trim().length() == 0 || typeKey.trim().length() == 0 || priceKey.trim().length() == 0 || stockKey.trim().length() == 0) {
            throw new IllegalArgumentException("(key) cannot be empty.");
        }
        if (!codeKey.equals("code")) {
            throw new IllegalArgumentException("Invalid key provided. " + codeKey + " doesn't exist.");
        }
        if (!nameKey.equals("name")) {
            throw new IllegalArgumentException("Invalid key provided. " + nameKey + " doesn't exist.");
        }
        if (!unitCodeKey.equals("unitcode")) {
            throw new IllegalArgumentException("Invalid key provided. " + unitCodeKey + " doesn't exist.");
        }
        if (!typeKey.equals("type")) {
            throw new IllegalArgumentException("Invalid key provided. " + typeKey + " doesn't exist.");
        }
        if (!priceKey.equals("price")) {
            throw new IllegalArgumentException("Invalid key provided. " + priceKey + " doesn't exist.");
        }
        if (!stockKey.equals("stock")) {
            throw new IllegalArgumentException("Invalid key provided. " + stockKey + " doesn't exist.");
        }
        return true;

    }


    public String validateDelete(String key) throws TemplateMismatchException {
        if (key.equals("-c")) {
            return "code";
        } else if (key.equals("-i")) {
            return "id";
        } else {
            throw new TemplateMismatchException("Invalid attribute '" + key + "'. Provide appropriate attribute for deletion.");
        }
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


