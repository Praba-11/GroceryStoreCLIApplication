package com.billing.app.domain.presentation.product;

import com.billing.app.domain.exceptions.*;
import com.billing.app.domain.exceptions.IllegalArgumentException;
import com.billing.app.domain.exceptions.TemplateMismatchException;
import com.billing.app.domain.presentation.Validator;
import com.billing.app.domain.service.product.ProductService;
import com.billing.app.domain.service.product.ProductServiceInterface;
import com.billing.app.domain.entity.Product;
import com.billing.app.domain.service.product.ProductValidator;


import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ProductController {
    private List<String> valueList;
    private List<String> keyList;
    private Product product;
    private ProductServiceInterface productServiceInterface = new ProductService();
    private Validator validator = new Validator();


    public Product create(ArrayList<String> values) throws ClassNotFoundException, SQLException, TemplateMismatchException, IllegalArgumentException, TypeMismatchException, ObjectNullPointerException {

        int expectedLength = 6;
        int actualLength = values.size();
        validator.validateProductDetails(values);
        if (actualLength == expectedLength) {
            product = new Product();
            product.setCode(values.get(0));
            product.setName(values.get(1));
            product.setUnitCode(values.get(2));
            product.setType(values.get(3));
            product.setPrice(Float.parseFloat(values.get(4)));
            product.setStock(Float.parseFloat(values.get(5)));
            return productServiceInterface.create(product);
        } else {
            throw new TemplateMismatchException("Invalid argument length. Expected: " + expectedLength + ", Actual: " + actualLength);
        }
    }



    public Product edit(ArrayList<String> values) throws SQLException, ClassNotFoundException, NullPointerException, TemplateMismatchException, TypeMismatchException, IllegalArgumentException, ObjectNullPointerException, CodeNotFoundException {

        int expectedLength = 14;
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

            List<String> keys = new ArrayList<>(keyList.subList(1, keyList.size()));
            List<String> details = new ArrayList<>(valueList.subList(1, valueList.size()));

            String key = keyList.get(0);
            String identifier = valueList.get(0);

            validator.validateId(key, identifier);
            validator.validateProductKeys(keys);
            validator.validateProductDetails(details);

            product = new Product();
            product.setId(Integer.parseInt(valueList.get(0)));
            product.setCode(valueList.get(1));
            product.setName(valueList.get(2));
            product.setUnitCode(valueList.get(3));
            product.setType(valueList.get(4));
            product.setPrice(Float.parseFloat(valueList.get(5)));
            product.setStock(Float.parseFloat(valueList.get(6)));

        } else {
            throw new TemplateMismatchException("Invalid argument length. Expected: " + expectedLength + ", Actual: " + actualLength);
        }

        return productServiceInterface.edit(product);
    }




    public boolean delete(ArrayList<String> values) throws TemplateMismatchException, SQLException, ClassNotFoundException, CodeNotFoundException {
        boolean flag = false;

        int expectedLength = 1;
        int actualLength = values.size();

        if (actualLength == expectedLength) {
            int id = Integer.parseInt(values.get(0));
            flag = productServiceInterface.delete(id);
            return flag;
        } else {
            throw new TemplateMismatchException("Number of arguments provided doesn't match the template. Please provide the value as key-value pair.");
        }
    }



    public List<Product> list(List<String> values) throws IllegalArgumentException, TemplateMismatchException, SQLException, ClassNotFoundException {

        int range, page;
        String attribute, searchText;

        validator = new Validator();
        Map<String, Object> parameters = validator.validateProductList(values);

        range = Integer.parseInt(parameters.get("range").toString());
        page = Integer.parseInt(parameters.get("page").toString());
        attribute = (String) parameters.get("attribute");
        searchText = (String) parameters.get("searchtext");

        return productServiceInterface.list(range, page, attribute, searchText);

    }
}
