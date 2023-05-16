package com.billing.app.domain.database;

import com.billing.app.domain.entity.Purchase;
import com.billing.app.domain.entity.Sales;

import java.sql.SQLException;
import java.util.List;

public interface SalesDAO {
    Sales create(Sales sales) throws SQLException, ClassNotFoundException;
    boolean find(String code) throws SQLException, ClassNotFoundException;
    boolean delete(int invoice) throws SQLException, ClassNotFoundException;
    List<Sales> list(int range, int page, String attribute, String searchText) throws SQLException, ClassNotFoundException;
    List<Sales> list(String searchText) throws SQLException, ClassNotFoundException;
    int count(String from, String to) throws SQLException, ClassNotFoundException;
}
