package com.billing.app.domain.repository;
import com.billing.app.domain.entity.Product;

import java.sql.SQLException;
import java.util.ArrayList;

public interface ProductDAO {
    void create(Product product) throws Throwable;
    void edit(String code, ArrayList arrayList) throws SQLException, ClassNotFoundException;
    void delete(String code) throws SQLException, ClassNotFoundException;
    void list(int range) throws SQLException, ClassNotFoundException;
    void list(int range, int page) throws SQLException, ClassNotFoundException;
    void list(String searchText) throws SQLException, ClassNotFoundException;
    void list(String attribute, String searchText) throws SQLException, ClassNotFoundException;
    void list(String attribute, String searchText, int range, int page) throws SQLException, ClassNotFoundException;

}
