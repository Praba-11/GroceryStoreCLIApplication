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
        productValidator.validate(product);
        productDAO.create(product);
        return productDAO.findByCode(product.getCode());
    }


    public Product edit(Product product) throws SQLException, ObjectNullPointerException, NotFoundException {
        productValidator.validate(product);
        if (productDAO.findById(product.getId()) != null) {
            if (!productDAO.findById(product.getId()).isDeleted()) {
                productDAO.edit(product);
                return productDAO.findById(product.getId());
            }
            throw new NotFoundException("Product is deleted. Cannot edit the deleted product.");
        }
        throw new NotFoundException("Product of (Id: " + product.getId() + ") doesn't exist.\n" +
                "Please provide a product to be edited with valid id.");
    }


    public boolean delete(int id) throws SQLException, NotFoundException {
        boolean isDeleted;
        isDeleted = productDAO.delete(id);
        if (!isDeleted) {
            throw new NotFoundException("(Id: " + id + ") doesn't exist. \n" +
                    "Please provide a valid id for deleting the product.");
        }
        return true;
    }

    public List<Product> list(int range, int page, String attribute, String searchText) throws SQLException,  InvalidArgumentException {
        List<Product> lists;
        if (attribute == null && searchText != null && range == 0 && page == 0) {
            lists = productDAO.list(searchText);
        } else {
            List list = productValidator.validateList(attribute, searchText, range, page);
            int range1 = Integer.parseInt(list.get(0).toString());
            int page1 = Integer.parseInt(list.get(1).toString());
            String attribute1 = list.get(2).toString();
            String searchText1 = list.get(3).toString();
            lists = productDAO.list(range1, page1, attribute1, searchText1);
        }
        return lists;
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
