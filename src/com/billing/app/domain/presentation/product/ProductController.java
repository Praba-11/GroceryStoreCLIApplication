package com.billing.app.domain.presentation.product;

import com.billing.app.domain.exceptions.*;
import com.billing.app.domain.exceptions.CodeNullException;
import com.billing.app.domain.exceptions.IllegalArgumentException;
import com.billing.app.domain.exceptions.TemplateMismatchException;
import com.billing.app.domain.presentation.Validator;
import com.billing.app.domain.service.product.ProductService;
import com.billing.app.domain.service.product.ProductServiceInterface;
import com.billing.app.domain.entity.Product;
import com.billing.app.domain.database.ProductDAO;

import java.sql.SQLException;
import java.util.ArrayList;

public class ProductController {
    private Product product;
    private ProductDAO productDAO;
    private ProductServiceInterface productServiceInterface;
    private Validator validator;

    public Product create(ArrayList<String> arrayList) throws ProductException, ClassNotFoundException, SQLException {
        ArrayList<String> values = new ArrayList<>(arrayList.subList(2, arrayList.size()));
        product = new Product();
        product.setCode(values.get(0));
        product.setName(values.get(1));
        product.setUnitCode(values.get(2));
        product.setType(values.get(3));
        product.setPrice(Float.parseFloat(values.get(4)));
        product.setStock(Float.parseFloat(values.get(5)));
        productServiceInterface = new ProductService();
        return productServiceInterface.create(product);
    }




    public Product edit(ArrayList<String> arrayList) throws SQLException, ClassNotFoundException, IllegalAccessException, NullPointerException, CodeNullException, TemplateMismatchException, CustomException, NoSuchFieldException, ProductException {
        validator = new Validator();
        product = new Product();
        ArrayList<String> keyValuePair = new ArrayList<>(arrayList.subList(2, arrayList.size()));
        for (int index = 0; index < keyValuePair.size(); index += 2) {
            String key = keyValuePair.get(index);
            String value = keyValuePair.get(index + 1);
            validator.productEditValidate(product, key, value);
        }
        productServiceInterface = new ProductService();
        return productServiceInterface.edit(product);
    }




    public boolean delete(ArrayList<String> arrayList) throws TemplateMismatchException, SQLException, ProductException, ClassNotFoundException {
        boolean flag = false;
        if (arrayList.size() == 4) {
            String key = arrayList.get(2);
            String value = arrayList.get(3);
            validator = new Validator();
            if (validator.deleteValidate(key)) {
                productServiceInterface = new ProductService();
                flag = productServiceInterface.delete(key, value);
            }
            return flag;
        } else {
            throw new TemplateMismatchException("Number of arguments provided doesn't match the template. Please provide the value as key-value pair.");
        }
    }




    public ArrayList<Product> list(ArrayList<String> arrayList) throws Throwable {
            productServiceInterface = new ProductService();
            if (arrayList.size() == 2) {
                return productServiceInterface.list();
            }
            else if (arrayList.get(2).equals("-p") && arrayList.size() == 4) {
                int range = Integer.parseInt(arrayList.get(3));
                return productServiceInterface.list(range);
            }
            else if (arrayList.get(2).equals("-p") && arrayList.size() == 5) {
                int range = Integer.parseInt(arrayList.get(3));
                int page = Integer.parseInt(arrayList.get(4));
                return productServiceInterface.list(range, page);
            }
            else if (arrayList.get(2).equals("-s") && arrayList.size() == 4) {
                String searchText = arrayList.get(3);
                return productServiceInterface.list(searchText);
            }
            else if (arrayList.get(2).equals("-s") && arrayList.size() == 5) {
                String searchText = arrayList.get(4);
                String attribute = arrayList.get(3);
                return productServiceInterface.list(attribute, searchText);
            }
            else if (arrayList.get(2).equals("-s") && arrayList.get(5).equals("-p") && arrayList.size() == 8) {
                String searchText = arrayList.get(4);
                String attribute = arrayList.get(3);
                int range = Integer.parseInt(arrayList.get(6));
                int page = Integer.parseInt(arrayList.get(7));
                return productServiceInterface.list(attribute, searchText, range, page);
            }
            else {
                throw new CustomException("Template mismatch.");
            }
    }

}
