package com.billing.app.domain.presentation;

import com.billing.app.domain.entity.Product;
import com.billing.app.domain.entity.Store;
import com.billing.app.domain.entity.Unit;
import com.billing.app.domain.entity.User;
import com.billing.app.domain.exceptions.IllegalArgumentException;
import com.billing.app.domain.exceptions.TemplateMismatchException;

import java.util.ArrayList;


public class Validator {

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


    public void productEditValidate(Product product, String key, String value) throws TemplateMismatchException {

        if (key.equals("name")) {
            product.setName(value);
        } else if (key.equals("code")) {
            product.setCode(value);
        } else if (key.equals("unitcode")) {
            product.setUnitCode(value);
        } else if (key.equals("type")) {
            product.setType(value);
        } else if (key.equals("price")) {
            product.setPrice(Float.parseFloat(value));
        } else if (key.equals("stock")) {
            product.setStock(Float.parseFloat(value));
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

    public boolean deleteValidate(String key) throws TemplateMismatchException {
        if (key.equals("code") || key.equals("id")) {
            return true;
        } else {
            throw new TemplateMismatchException("Invalid attribute '" + key + "'. Provide appropriate attribute for deletion.");
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


