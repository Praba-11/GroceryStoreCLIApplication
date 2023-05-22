package com.billing.app.domain.service.purchase;

import com.billing.app.domain.entity.Purchase;
import com.billing.app.domain.exceptions.CodeNotFoundException;
import com.billing.app.domain.exceptions.InvalidArgumentException;

import java.sql.SQLException;
import java.util.List;

public interface PurchaseServiceInterface {
    Purchase create(Purchase purchase) throws SQLException, CodeNotFoundException;
    boolean delete(int invoice) throws CodeNotFoundException, SQLException, ClassNotFoundException;
    List<Purchase> list(int range, int page, String attribute, String searchText) throws SQLException, ClassNotFoundException, InvalidArgumentException;
    int count(String from, String to) throws SQLException, ClassNotFoundException;
}
