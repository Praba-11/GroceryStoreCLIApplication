package com.billing.app.domain.service.product;

import com.billing.app.domain.database.*;
import com.billing.app.domain.entity.Product;
import com.billing.app.domain.exceptions.*;
import com.billing.app.domain.exceptions.InvalidArgumentException;

import java.sql.SQLException;
import java.util.List;

public class ProductServiceImplementation implements ProductService {
    private ProductDAO productDAO = new ProductDAOImplementation();
    private ProductValidator productValidator = new ProductValidator();

    public Product create(Product product) throws SQLException, ObjectNullPointerException {
        try {
            productValidator.validate(product);
            return productDAO.create(product);
        } catch (ObjectNullPointerException exception) {
            throw new ObjectNullPointerException("Error while creating product: " + exception.getMessage());
        }
    }


    public Product edit(Product product) throws SQLException, ObjectNullPointerException, CodeOrIDNotFoundException {

        try {
            productValidator.validate(product);
            if (productDAO.findById(product.getId()) != null) {
                return productDAO.edit(product);
            }
            throw new CodeOrIDNotFoundException("Provided product id not present in product relation table.");
        } catch (ObjectNullPointerException exception) {
            throw new ObjectNullPointerException("Error while editing product: " + exception.getMessage());
        }
    }


    public boolean delete(int id) throws SQLException, CodeOrIDNotFoundException {
        boolean isDeleted;
        isDeleted = productDAO.delete(id);
        if (!isDeleted) {
            throw new CodeOrIDNotFoundException("(Id: " + id + ") not present in product relational table.");
        }
        return true;
    }

    public List<Product> list(int range, int page, String attribute, String searchText) throws SQLException,  InvalidArgumentException {
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
                throw new InvalidArgumentException("Invalid argument provided. Please provide valid arguments as per template.");
            }
            list = productDAO.list(range, page, attribute, searchText);
        }
        return list;
    }

    public Product stockUpdate(String code, float stock) throws SQLException {
        Product product = productDAO.findByCode(code);
        product.setStock(stock);
        return productDAO.edit(product);
    }

    public Product priceUpdate(String code, float price) throws SQLException {
        Product product = productDAO.findByCode(code);
        product.setPrice(price);
        return productDAO.edit(product);
    }
}
