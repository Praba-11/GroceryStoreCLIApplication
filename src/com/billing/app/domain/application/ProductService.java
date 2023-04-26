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
        if (productDAO.create(product))
            return productDAO.getProduct(product.getCode());
        else
            throw new CustomException("Product creation unsuccessful.");
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
}
