package com.billing.app.domain.service.product;

import com.billing.app.domain.database.ProductJdbcDAO;
import com.billing.app.domain.database.UnitJdbcDAO;
import com.billing.app.domain.entity.Product;
import com.billing.app.domain.database.ProductDAO;
import com.billing.app.domain.entity.Unit;
import com.billing.app.domain.exceptions.*;

import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class ProductValidator {

    public boolean validate(Product product) throws ObjectNullPointerException {
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
