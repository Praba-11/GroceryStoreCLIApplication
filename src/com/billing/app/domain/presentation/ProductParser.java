package com.billing.app.domain.presentation;

import com.billing.app.domain.exceptions.*;
import com.billing.app.domain.service.ProductService;
import com.billing.app.domain.service.ProductServiceInterface;
import com.billing.app.domain.entity.Product;
import com.billing.app.domain.database.ProductJdbcDAO;
import com.billing.app.domain.database.ProductDAO;

import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ProductParser {
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




    public Product edit(ArrayList<String> arrayList) throws ProductException, ClassNotFoundException, IllegalAccessException, NoSuchFieldException, CustomException {
        ArrayList<String> keyValues = new ArrayList<>(arrayList.subList(2, arrayList.size()));
        HashMap<String, String> map = new HashMap<>();
        for (int i = 0; i < keyValues.size(); i += 2) {
            String key = keyValues.get(i);
            String value = keyValues.get(i + 1);
            map.put(key, value);
        }
        product = new Product();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            if (key.equals("code")) {
              product.setCode(value);
            } else if (key.equals("name")) {
                product.setName(value);
            } else if (key.equals("unitCode")) {
                product.setUnitCode(value);
            } else if (key.equals("type")) {
                product.setType(value);
            } else if (key.equals("price")) {
                product.setPrice(Float.parseFloat(value));
            } else {
                throw new ProductException("Invalid attribute provided. Please provide necessary attribute. " + key);
            }
        }
        productServiceInterface = new ProductService();
        return productServiceInterface.edit(product);
    }




    public boolean delete(ArrayList<String> arrayList) throws ProductException, ClassNotFoundException, CustomException {
        try {
            String code = arrayList.get(2);
            productServiceInterface = new ProductService();
            return productServiceInterface.delete(code);
        }
        catch (Throwable exception) {
            throw new CustomException(exception.getMessage());
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
