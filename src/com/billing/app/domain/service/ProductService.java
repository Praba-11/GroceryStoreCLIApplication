package com.billing.app.domain.service;

import com.billing.app.domain.database.*;
import com.billing.app.domain.entity.Product;
import com.billing.app.domain.exceptions.*;
import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class ProductService implements ProductServiceInterface {
    private ProductDAO productDAO;

    public Product create(Product product) throws ProductException, ClassNotFoundException {
        productDAO = new ProductJdbcDAO();
        ProductValidator productValidator = new ProductValidator();
        if (productValidator.validate(product)) {
            if (productDAO.create(product)) {
                return productDAO.getProduct(product.getCode());
            }
            else {
                throw new ProductCreationException("Product creation unsuccessful.");
            }
        }
        else {
            throw new ProductValidationException("Product validation failed.");
        }
    }




    public Product edit(Product product, HashMap<String, String> map) throws ProductException, ClassNotFoundException, NoSuchFieldException, IllegalAccessException {
        productDAO = new ProductJdbcDAO();
        ProductValidator productValidator = new ProductValidator();
        Product editedProduct = productValidator.editByValidation(product, map);
        if (productDAO.isCodePresent(editedProduct.getCode())) {
            if (productDAO.edit(editedProduct)) {
                return productDAO.getProduct(editedProduct.getCode());
            }
            else {
                throw new ProductException("Product edit unsuccessful.");
            }
        }
        else {
            throw new CodeNotFoundException("Code not present in product relation table.");
        }
    }



    public boolean delete(String code) throws ClassNotFoundException, ProductException {
        try {
            productDAO = new ProductJdbcDAO();
            if (productDAO.isCodePresent(code)) {
                if (productDAO.getProduct(code).isDeleted()) {
                    throw new ProductException("Product has already been deleted.");
                }
                else {
                    if (productDAO.getStock(code) == 0) {
                        return productDAO.delete(code);
                    }
                    else {
                        throw new ProductException("Product stock not zero.");
                    }
                }
            }
            else  {
                throw new CodeNotFoundException("Code not present in product relation table.");
            }
        }
        catch (ProductException exception) {
            throw new ProductException(exception.getMessage());
        }
    }




    @Override
    public ArrayList<Product> list() throws ProductException, ClassNotFoundException {
        try {
            productDAO = new ProductJdbcDAO();
            ArrayList<Product> productArrayList = productDAO.list();
            if (productArrayList.isEmpty()) {
                throw new NullPageException("Pagination (or) search text failed. Cannot return any list of products.");
            }
            else {
                return productArrayList;
            }
        }
        catch (Throwable exception) {
            throw new ProductException(exception.getMessage());
        }
    }




    @Override
    public ArrayList<Product> list(int range) throws ProductException, ClassNotFoundException {
        try {
            productDAO = new ProductJdbcDAO();
            ArrayList<Product> productArrayList = productDAO.list(range);
            if (productArrayList.isEmpty()) {
                throw new NullPageException("Pagination (or) search text failed. Cannot return any list of products.");
            }
            else {
                return productArrayList;
            }
        }
        catch (Throwable exception) {
            throw new ProductException(exception.getMessage());
        }
    }




    @Override
    public ArrayList<Product> list(int range, int page) throws ProductException, ClassNotFoundException {
        try {
            productDAO = new ProductJdbcDAO();
            ArrayList<Product> productArrayList = productDAO.list(range, page);
            if (productArrayList.isEmpty()) {
                throw new NullPageException("Pagination (or) search text failed. Cannot return any list of products.");
            }
            else {
                return productArrayList;
            }
        }
        catch (Throwable exception) {
            throw new ProductException(exception.getMessage());
        }
    }




    @Override
    public ArrayList<Product> list(String searchText) throws ProductException, ClassNotFoundException {
        try {
            productDAO = new ProductJdbcDAO();
            ArrayList<Product> productArrayList = productDAO.list(searchText);
            if (productArrayList.isEmpty()) {
                throw new NullPageException("Pagination (or) search text failed. Cannot return any list of products.");
            }
            else {
                return productArrayList;
            }
        }
        catch (Throwable exception) {
            throw new ProductException(exception.getMessage());
        }
    }




    @Override
    public ArrayList<Product> list(String attribute, String searchText) throws ProductException, ClassNotFoundException {
        try {
            productDAO = new ProductJdbcDAO();
            ArrayList<Product> productArrayList = productDAO.list(attribute, searchText);
            if (productArrayList.isEmpty()) {
                throw new NullPageException("Pagination (or) search text failed. Cannot return any list of products.");
            }
            else {
                return productArrayList;
            }
        }
        catch (Throwable exception) {
            throw new ProductException(exception.getMessage());
        }
    }




    @Override
    public ArrayList<Product> list(String attribute, String searchText, int range, int page) throws ProductException, ClassNotFoundException {
        try {
            productDAO = new ProductJdbcDAO();
            ArrayList<Product> productArrayList = productDAO.list(attribute, searchText, range, page);
            if (productArrayList.isEmpty()) {
                throw new NullPageException("Pagination (or) search text failed. Cannot return any list of products.");
            }
            else {
                return productArrayList;
            }
        }
        catch (Throwable exception) {
            throw new ProductException(exception.getMessage());
        }
    }

}
