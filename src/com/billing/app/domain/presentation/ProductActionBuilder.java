package com.billing.app.domain.presentation;

import com.billing.app.domain.application.ProductService;
import com.billing.app.domain.application.ProductServiceInterface;
import com.billing.app.domain.entity.Product;
import com.billing.app.domain.repository.CustomException;
import com.billing.app.domain.repository.ProductJdbcDAO;
import com.billing.app.domain.repository.ProductDAO;
import java.util.ArrayList;

public class ProductActionBuilder {
    private Product product;
    private ProductDAO productDAO;
    private ProductServiceInterface productServiceInterface;



    public Product create(ArrayList arrayList) throws Throwable {
        try {
            Product product = new Product();
            product.setCode(arrayList.get(2).toString());
            product.setName(arrayList.get(3).toString());
            product.setUnitCode(arrayList.get(4).toString());
            product.setType(arrayList.get(5).toString());
            product.setPrice(Float.parseFloat(arrayList.get(6).toString()));
            productServiceInterface = new ProductService();
            return productServiceInterface.create(product);
        }
        catch (Throwable exception) {
            throw new CustomException(exception.getMessage());
        }
    }



    public Product edit(ArrayList arrayList) throws Throwable {
        try {
            productDAO = new ProductJdbcDAO();
            String code = arrayList.get(3).toString();
            ArrayList editArrayList = new ArrayList<>(arrayList.subList(4, arrayList.size()));
            product = productDAO.getProduct(code);
            productServiceInterface = new ProductService();
            return productServiceInterface.edit(product, editArrayList);
        }
        catch (Throwable exception) {
            throw new CustomException(exception.getMessage());
        }
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
}
