package com.billing.app.domain.service.product;

import com.billing.app.domain.database.*;
import com.billing.app.domain.entity.Product;
import com.billing.app.domain.exceptions.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductService implements ProductServiceInterface {
    private ProductDAO productDAO = new ProductJdbcDAO();
    private ProductValidator productValidator;

    public Product create(Product product) throws ClassNotFoundException, SQLException, ObjectNullPointerException {
        try {
            productValidator = new ProductValidator();
            productValidator.validate(product);
            return productDAO.create(product);
        } catch (ObjectNullPointerException exception) {
            throw new ObjectNullPointerException("Error while creating product: " + exception.getMessage());
        }
    }


    public Product edit(Product product) throws ClassNotFoundException, SQLException, ObjectNullPointerException, CodeNotFoundException {

        try {
            productValidator = new ProductValidator();
            productValidator.validate(product);
            if (productDAO.find(product.getCode()) != null) {
                return productDAO.edit(product);
            }
            throw new CodeNotFoundException("Provided product code not present in product relation table.");
        } catch (ObjectNullPointerException exception) {
            throw new ObjectNullPointerException("Error while editing product: " + exception.getMessage());
        }
    }


    public boolean delete(String key, String value) throws SQLException, ClassNotFoundException, CodeNotFoundException {
        boolean isDeleted;
        isDeleted = productDAO.delete(key, value);
        if (!isDeleted) {
            throw new CodeNotFoundException("(" + key  + ": " + value + ") not present in product relational table.");
        }
        return true;
    }

    public List<Product> list(int range, int page, String attribute, String searchText) throws SQLException, ClassNotFoundException {
        List<Product> list = productDAO.list(range, page, attribute, searchText);
        System.out.println(list);
        return list;
    }
}
