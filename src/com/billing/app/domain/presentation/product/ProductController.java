package com.billing.app.domain.presentation.product;

import com.billing.app.domain.exceptions.*;
import com.billing.app.domain.exceptions.IllegalArgumentException;
import com.billing.app.domain.exceptions.TemplateMismatchException;
import com.billing.app.domain.presentation.Validator;
import com.billing.app.domain.service.product.ProductService;
import com.billing.app.domain.service.product.ProductServiceInterface;
import com.billing.app.domain.entity.Product;


import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductController {
    List<String> valueList;
    List<String> keyList;
    private Product product;
    private ProductServiceInterface productServiceInterface;
    private Validator validator;

    public Product create(ArrayList<String> values) throws ClassNotFoundException, SQLException, TemplateMismatchException, IllegalArgumentException, TypeMismatchException, ObjectNullPointerException {
        int expectedLength = 6;
        int actualLength = values.size();
        validator = new Validator();
        validator.validateProductDetails(values);
        if (actualLength == expectedLength) {
            product = new Product();
            product.setCode(values.get(0));
            product.setName(values.get(1));
            product.setUnitCode(values.get(2));
            product.setType(values.get(3));
            product.setPrice(Float.parseFloat(values.get(4)));
            product.setStock(Float.parseFloat(values.get(5)));
            productServiceInterface = new ProductService();
            return productServiceInterface.create(product);
        } else {
            throw new TemplateMismatchException("Invalid argument length. Expected: " + expectedLength + ", Actual: " + actualLength);
        }
    }


    public Product edit(ArrayList<String> values) throws SQLException, ClassNotFoundException, NullPointerException, TemplateMismatchException, TypeMismatchException, IllegalArgumentException, ObjectNullPointerException, CodeNotFoundException {
        int expectedLength = 12;
        int actualLength = values.size();
        valueList = new ArrayList<>();
        keyList = new ArrayList<>();
        if (actualLength == expectedLength) {
            for (int index = 0; index < values.size(); index += 2) {
                String key = values.get(index);
                String value = values.get(index + 1);
                keyList.add(key);
                valueList.add(value);
            }
            validator = new Validator();
            validator.validateProductKeys(keyList);
            validator.validateProductDetails(valueList);
            product = new Product();
            product.setCode(valueList.get(0));
            product.setName(valueList.get(1));
            product.setUnitCode(valueList.get(2));
            product.setType(valueList.get(3));
            product.setPrice(Float.parseFloat(valueList.get(4)));
            product.setStock(Float.parseFloat(valueList.get(5)));
        } else {
            throw new TemplateMismatchException("Invalid argument length. Expected: " + expectedLength + ", Actual: " + actualLength);
        }
        productServiceInterface = new ProductService();
        return productServiceInterface.edit(product);
    }




    public boolean delete(ArrayList<String> values) throws TemplateMismatchException, SQLException, ClassNotFoundException, CodeNotFoundException {
        boolean flag = false;

        int expectedLength = 2;
        int actualLength = values.size();

        if (actualLength == expectedLength) {
            String notation = values.get(0);
            String value = values.get(1);
            validator = new Validator();
            String key = validator.validateDelete(notation);
            productServiceInterface = new ProductService();
            flag = productServiceInterface.delete(key, value);
            return flag;
        } else {
            throw new TemplateMismatchException("Number of arguments provided doesn't match the template. Please provide the value as key-value pair.");
        }
    }

    public List<Product> list(List<String> values) {
        System.out.println(values);
        return null;
    }
}
