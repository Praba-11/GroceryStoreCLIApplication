package com.billing.app.domain.service;

import com.billing.app.domain.entity.Product;
import com.billing.app.domain.database.CustomException;
import com.billing.app.domain.database.ProductDAO;

public class ProductValidator {
    ProductDAO productDAO;
    public boolean validate(Product product) throws CustomException {
        if (product == null) {
            throw new CustomException("Product cannot be null");
        }
        if (product.getCode() == null && (product.getCode().length() > 6 || product.getCode().length() < 2)) {
            throw new CustomException("Product code cannot be null or empty (or) Product code out of bound.");
        }
        if (product.getName() == null && (product.getName().length() > 30 || product.getName().length() < 2)) {
            throw new CustomException("Product name cannot be null or empty (or) Product name out of bound.");
        }
        if (product.getUnitCode() == null) {
            throw new CustomException("Product unit code cannot be null or empty.");
        }
        if (product.getType() == null) {
            throw new CustomException("Product type cannot be null or empty.");
        }
        if (product.getPrice() == 0) {
            throw new CustomException("Product price cannot be 0.");
        }
        if (product.isDeleted()) {
            throw new CustomException("Product isDeleted condition cannot be true.");
        }
        return true;
    }

}
