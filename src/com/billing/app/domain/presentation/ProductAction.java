package com.billing.app.domain.presentation;

import com.billing.app.domain.entity.Product;
import com.billing.app.domain.repository.CustomException;
import com.billing.app.domain.repository.JdbcProductDAO;
import com.billing.app.domain.repository.ProductDAO;

import java.sql.SQLException;
import java.util.ArrayList;

public class ProductAction {
    private ProductDAO productDAO;

    public Product create(ArrayList arrayList) throws Throwable {
        try {
            Product product = new Product();
            product.setCode(arrayList.get(2).toString());
            product.setName(arrayList.get(3).toString());
            product.setUnitCode(arrayList.get(4).toString());
            product.setType(arrayList.get(5).toString());
            product.setPrice(Float.parseFloat(arrayList.get(6).toString()));
            productDAO = new JdbcProductDAO();
            productDAO.create(product);
            return product;
        }
        catch (Throwable exception) {
            throw new CustomException(exception.getMessage());
        }
    }

    public Product edit(ArrayList arrayList) throws Throwable {
        try {
            ArrayList editArrayList;
            editArrayList = new ArrayList<Product>(arrayList.subList(2, arrayList.size()));
            Product product = new Product();

            for (int index = 0; index < editArrayList.size(); index+=2) {
                switch (editArrayList.get(index).toString()) {
                    case "code": product.setCode(editArrayList.get(index + 1).toString()); break;
                    case "name": product.setName(editArrayList.get(index + 1).toString()); break;
                    case "unitCode": product.setUnitCode(editArrayList.get(index + 1).toString()); break;
                    case "type": product.setType(editArrayList.get(index + 1).toString()); break;
                    case "price": product.setPrice(Float.parseFloat(editArrayList.get(index + 1).toString())); break;
                    default: throw new CustomException("Template mismatch");
                }
            }
            productDAO = new JdbcProductDAO();
            productDAO.edit(product);
            return product;
        }
        catch (Throwable exception) {
            throw new CustomException(exception.getMessage());
        }
    }

    public boolean delete(ArrayList arrayList) throws Throwable {
        try {
            String code = arrayList.get(2).toString();
            productDAO = new JdbcProductDAO();
            productDAO.delete(code);
            return true;
        }
        catch (Throwable exception) {
            throw new CustomException(exception.getMessage());
        }
    }

    public ArrayList<Product> list(ArrayList arrayList) throws SQLException, ClassNotFoundException{
        try {
            if (arrayList.size() == 2)
                productDAO.list();
            else if (arrayList.get(2).equals("-p")) {

            }
            else if (arrayList.get(2).equals("-s")) {

            }
        }
        catch (Throwable exception) {
            throw new CustomException(exception.getMessage());
        }
    }
}
