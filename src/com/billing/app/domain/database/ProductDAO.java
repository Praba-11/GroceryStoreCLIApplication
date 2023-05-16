package com.billing.app.domain.database;

import com.billing.app.domain.entity.Product;
import com.billing.app.domain.exceptions.CodeNotFoundException;

import java.sql.SQLException;
import java.util.List;

public interface ProductDAO {
    Product create(Product product) throws ClassNotFoundException, SQLException;
    Product edit(Product product) throws ClassNotFoundException, SQLException, CodeNotFoundException;
    boolean delete(int id) throws SQLException, ClassNotFoundException;
    List<Product> list(int range, int page, String attribute, String searchText) throws SQLException, ClassNotFoundException;
    List<Product> list(String searchText) throws SQLException, ClassNotFoundException;
    Product find(int id) throws SQLException, ClassNotFoundException;
    int count() throws SQLException, ClassNotFoundException;
    float getPrice(String code) throws SQLException, ClassNotFoundException;
}