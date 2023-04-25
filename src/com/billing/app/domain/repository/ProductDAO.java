package com.billing.app.domain.repository;
import com.billing.app.domain.entity.Product;

import java.sql.SQLException;
import java.util.ArrayList;

public interface ProductDAO {
    Product create(Product product) throws Throwable;
    Product edit(Product product) throws Throwable;
    boolean delete(String code) throws SQLException, ClassNotFoundException, CustomException;
    ArrayList<Product> list() throws SQLException, ClassNotFoundException;
    ArrayList<Product> list(int range) throws SQLException, ClassNotFoundException;
    ArrayList<Product> list(int range, int page) throws SQLException, ClassNotFoundException;
    ArrayList<Product> list(String searchText) throws SQLException, ClassNotFoundException;
    ArrayList<Product> list(String attribute, String searchText) throws SQLException, ClassNotFoundException;
    ArrayList<Product> list(String attribute, String searchText, int range, int page) throws SQLException, ClassNotFoundException;

}
