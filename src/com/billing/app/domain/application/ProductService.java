package com.billing.app.domain.application;

import com.billing.app.domain.entity.Product;
import com.billing.app.domain.repository.CustomException;
import com.billing.app.domain.repository.ProductDAO;
import com.billing.app.domain.repository.ProductJdbcDAO;


import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.ArrayList;

public class ProductService implements ProductServiceInterface {
    private ProductDAO productDAO;
    private ProductValidator productValidator;
    public Product create(Product product) throws Throwable {
        try {
            productDAO = new ProductJdbcDAO();
            productValidator = new ProductValidator();
            if (productValidator.validate(product)) {
                if (productDAO.create(product))
                    return productDAO.getProduct(product.getCode());
                else
                    throw new CustomException("Product creation unsuccessful.");
            }
            else {
                throw new CustomException("Product validation failed.");
            }
        }
        catch (SQLException exception) {
            if (exception.getSQLState().equals("23505")) {
                throw new CustomException("Primary key cannot be modified." + exception.getMessage());
            }
            else if (exception.getSQLState().equals("23502")) {
                throw new CustomException("Provided constraint cannot be null in relational table." + exception.getMessage());
            }
            else if (exception.getSQLState().equals("23503")) {
                throw new CustomException("Provided unit not present in Unit relation table." + exception.getMessage());
            }
            else {
                throw new CustomException(exception.getMessage());
            }
        }
    }



    public Product edit(Product product, ArrayList<String> arrayList) throws Throwable {
        try {
            productDAO = new ProductJdbcDAO();
            for (int index = 0; index < arrayList.size(); index+=2) {
                String name = arrayList.get(index);
                Object values = arrayList.get(index+1);
                Field field = Product.class.getDeclaredField(name);
                field.setAccessible(true);
                field.set(product, values);
            }
            if (productDAO.edit(product))
                return productDAO.getProduct(product.getCode());
            else
                throw new CustomException("Product edit unsuccessful.");
        }
        catch (SQLException | CustomException | ClassNotFoundException | NoSuchFieldException | IllegalAccessException exception) {
            throw new CustomException("Incompatible edit attributes (or) code not found." + exception.getMessage());
        }
    }



    public boolean delete(String code) throws SQLException, CustomException, ClassNotFoundException {
        try {
            productDAO = new ProductJdbcDAO();
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
                throw new CustomException("Pagination (or) search text failed. Cannot return any list of products.");
            }
            else {
                return productArrayList;
            }
        }
        catch (Throwable exception) {
            throw new CustomException("Error while listing the products from database. " + exception.getMessage());
        }
    }




    @Override
    public ArrayList<Product> list(int range) throws Throwable {
        try {
            productDAO = new ProductJdbcDAO();
            ArrayList<Product> productArrayList = productDAO.list(range);
            if (productArrayList.isEmpty()) {
                throw new CustomException("Pagination (or) search text failed. Cannot return any list of products.");
            }
            else {
                return productArrayList;
            }
        }
        catch (Throwable exception) {
            throw new CustomException("Error while listing the products from database. " + exception.getMessage());
        }
    }




    @Override
    public ArrayList<Product> list(int range, int page) throws Throwable {
        try {
            productDAO = new ProductJdbcDAO();
            ArrayList<Product> productArrayList = productDAO.list(range, page);
            if (productArrayList.isEmpty()) {
                throw new CustomException("Pagination (or) search text failed. Cannot return any list of products.");
            }
            else {
                return productArrayList;
            }
        }
        catch (Throwable exception) {
            throw new CustomException("Error while listing the products from database. " + exception.getMessage());
        }
    }




    @Override
    public ArrayList<Product> list(String searchText) throws Throwable {
        try {
            productDAO = new ProductJdbcDAO();
            ArrayList<Product> productArrayList = productDAO.list(searchText);
            if (productArrayList.isEmpty()) {
                throw new CustomException("Pagination (or) search text failed. Cannot return any list of products.");
            }
            else {
                return productArrayList;
            }
        }
        catch (Throwable exception) {
            throw new CustomException("Error while listing the products from database. " + exception.getMessage());
        }
    }




    @Override
    public ArrayList<Product> list(String attribute, String searchText) throws Throwable {
        try {
            productDAO = new ProductJdbcDAO();
            ArrayList<Product> productArrayList = productDAO.list(attribute, searchText);
            if (productArrayList.isEmpty()) {
                throw new CustomException("Pagination (or) search text failed. Cannot return any list of products.");
            }
            else {
                return productArrayList;
            }
        }
        catch (Throwable exception) {
            throw new CustomException("Error while listing the products from database. " + exception.getMessage());
        }
    }




    @Override
    public ArrayList<Product> list(String attribute, String searchText, int range, int page) throws Throwable {
        try {
            productDAO = new ProductJdbcDAO();
            ArrayList<Product> productArrayList = productDAO.list(attribute, searchText, range, page);
            if (productArrayList.isEmpty()) {
                throw new CustomException("Pagination (or) search text failed. Cannot return any list of products.");
            }
            else {
                return productArrayList;
            }
        }
        catch (Throwable exception) {
            throw new CustomException("Error while listing the products from database. " + exception.getMessage());
        }
    }

}
