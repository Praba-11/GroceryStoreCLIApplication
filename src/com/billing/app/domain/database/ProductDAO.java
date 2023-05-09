package com.billing.app.domain.database;

import com.billing.app.domain.entity.Product;
import com.billing.app.domain.exceptions.CustomException;
import com.billing.app.domain.exceptions.ProductException;

import java.sql.SQLException;
import java.util.ArrayList;

public interface ProductDAO {
    boolean create(Product product) throws ClassNotFoundException, SQLException;
    boolean edit(Product product) throws ClassNotFoundException, IllegalAccessException, SQLException;
    boolean delete(String key, String value) throws SQLException, ClassNotFoundException;
    ArrayList<Product> list() throws ProductException, ClassNotFoundException;
    ArrayList<Product> list(int range) throws ProductException, ClassNotFoundException;
    ArrayList<Product> list(int range, int page) throws ProductException;
    ArrayList<Product> list(String searchText) throws ProductException;
    ArrayList<Product> list(String attribute, String searchText) throws ProductException;
    ArrayList<Product> list(String attribute, String searchText, int range, int page) throws ProductException;
    boolean isIdPresent(String id) throws SQLException, ClassNotFoundException;
    Product getProductByCode(String code) throws ProductException;
    boolean isCodePresent(String code) throws SQLException, ClassNotFoundException;

}