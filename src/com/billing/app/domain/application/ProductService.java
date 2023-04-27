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
    public Product create(Product product) throws Throwable {
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



    public Product edit(Product product, ArrayList arrayList) throws Throwable {
        productDAO = new ProductJdbcDAO();
        for (int index = 0; index < arrayList.size(); index+=2) {
            String name = arrayList.get(index).toString();
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



    public boolean delete(String code) throws SQLException, CustomException, ClassNotFoundException {
        productDAO = new ProductJdbcDAO();
        if (productDAO.getStock(code) != 0) {
            return productDAO.delete(code);
        } else {
            throw new CustomException("Product stock not zero.");
        }
    }




    @Override
    public ArrayList<Product> list() throws Throwable {
        productDAO = new ProductJdbcDAO();
        return productDAO.list();
    }




    @Override
    public ArrayList<Product> list(int range) throws Throwable {
        productDAO = new ProductJdbcDAO();
        return productDAO.list(range);
    }




    @Override
    public ArrayList<Product> list(int range, int page) throws Throwable {
        productDAO = new ProductJdbcDAO();
        return productDAO.list(range, page);
    }




    @Override
    public ArrayList<Product> list(String searchText) throws Throwable {
        productDAO = new ProductJdbcDAO();
        return productDAO.list(searchText);
    }




    @Override
    public ArrayList<Product> list(String attribute, String searchText) throws Throwable {
        productDAO = new ProductJdbcDAO();
        return productDAO.list(attribute, searchText);
    }




    @Override
    public ArrayList<Product> list(String attribute, String searchText, int range, int page) throws Throwable {
        productDAO = new ProductJdbcDAO();
        return productDAO.list(attribute, searchText, range, page);
    }




    public boolean validate(Product product) throws CustomException {
        if (product.getCode() == null && product.getCode().length() > 6) {
            throw new CustomException("Incompatible product code (or) product code out of bound.");
        } else if (product.getName() == null) {
            throw new CustomException("Incompatible product name.");
        } else if (product.getUnitCode() == null) {
            throw new CustomException("Incompatible product unit code.");
        } else if (product.getType() == null) {
            throw new CustomException("Incompatible product type.");
        } else if (product.getPrice() == 0) {
            throw new CustomException("Incompatible product price");
        } else if (product.isDeleted() == true) {
            throw new CustomException("Incompatible isDeleted assigned.");
        }
        else {
            return true;
        }
    }
}
