package com.billing.app.domain.database;

import com.billing.app.domain.entity.Product;
import com.billing.app.domain.exceptions.CustomException;
import com.billing.app.domain.exceptions.ProductException;

import java.util.ArrayList;

public interface ProductDAO {
    boolean create(Product product) throws ProductException, ClassNotFoundException;
    boolean edit(Product product) throws ProductException, ClassNotFoundException, IllegalAccessException;
    boolean delete(String code) throws ProductException, ClassNotFoundException;
    ArrayList<Product> list() throws ProductException, ClassNotFoundException;
    ArrayList<Product> list(int range) throws ProductException, ClassNotFoundException;
    ArrayList<Product> list(int range, int page) throws ProductException;
    ArrayList<Product> list(String searchText) throws ProductException;
    ArrayList<Product> list(String attribute, String searchText) throws ProductException;
    ArrayList<Product> list(String attribute, String searchText, int range, int page) throws ProductException;
    int getStock(String code) throws ProductException;
    Product getProduct(String code) throws ProductException;
    int getCount() throws CustomException, ProductException;
    boolean isCodePresent(String code) throws ProductException;

}
