package com.billing.app.domain.service.product;

import com.billing.app.domain.database.ProductJdbcDAO;
import com.billing.app.domain.database.UnitJdbcDAO;
import com.billing.app.domain.entity.Product;
import com.billing.app.domain.database.ProductDAO;
import com.billing.app.domain.entity.Unit;
import com.billing.app.domain.exceptions.CodeNotFoundException;
import com.billing.app.domain.exceptions.CustomException;
import com.billing.app.domain.exceptions.ProductException;
import com.billing.app.domain.exceptions.ProductValidationException;

import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

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

    public Product editAttributes(Product productToBeEdited, Product modfiedProduct) throws NullPointerException {
        productToBeEdited.setName(modfiedProduct.getName());
        productToBeEdited.setCode(modfiedProduct.getCode());
        productToBeEdited.setUnitCode(modfiedProduct.getUnitCode());
        productToBeEdited.setPrice(modfiedProduct.getPrice());
        productToBeEdited.setStock(modfiedProduct.getStock());
        return productToBeEdited;
    }

    public boolean isDeletable(String key, String value) throws SQLException, ClassNotFoundException, CodeNotFoundException {
        productDAO = new ProductJdbcDAO();
        if (key.equals("code")) {
            return productDAO.isCodePresent(value);
        } else if (key.equals("id")) {
            return productDAO.isIdPresent(value);
        } else {
            throw new CodeNotFoundException("Provided attribute not found in relational table. '" + key + " doesn't contain '" + value + "'");
        }
    }
}
