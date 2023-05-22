package com.billing.app.domain.service.sale;

import com.billing.app.domain.database.*;
import com.billing.app.domain.entity.Sales;
import com.billing.app.domain.entity.SalesItem;
import com.billing.app.domain.exceptions.CodeNotFoundException;
import com.billing.app.domain.exceptions.InvalidArgumentException;

import java.sql.SQLException;
import java.util.List;

public class SalesService implements SalesServiceInterface {
    ProductDAO productDAO = new ProductDAOImplementation();
    SalesDAO salesDAO = new SalesDAOImplementation();

    public Sales create(Sales sales) throws SQLException, ClassNotFoundException, CodeNotFoundException {

        float grandTotal = 0;

        List<SalesItem> listOfSalesItem = sales.getListOfSalesItem();
        for (SalesItem salesItem : listOfSalesItem) {

            SalesItem newSalesItem = new SalesItem();

            newSalesItem.setCode(salesItem.getCode());
            newSalesItem.setQuantity(salesItem.getQuantity());
            newSalesItem.setCostPrice(productDAO.getPrice(salesItem.getCode()));

            grandTotal += newSalesItem.getCostPrice() * newSalesItem.getQuantity();

        }
        sales.setGrandTotal(grandTotal);
        salesDAO.setGrandTotal(sales);
        return sales;
    }


    public boolean delete(int invoice) throws CodeNotFoundException, SQLException, ClassNotFoundException {
        boolean isDeleted;
        isDeleted = salesDAO.delete(invoice);
        if (!isDeleted) {
            throw new CodeNotFoundException("(Invoice no: " + invoice + ") not present in sales relational table.");
        }
        return true;
    }

    public List<Sales> list(int range, int page, String attribute, String searchText) throws SQLException, ClassNotFoundException, InvalidArgumentException {
        List<Sales> list;
        if (attribute == null && searchText != null && range == 0 && page == 0) {
            list = salesDAO.list(searchText);
        } else {
            if (attribute == null && searchText == null && range == 0 && page == 0) {
                attribute = "invoice_id";
                searchText = "";
                range = salesDAO.count("01-01-1970", "01-01-40000");
            } else if (attribute == null && searchText == null && range > 0 && page == 0) {
                attribute = "invoice_id";
                searchText = "";
            } else if (attribute == null && searchText == null && range > 0 && page > 0) {
                attribute = "invoice_id";
                searchText = "";
                page = (page - 1) * range;
            } else if (attribute != null && searchText != null && range == 0 && page == 0) {
                range = salesDAO.count("01-01-1970", "01-01-40000");
            } else if (attribute != null && searchText != null && range > 0 && page > 0) {
                page = (page - 1) * range;
            } else {
                throw new InvalidArgumentException("Invalid argument provided. Please provide valid arguments as per template.");
            }
            list = salesDAO.list(range, page, attribute, searchText);
        }
        return list;
    }

    public int count(String from, String to) throws SQLException, ClassNotFoundException {
        int count = salesDAO.count(from, to);
        return count;
    }
}
