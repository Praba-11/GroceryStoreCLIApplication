package com.billing.app.domain.service.product;

import com.billing.app.domain.database.ProductDAO;
import com.billing.app.domain.database.ProductDAOImplementation;
import com.billing.app.domain.entity.Product;
import com.billing.app.domain.exceptions.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ProductValidator {
    ProductDAO productDAO = new ProductDAOImplementation();

    public boolean validate(Product product) throws ObjectNullPointerException {
        if(product == null) {
            throw new ObjectNullPointerException("Product cannot be null or empty.");
        }
        if (product.getCode().isEmpty() || product.getCode() == null) {
            throw new ObjectNullPointerException("Product code cannot be null (or) empty.");
        }
        if (product.getName().isEmpty() || product.getName() == null) {
            throw new ObjectNullPointerException("Product name cannot be null (or) empty.");
        }
        if (product.getUnitCode().isEmpty() || product.getUnitCode() == null) {
            throw new ObjectNullPointerException("Product unit code cannot be null (or) empty.");
        }
        if (product.getType().isEmpty() || product.getType() == null) {
            throw new ObjectNullPointerException("Product type cannot be null (or) empty.");
        }
        return true;
    }

    public List validateList(String attribute, String searchText, int range, int page) throws InvalidArgumentException, SQLException {
        List list = new ArrayList<>();
        if (attribute == null && searchText == null && range == 0 && page == 0) {
            attribute = "isdeleted";
            searchText = "";
            range = productDAO.count();
        } else if (attribute == null && searchText == null && range > 0 && page == 0) {
            attribute = "isdeleted";
            searchText = "";
        } else if (attribute == null && searchText == null && range > 0 && page > 0) {
            int calculatedLimit = (range * page) - range;
            int calculatedPage = calculatedLimit / range;
            if (calculatedLimit > productDAO.count()) {
                throw new InvalidArgumentException("Invalid argument provided. " +
                        "(Number of pages for listing : " + calculatedPage + ")");
            } else {
                attribute = "isdeleted";
                searchText = "";
                page = (page - 1) * range;
            }
        } else if (attribute != null && searchText != null && range == 0 && page == 0) {
            range = productDAO.count();
        } else if (attribute != null && searchText != null && range > 0 && page > 0) {
            int calculatedLimit = (range * page) - range;
            int calculatedPage = calculatedLimit / range;
            if (calculatedLimit > productDAO.count()) {
                throw new InvalidArgumentException("Invalid argument provided. " +
                        "(Number of pages for listing : " + calculatedPage + ")");
            } else {
                page = (page - 1) * range;
            }

        } else {
            throw new InvalidArgumentException("Invalid argument provided\n. " +
                    "Please provide valid arguments as per template for listing the product.");
        }
        list.addAll(Arrays.asList(range, page, attribute, searchText));
        return list;
    }
}
