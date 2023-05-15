package com.billing.app.domain.service.product;

import com.billing.app.domain.entity.Product;
import com.billing.app.domain.exceptions.*;

public class ProductValidator {

    public boolean validate(Product product) throws ObjectNullPointerException {
        if(product == null) {
            throw new ObjectNullPointerException("Product cannot be null or empty.");
        }
        if (product.getCode().isEmpty() || product.getCode() == null) {
            throw new ObjectNullPointerException("Product code cannot be null (or) empty.");
        }
        if (product.getName().isEmpty() || product.getName() == null) {
            throw new ObjectNullPointerException("Product name cannot be null (or) empty.");
        }
        if (product.getUnitCode().isEmpty() || product.getUnitCode() == null) {
            throw new ObjectNullPointerException("Product unit code cannot be null (or) empty.");
        }
        if (product.getType().isEmpty() || product.getType() == null) {
            throw new ObjectNullPointerException("Product type cannot be null (or) empty.");
        }
        return true;
    }

}
