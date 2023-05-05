package com.billing.app.domain.presentation;

import com.billing.app.domain.entity.Product;
import com.billing.app.domain.entity.Unit;
import com.billing.app.domain.exceptions.unit.TemplateMismatchException;


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

    public boolean unitDeleteValidate(String key) throws TemplateMismatchException {
        if (key.equals("code") || key.equals("id")) {
            return true;
        } else {
            throw new TemplateMismatchException("Invalid attribute '" + key + "'. Provide appropriate attribute for deletion.");
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
}


