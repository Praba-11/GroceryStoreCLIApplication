package com.billing.app.domain.database;

import com.billing.app.domain.entity.Purchase;

import java.sql.SQLException;
import java.util.List;

public interface PurchaseDAO {
    Purchase create(Purchase purchase) throws SQLException;
    boolean find(String code) throws SQLException;
    boolean delete(int invoice) throws SQLException, ClassNotFoundException;
    List<Purchase> list(int range, int page, String attribute, String searchText) throws SQLException, ClassNotFoundException;
    List<Purchase> list(String searchText) throws SQLException, ClassNotFoundException;
    int count(String from, String to) throws SQLException, ClassNotFoundException;
}
