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
import java.util.Map;

public class ProductController {
    private List<String> valueList;
    private List<String> keyList;
    private Product product;
    private ProductServiceInterface productServiceInterface = new ProductService();
    private Validator validator = new Validator();


    public Product create(List<String> values) throws ClassNotFoundException, SQLException, TemplateMismatchException, IllegalArgumentException, TypeMismatchException, ObjectNullPointerException {

        int expectedLength = 6;
        int actualLength = values.size();
        validator.validateValues(values);
        if (actualLength == expectedLength) {
            product = setValues(values, false);
            return productServiceInterface.create(product);
        } else {
            throw new TemplateMismatchException("Invalid argument length. Expected: " + expectedLength + ", Actual: " + actualLength);
        }
    }



    public Product edit(Map<String, String> values) throws SQLException, ClassNotFoundException, NullPointerException, TemplateMismatchException, TypeMismatchException, IllegalArgumentException, ObjectNullPointerException, CodeNotFoundException {

        int expectedLength = 7;
        int actualLength = values.size();
        if (actualLength == expectedLength) {
            validator.validateMap(values);
            valueList = new ArrayList<>(values.values());
            product = setValues(valueList, true);
        } else {
            throw new TemplateMismatchException("Invalid argument length. Expected: " + expectedLength + ", Actual: " + actualLength);
        }

        return productServiceInterface.edit(product);
    }




    public boolean delete(List<String> values) throws TemplateMismatchException, SQLException, ClassNotFoundException, CodeNotFoundException {
        boolean flag = false;

        int expectedLength = 1;
        int actualLength = values.size();

        if (actualLength == expectedLength) {
            int id = Integer.parseInt(values.get(0));
            flag = productServiceInterface.delete(id);
            return flag;
        } else {
            throw new TemplateMismatchException("Invalid argument length. Expected: " + expectedLength + ", Provided: " + actualLength);
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

    private static Product setValues(List<String> values, boolean setId) {
        Product product = new Product();
        int startIndex = setId ? 0 : -1;

        if (setId) {
            product.setId(Integer.parseInt(values.get(startIndex)));
        }

        product.setCode(values.get(startIndex + 1));
        product.setName(values.get(startIndex + 2));
        product.setUnitCode(values.get(startIndex + 3));
        product.setType(values.get(startIndex + 4));
        product.setPrice(Float.parseFloat(values.get(startIndex + 5)));
        product.setStock(Float.parseFloat(values.get(startIndex + 6)));
        return product;
    }
}
