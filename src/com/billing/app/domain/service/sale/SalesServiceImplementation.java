package com.billing.app.domain.service.sale;

import com.billing.app.domain.database.*;
import com.billing.app.domain.entity.Product;
import com.billing.app.domain.entity.Sales;
import com.billing.app.domain.entity.SalesItem;
import com.billing.app.domain.exceptions.NotFoundException;
import com.billing.app.domain.exceptions.InvalidArgumentException;
import com.billing.app.domain.exceptions.NegativeStockException;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SalesServiceImplementation implements SalesService {
    ProductDAO productDAO = new ProductDAOImplementation();
    SalesDAO salesDAO = new SalesDAOImplementation();
    SalesItemDAO salesItemDAO = new SalesItemDAOImplementation();

    public Sales create(Sales sales) throws SQLException, NotFoundException, NegativeStockException {

        float grandTotal = 0;
        List<SalesItem> listOfSalesItem = new ArrayList<>();
        salesDAO.create(sales);
        for (SalesItem salesItem : sales.getListOfSalesItem()) {

            salesItem.setInvoice(sales.getInvoice());
            salesItem.setName(productDAO.findByCode(salesItem.getCode()).getName());
            salesItem.setCostPrice(productDAO.findByCode(salesItem.getCode()).getPrice());

            Product product = productDAO.findByCode(salesItem.getCode());
            float stock = productDAO.findByCode(salesItem.getCode()).getStock() - salesItem.getQuantity();
            if (stock > 0) {
                product.setStock(stock);
            } else {
                throw new NegativeStockException("Stock already minimum. Cannot perform sales operation.");
            }
            productDAO.edit(product);

            grandTotal += salesItem.getCostPrice() * salesItem.getQuantity();

            listOfSalesItem.add(salesItem);

            salesItemDAO.create(salesItem);
        }
        sales.setGrandTotal(grandTotal);
        salesDAO.setGrandTotal(sales);
        sales.setListOfSalesItem(listOfSalesItem);
        return sales;
    }


    public boolean delete(int invoice) throws NotFoundException, SQLException {
        boolean isDeleted;
        isDeleted = salesDAO.delete(invoice);
        if (!isDeleted) {
            throw new NotFoundException("(Invoice no: " + invoice + ") not present in sales relational table.");
        }
        return true;
    }

    public List<Sales> list(int range, int page, String attribute, String searchText) throws SQLException, InvalidArgumentException {
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

    public int count(String from, String to) throws SQLException {
        int count = salesDAO.count(from, to);
        return count;
    }
}
