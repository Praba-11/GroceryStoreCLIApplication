package com.billing.app.domain.service.sale;

import com.billing.app.domain.entity.Purchase;
import com.billing.app.domain.entity.Sales;
import com.billing.app.domain.exceptions.CodeNotFoundException;
import com.billing.app.domain.exceptions.IllegalArgumentException;

import java.sql.SQLException;
import java.util.List;

public interface SalesServiceInterface {
    Sales create(Sales sales) throws SQLException, ClassNotFoundException, CodeNotFoundException;
    boolean delete(int invoice) throws CodeNotFoundException, SQLException, ClassNotFoundException;
    List<Sales> list(int range, int page, String attribute, String searchText) throws SQLException, ClassNotFoundException, IllegalArgumentException;
    int count(String from, String to) throws SQLException, ClassNotFoundException;
}
