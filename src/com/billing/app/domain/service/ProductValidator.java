package com.billing.app.domain.service;

import com.billing.app.domain.entity.Product;
import com.billing.app.domain.database.ProductDAO;
import com.billing.app.domain.exceptions.CustomException;
import com.billing.app.domain.exceptions.ProductException;
import com.billing.app.domain.exceptions.ProductValidationException;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;

public class ProductValidator {
    ProductDAO productDAO;
    public boolean validate(Product product) throws ProductException {

        if (product == null) {
            throw new ProductValidationException("Product cannot be null");
        }
        if (product.getCode().isEmpty()) {
            throw new ProductValidationException("Product code cannot be empty.");
        }
        if (product.getCode().length() > 6 || product.getCode().length() < 2) {
            throw new ProductValidationException("Product code out of bound.");
        }
        if (product.getName().isEmpty()) {
            throw new ProductValidationException("Product name cannot be empty.");
        }
        if ((product.getName().length() > 30 || product.getName().length() < 2)) {
            throw new ProductValidationException("Product name out of bound.");
        }
        if (product.getUnitCode().isEmpty()) {
            throw new ProductValidationException("Product unit code cannot be empty.");
        }
        if (product.getType().isEmpty()) {
            throw new ProductValidationException("Product type cannot be empty.");
        }
        if (product.getPrice() == 0) {
            throw new ProductValidationException("Product price cannot be 0.");
        }
        if (product.isDeleted()) {
            throw new ProductValidationException("Product isDeleted condition cannot be true.");
        }
        return true;
    }

    public Product editByValidation(Product product, HashMap<String, String> map) throws IllegalAccessException {
        Field[] fields = product.getClass().getDeclaredFields();
        for (Field field : fields) {
            String fieldName = field.getName();
            String attributeValue = map.get(fieldName);
            if (attributeValue != null) {
                field.setAccessible(true);
                Class<?> fieldType = field.getType();
                if (fieldType.equals(String.class)) {
                    field.set(product, attributeValue);
                } else if (fieldType.equals(int.class) || fieldType.equals(Integer.class)) {
                    int intValue = Integer.parseInt(attributeValue);
                    field.set(product, intValue);
                } else if (fieldType.equals(float.class) || fieldType.equals(Float.class)) {
                    float floatValue = Float.parseFloat(attributeValue);
                    field.set(product, floatValue);
                } else if (fieldType.equals(double.class) || fieldType.equals(Double.class)) {
                    double doubleValue = Double.parseDouble(attributeValue);
                    field.set(product, doubleValue);
                } else if (fieldType.equals(boolean.class) || fieldType.equals(Boolean.class)) {
                    boolean booleanValue = Boolean.parseBoolean(attributeValue);
                    field.set(product, booleanValue);
                }
            }
        }
        return product;
    }
}
