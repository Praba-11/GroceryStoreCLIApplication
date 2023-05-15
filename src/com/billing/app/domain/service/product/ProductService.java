package com.billing.app.domain.service.product;

import com.billing.app.domain.database.*;
import com.billing.app.domain.entity.Product;
import com.billing.app.domain.exceptions.*;
import com.billing.app.domain.exceptions.IllegalArgumentException;

import java.sql.SQLException;
import java.util.List;

public class ProductService implements ProductServiceInterface {
    private ProductDAO productDAO = new ProductDAOImplementation();
    private ProductValidator productValidator = new ProductValidator();

    public Product create(Product product) throws ClassNotFoundException, SQLException, ObjectNullPointerException {
        try {
            productValidator.validate(product);
            return productDAO.create(product);
        } catch (ObjectNullPointerException exception) {
            throw new ObjectNullPointerException("Error while creating product: " + exception.getMessage());
        }
    }


    public Product edit(Product product) throws ClassNotFoundException, SQLException, ObjectNullPointerException, CodeNotFoundException {

        try {
            productValidator.validate(product);
            if (productDAO.find(product.getId()) != null) {
                return productDAO.edit(product);
            }
            throw new CodeNotFoundException("Provided product id not present in product relation table.");
        } catch (ObjectNullPointerException exception) {
            throw new ObjectNullPointerException("Error while editing product: " + exception.getMessage());
        }
    }


    public boolean delete(int id) throws SQLException, ClassNotFoundException, CodeNotFoundException {
        boolean isDeleted;
        isDeleted = productDAO.delete(id);
        if (!isDeleted) {
            throw new CodeNotFoundException("(Id: " + id + ") not present in product relational table.");
        }
        return true;
    }

    public List<Product> list(int range, int page, String attribute, String searchText) throws SQLException, ClassNotFoundException, IllegalArgumentException {
        List<Product> list;
        if (attribute == null && searchText != null && range == 0 && page == 0) {
            list = productDAO.list(searchText);
        } else {
            if (attribute == null && searchText == null && range == 0 && page == 0) {
                attribute = "isdeleted";
                searchText = "";
                range = productDAO.count();
            } else if (attribute == null && searchText == null && range > 0 && page == 0) {
                attribute = "isdeleted";
                searchText = "";
            } else if (attribute == null && searchText == null && range > 0 && page > 0) {
                attribute = "isdeleted";
                searchText = "";
                page = (page - 1) * range;
            } else if (attribute != null && searchText != null && range == 0 && page == 0) {
                range = productDAO.count();
            } else if (attribute != null && searchText != null && range > 0 && page > 0) {
                page = (page - 1) * range;
            } else {
                throw new IllegalArgumentException("Invalid argument provided. Please provide valid arguments as per template.");
            }
            list = productDAO.list(range, page, attribute, searchText);
        }
        return list;
    }
}
