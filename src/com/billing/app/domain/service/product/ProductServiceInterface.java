package com.billing.app.domain.service.product;

import com.billing.app.domain.entity.Product;
import com.billing.app.domain.exceptions.CodeNotFoundException;
import com.billing.app.domain.exceptions.InvalidArgumentException;
import com.billing.app.domain.exceptions.ObjectNullPointerException;

import java.sql.SQLException;
import java.util.List;

public interface ProductServiceInterface {
    Product create(Product product) throws ClassNotFoundException, SQLException, ObjectNullPointerException;
    Product edit(Product product) throws ClassNotFoundException, SQLException, ObjectNullPointerException, CodeNotFoundException;
    boolean delete(int id) throws SQLException, ClassNotFoundException, CodeNotFoundException;
    List<Product> list(int range, int page, String attribute, String searchText) throws SQLException, ClassNotFoundException, InvalidArgumentException;
    boolean stockUpdate(String code, float stock) throws SQLException;
    boolean priceUpdate(String code, float price) throws SQLException;
}
