package com.billing.app.domain.service;

import com.billing.app.domain.database.*;
import com.billing.app.domain.entity.Product;
import com.billing.app.domain.exceptions.*;


import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.ArrayList;

public class ProductService implements ProductServiceInterface {
    private ProductDAO productDAO;

    public Product create(Product product) throws Throwable {
        productDAO = new ProductJdbcDAO();
        ProductValidator productValidator = new ProductValidator();
        if (productValidator.validate(product)) {
            if (productDAO.create(product)) {
                return productDAO.getProduct(product.getCode());
            } else {
                throw new ProductCreationException("Product creation unsuccessful.");
            }
        } else {
            throw new ProductValidationException("Product validation failed.");
        }
    }






    public Product edit(Product product, ArrayList<String> arrayList) throws Throwable {
        productDAO = new ProductJdbcDAO();
        for (int index = 0; index < arrayList.size(); index+=2) {
            String name = arrayList.get(index);
            Object values = arrayList.get(index+1);
            Field field = Product.class.getDeclaredField(name);
            field.setAccessible(true);
            field.set(product, values);
        }
        if (productDAO.isCodePresent(product.getCode())) {
            if (productDAO.edit(product)) {
                return productDAO.getProduct(product.getCode());
            }
            else {
                throw new ProductException("Product edit unsuccessful.");
            }
        }
        else {
            throw new CodeNotFoundException("Code not present in product relation table.");
        }
    }



    public boolean delete(String code) throws SQLException, CustomException, ClassNotFoundException {
        try {
            productDAO = new ProductJdbcDAO();
            if (productDAO.isCodePresent(code)) {
                if (productDAO.getProduct(code).isDeleted()) {
                    throw new CustomException("Product has already been deleted.");
                }
                else {
                    if (productDAO.getStock(code) == 0) {
                        return productDAO.delete(code);
                    }
                    else {
                        throw new CustomException("Product stock not zero.");
                    }
                }
            }
            else  {
                throw new CodeNotFoundException("Code not present in product relation table.");
            }
        }
        catch (Throwable exception) {
            throw new CustomException(exception.getMessage());
        }
    }




    @Override
    public ArrayList<Product> list() throws Throwable {
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
            throw new CustomException(exception.getMessage());
        }
    }




    @Override
    public ArrayList<Product> list(int range) throws Throwable {
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
            throw new CustomException(exception.getMessage());
        }
    }




    @Override
    public ArrayList<Product> list(int range, int page) throws Throwable {
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
            throw new CustomException(exception.getMessage());
        }
    }




    @Override
    public ArrayList<Product> list(String searchText) throws Throwable {
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
            throw new CustomException(exception.getMessage());
        }
    }




    @Override
    public ArrayList<Product> list(String attribute, String searchText) throws Throwable {
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
            throw new CustomException(exception.getMessage());
        }
    }




    @Override
    public ArrayList<Product> list(String attribute, String searchText, int range, int page) throws Throwable {
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
            throw new CustomException(exception.getMessage());
        }
    }

}
