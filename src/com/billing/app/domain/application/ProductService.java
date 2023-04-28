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
    public Product create(Product product) throws SQLException, CustomException, ClassNotFoundException {
        try {
            productDAO = new ProductJdbcDAO();
            if (validate(product)) {
                if (productDAO.create(product))
                    return productDAO.getProduct(product.getCode());
                else
                    throw new CustomException("Product creation unsuccessful.");
            }
            else {
                throw new CustomException("Product validation failed.");
            }
        }
        catch (Throwable exception) {
            throw new CustomException("Error while creating product in database. " + exception.getMessage());
        }
    }



    public Product edit(Product product, ArrayList<String> arrayList) throws SQLException, CustomException, ClassNotFoundException {
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
        catch (Throwable exception) {
            throw new CustomException("Error while editing product in database. Incompatible edit attributes (or) code not found." + exception.getMessage());
        }
    }



    public boolean delete(String code) throws SQLException, CustomException, ClassNotFoundException {
        try {
            productDAO = new ProductJdbcDAO();
            if (productDAO.getStock(code) == 0) {
                return productDAO.delete(code);
            } else {
                throw new CustomException("Product stock not zero.");
            }
        }
        catch (Throwable exception) {
            throw new CustomException("Error while deleting product in database. " + exception.getMessage());
        }
    }




    @Override
    public ArrayList<Product> list() throws Throwable {
        try {
            productDAO = new ProductJdbcDAO();
            ArrayList<Product> productArrayList = productDAO.list();
            if (productArrayList.isEmpty()) {
                return productArrayList;
            }
            else {
                throw new CustomException("Pagination (or) search text failed. Cannot return any list of products.");
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
                return productArrayList;
            }
            else {
                throw new CustomException("Pagination (or) search text failed. Cannot return any list of products.");
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
                return productArrayList;
            }
            else {
                throw new CustomException("Pagination (or) search text failed. Cannot return any list of products.");
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
                return productArrayList;
            }
            else {
                throw new CustomException("Pagination (or) search text failed. Cannot return any list of products.");
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
                return productArrayList;
            }
            else {
                throw new CustomException("Pagination (or) search text failed. Cannot return any list of products.");
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
                return productArrayList;
            }
            else {
                throw new CustomException("Pagination (or) search text failed. Cannot return any list of products.");
            }
        }
        catch (Throwable exception) {
            throw new CustomException("Error while listing the products from database. " + exception.getMessage());
        }
    }




    public boolean validate(Product product) throws CustomException {
        if (product == null) {
            throw new CustomException("Product cannot be null");
        }
        if (product.getCode() == null && product.getCode().length() > 6) {
            throw new CustomException("Product code cannot be null or empty (or) Product code out of bound.");
        }
        if (product.getName() == null) {
            throw new CustomException("Product name cannot be null or empty.");
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
