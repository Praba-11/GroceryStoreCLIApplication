package com.billing.app.domain.service.product;

import com.billing.app.domain.database.*;
import com.billing.app.domain.entity.Product;
import com.billing.app.domain.exceptions.*;

import java.sql.SQLException;
import java.util.ArrayList;

public class ProductService implements ProductServiceInterface {
    private ProductDAO productDAO;
    private ProductValidator productValidator;

    public Product create(Product product) throws ClassNotFoundException, SQLException, ProductException {
        productDAO = new ProductJdbcDAO();
        if (productDAO.create(product)) {
            return productDAO.getProductByCode(product.getCode());
        }
        return null;
    }




    public Product edit(Product modifiedProduct) throws ClassNotFoundException, NoSuchFieldException, IllegalAccessException, CustomException, SQLException, ProductException {
        productDAO = new ProductJdbcDAO();
        Product productToBeEdited = productDAO.getProductByCode(modifiedProduct.getCode());
        productValidator = new ProductValidator();
        if (productToBeEdited != null) {
            productValidator.editAttributes(productToBeEdited, modifiedProduct);
            if (productDAO.edit(productToBeEdited)) {
                return productDAO.getProductByCode(modifiedProduct.getCode());
            }
        } else {
            return null;
        }
        return null;
    }



    public boolean delete(String key, String value) throws ProductException, SQLException, ClassNotFoundException, CodeNotFoundException {
        boolean flag = false;
        productDAO = new ProductJdbcDAO();
        productValidator = new ProductValidator();
        if (productValidator.isDeletable(key, value)) {
            flag = productDAO.delete(key, value);
        }
        return flag;
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
