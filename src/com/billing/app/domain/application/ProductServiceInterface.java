package com.billing.app.domain.application;

import com.billing.app.domain.entity.Product;
import java.util.ArrayList;

public interface ProductServiceInterface {
    Product create(Product product) throws Throwable;
    Product edit(Product product, ArrayList arrayList) throws Throwable;
    boolean delete(String code) throws Throwable;
    ArrayList<Product> list() throws Throwable;
    ArrayList<Product> list(int range) throws Throwable;
    ArrayList<Product> list(int range, int page) throws Throwable;
    ArrayList<Product> list(String searchText) throws Throwable;
    ArrayList<Product> list(String attribute, String searchText) throws Throwable;
    ArrayList<Product> list(String attribute, String searchText, int range, int page) throws Throwable;


}
