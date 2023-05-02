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

    public Product create(ArrayList<String> arrayList) throws ProductException, ClassNotFoundException {
        validator = new Validator();
        if (validator.isValidConstraints(arrayList)) {
            Product product = new Product();
            product.setCode(arrayList.get(2));
            product.setName(arrayList.get(3));
            product.setUnitCode(arrayList.get(4));
            product.setType(arrayList.get(5));
            product.setPrice(Float.parseFloat(arrayList.get(6)));
            productServiceInterface = new ProductService();
            return productServiceInterface.create(product);
        }
        else {
            throw new InvalidConstraintLengthException("Invalid constraint length provided, please provide valid constraints ");
        }
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
        product = productDAO.getProduct(code);
        productServiceInterface = new ProductService();
        return productServiceInterface.edit(product, map);
    }




    public boolean delete(ArrayList arrayList) throws Throwable {
        try {
            String code = arrayList.get(2).toString();
            productServiceInterface = new ProductService();
            return productServiceInterface.delete(code);
        }
        catch (Throwable exception) {
            throw new CustomException(exception.getMessage());
        }
    }




    public ArrayList<Product> list(ArrayList arrayList) throws Throwable {
        try {
            productServiceInterface = new ProductService();
            if (arrayList.size() == 2) {
                return productServiceInterface.list();
            }
            else if (arrayList.get(2).toString().equals("-p") && arrayList.size() == 4) {
                int range = Integer.parseInt(arrayList.get(3).toString());
                return productServiceInterface.list(range);
            }
            else if (arrayList.get(2).toString().equals("-p") && arrayList.size() == 5) {
                int range = Integer.parseInt(arrayList.get(3).toString());
                int page = Integer.parseInt(arrayList.get(4).toString());
                return productServiceInterface.list(range, page);
            }
            else if (arrayList.get(2).toString().equals("-s") && arrayList.size() == 4) {
                String searchText = arrayList.get(3).toString();
                return productServiceInterface.list(searchText);
            }
            else if (arrayList.get(2).toString().equals("-s") && arrayList.size() == 5) {
                String searchText = arrayList.get(4).toString();
                String attribute = arrayList.get(3).toString();
                return productServiceInterface.list(attribute, searchText);
            }
            else if (arrayList.get(2).toString().equals("-s") && arrayList.size() == 7) {
                String searchText = arrayList.get(4).toString();
                String attribute = arrayList.get(3).toString();
                int range = Integer.parseInt(arrayList.get(5).toString());
                int page = Integer.parseInt(arrayList.get(6).toString());
                return productServiceInterface.list(attribute, searchText, range, page);
            }
            else {
                throw new CustomException("Template mismatch.");
            }
        }
        catch (Throwable exception) {
            throw new CustomException(exception.getMessage());
        }
    }
}
