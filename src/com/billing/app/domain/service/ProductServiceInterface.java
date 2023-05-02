package com.billing.app.domain.service;

import com.billing.app.domain.entity.Product;
import com.billing.app.domain.exceptions.ProductException;

import java.util.ArrayList;
import java.util.HashMap;

public interface ProductServiceInterface {
    Product create(Product product) throws ProductException, ClassNotFoundException ;
    Product edit(Product product, HashMap<String, String> map) throws ProductException, ClassNotFoundException, IllegalAccessException, NoSuchFieldException;
    boolean delete(String code) throws ProductException, ClassNotFoundException;
    ArrayList<Product> list() throws ProductException, ClassNotFoundException;
    ArrayList<Product> list(int range) throws ProductException, ClassNotFoundException;
    ArrayList<Product> list(int range, int page) throws ProductException, ClassNotFoundException;
    ArrayList<Product> list(String searchText) throws ProductException, ClassNotFoundException;
    ArrayList<Product> list(String attribute, String searchText) throws ProductException, ClassNotFoundException;
    ArrayList<Product> list(String attribute, String searchText, int range, int page) throws ProductException, ClassNotFoundException;


}
