package com.billing.app.domain.presentation.sale;

import com.billing.app.domain.entity.Sales;
import com.billing.app.domain.entity.SalesItem;
import com.billing.app.domain.exceptions.NotFoundException;
import com.billing.app.domain.exceptions.InvalidArgumentException;
import com.billing.app.domain.exceptions.NegativeStockException;
import com.billing.app.domain.exceptions.TemplateMismatchException;
import com.billing.app.domain.service.sale.SalesServiceImplementation;
import com.billing.app.domain.service.sale.SalesService;

import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SalesController {
    Sales sales = new Sales();
    SalesItem salesItem;
    List<SalesItem> listOfSalesItem = new ArrayList<>();
    List<String> salesItemDetails;
    SalesService salesService = new SalesServiceImplementation();
    SalesValidator salesValidator = new SalesValidator();
    List<SalesItem> salesItems;
    SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");


    public Sales create(List<String> stringArrayList) throws ParseException, SQLException, ClassNotFoundException, NotFoundException, TemplateMismatchException, NegativeStockException {

        List<String> create = new ArrayList<>(stringArrayList.subList(0, 2));
        List<String> salesItemDetails = new ArrayList<>(stringArrayList.subList(2, stringArrayList.size()));


        sales = new Sales();
        salesItems = new ArrayList<>();
        float grandTotal = 0;

        if (salesItemDetails.size() % 2 == 0) {
            for (int index = 0; index < salesItemDetails.size(); index += 2) {
                salesItem = new SalesItem();
                salesItem.setCode(salesItemDetails.get(index));
                salesItem.setQuantity(Float.parseFloat(salesItemDetails.get(index + 1)));
                grandTotal += (salesItem.getQuantity() * salesItem.getCostPrice());
                salesItems.add(salesItem);

            }
        } else {
            throw new TemplateMismatchException("Incompatible purchase item details. Please provide according to the template.");
        }
        sales.setDate(new Date(format.parse(create.get(1)).getTime()));
        sales.setInvoice(Integer.parseInt(create.get(0)));
        sales.setListOfSalesItem(salesItems);
        sales.setGrandTotal(grandTotal);
        return salesService.create(sales);
    }

    public boolean delete(String value) throws TemplateMismatchException, SQLException, NotFoundException, ClassNotFoundException, InvalidArgumentException {
        boolean flag = false;
        int invoice;
        try {
            invoice = Integer.parseInt(value);
        } catch (NumberFormatException exception) {
            throw new InvalidArgumentException("Incompatible invoice number. Invoice cannot be string.");
        }
        flag = salesService.delete(invoice);
        return flag;
    }

    public List<Sales> list(List<String> values) throws InvalidArgumentException, TemplateMismatchException, SQLException, ClassNotFoundException {

        int range, page;
        String attribute, searchText;
        Map<String, Object> parameters = salesValidator.validateList(values);

        range = Integer.parseInt(parameters.get("range").toString());
        page = Integer.parseInt(parameters.get("page").toString());
        attribute = (String) parameters.get("attribute");
        searchText = (String) parameters.get("searchtext");

        return salesService.list(range, page, attribute, searchText);

    }

    public int count(List<String> values) throws SQLException, ClassNotFoundException, TemplateMismatchException {
        int actualLength;
        actualLength = values.size();
        if (actualLength == 0) {
            return salesService.count("1970-01-01", "40000-01-01");
        }
        else if (actualLength == 1) {
            String date = values.get(0);
            return salesService.count(date, date);
        } else if (actualLength == 2) {
            String from, to;
            from = values.get(0);
            to = values.get(1);
            return salesService.count(from, to);
        } else {
            throw new TemplateMismatchException("Invalid argument length. Provided: " + actualLength);
        }

    }
}
