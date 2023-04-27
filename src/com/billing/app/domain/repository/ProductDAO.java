package com.billing.app.domain.repository;

import com.billing.app.domain.entity.Product;

import java.util.ArrayList;

public interface ProductDAO {
    boolean create(Product product) throws Throwable;
    boolean edit(Product product) throws Throwable;
    boolean delete(String code) throws CustomException;
    ArrayList<Product> list() throws CustomException;
    ArrayList<Product> list(int range) throws CustomException;
    ArrayList<Product> list(int range, int page) throws CustomException;
    ArrayList<Product> list(String searchText) throws CustomException;
    ArrayList<Product> list(String attribute, String searchText) throws CustomException;
    ArrayList<Product> list(String attribute, String searchText, int range, int page) throws CustomException;
    int getStock(String code) throws CustomException;
    Product getProduct(String code) throws CustomException;
    int getCount() throws CustomException;

}
