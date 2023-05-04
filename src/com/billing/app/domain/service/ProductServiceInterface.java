package com.billing.app.domain.service;

import com.billing.app.domain.entity.Product;
import com.billing.app.domain.exceptions.CustomException;
import com.billing.app.domain.exceptions.ProductException;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public interface ProductServiceInterface {
    Product create(Product product) throws ProductException, ClassNotFoundException, SQLException;
    Product edit(Product product) throws ProductException, ClassNotFoundException, IllegalAccessException, NoSuchFieldException, CustomException;
    boolean delete(String code) throws ProductException, ClassNotFoundException;
    ArrayList<Product> list() throws ProductException, ClassNotFoundException;
    ArrayList<Product> list(int range) throws ProductException, ClassNotFoundException;
    ArrayList<Product> list(int range, int page) throws ProductException, ClassNotFoundException;
    ArrayList<Product> list(String searchText) throws ProductException, ClassNotFoundException;
    ArrayList<Product> list(String attribute, String searchText) throws ProductException, ClassNotFoundException;
    ArrayList<Product> list(String attribute, String searchText, int range, int page) throws ProductException, ClassNotFoundException;


}
