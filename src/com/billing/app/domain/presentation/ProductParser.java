package com.billing.app.domain.presentation;

import com.billing.app.domain.exceptions.*;
import com.billing.app.domain.service.ProductService;
import com.billing.app.domain.service.ProductServiceInterface;
import com.billing.app.domain.entity.Product;
import com.billing.app.domain.database.ProductJdbcDAO;
import com.billing.app.domain.database.ProductDAO;
import java.util.ArrayList;
import java.util.HashMap;

public class ProductParser {
    private Product product;
    private ProductDAO productDAO;
    private ProductServiceInterface productServiceInterface;
    private Validator validator;

    public Product create(ArrayList<String> arrayList) throws ProductException, ClassNotFoundException, IllegalAccessException {
        validator = new Validator();
        ArrayList<String> values = new ArrayList<>(arrayList.subList(2, arrayList.size()));
        Product product = new Product();
        if (validator.createByValidation(values)) {
            product.setCode(values.get(0));
            product.setName(values.get(1));
            product.setUnitCode(values.get(2));
            product.setType(values.get(3));
            product.setPrice(Float.parseFloat(values.get(4)));
        }
        else {
            throw new ProductNullConstraintException("Product value to be assign may be null. Cannot assign null values.");
        }
        productServiceInterface = new ProductService();
        return productServiceInterface.create(product);
    }





    public Product edit(ArrayList<String> arrayList) throws ProductException, ClassNotFoundException, IllegalAccessException, NoSuchFieldException {
        productDAO = new ProductJdbcDAO();
        String code = arrayList.get(3);
        ArrayList<String> keyValues = new ArrayList<>(arrayList.subList(4, arrayList.size()));
        HashMap<String, String> map = new HashMap<>();
        for (int i = 0; i < keyValues.size(); i += 2) {
            String key = keyValues.get(i);
            String value = keyValues.get(i + 1);
            map.put(key, value);
        }
        product = productDAO.getProductByCode(code);
        productServiceInterface = new ProductService();
        return productServiceInterface.edit(product, map);
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
