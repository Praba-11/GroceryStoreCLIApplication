package com.billing.app.domain.presentation.sale;

import com.billing.app.domain.entity.Sales;
import com.billing.app.domain.entity.SalesItem;
import com.billing.app.domain.exceptions.CodeNotFoundException;
import com.billing.app.domain.exceptions.InvalidArgumentException;
import com.billing.app.domain.exceptions.TemplateMismatchException;
import com.billing.app.domain.service.sale.SalesService;
import com.billing.app.domain.service.sale.SalesServiceInterface;

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
    List<String> salesItemDetails;
    List<SalesItem> salesItems;
    SalesServiceInterface salesServiceInterface = new SalesService();
    SalesCLIValidator salesCLIValidator = new SalesCLIValidator();
    SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");


    public Sales create(ArrayList<String> stringArrayList) throws ParseException, SQLException, ClassNotFoundException, CodeNotFoundException, TemplateMismatchException {

        List<String> create = new ArrayList<>(stringArrayList.subList(1, 3));
        List<String> items = new ArrayList<>(stringArrayList.subList(3, stringArrayList.size()));
        for (String item : items) {
            item = item.replaceAll("\\[|\\]", "");
            create.add(item);
        }
        salesItems = new ArrayList<>();
        salesItemDetails = new ArrayList<>(create.subList(3, create.size()));
        System.out.println(salesItemDetails);
        if (salesItemDetails.size() % 2 == 0) {
            for (int index = 0; index < salesItemDetails.size(); index += 2) {
                salesItem = new SalesItem();
                salesItem.setCode(salesItemDetails.get(index));
                salesItem.setQuantity(Float.parseFloat(salesItemDetails.get(index + 1)));

            }
        } else {
            throw new TemplateMismatchException("Incompatible purchase item details. Please provide according to the template.");
        }
        sales.setDate(new Date(format.parse(create.get(1)).getTime()));
        sales.setInvoice(Integer.parseInt(create.get(2)));
        sales.setListOfSalesItem(salesItems);
        return salesServiceInterface.create(sales);
    }

    public boolean delete(List<String> values) throws TemplateMismatchException, SQLException, CodeNotFoundException, ClassNotFoundException {
        boolean flag = false;
        int expectedLength = 1;
        int actualLength = values.size();

        if (actualLength == expectedLength) {
            int invoice = Integer.parseInt(values.get(0));
            flag = salesServiceInterface.delete(invoice);
            return flag;
        } else {
            throw new TemplateMismatchException("Invalid argument length. Expected: " + expectedLength + ", Provided: " + actualLength);
        }
    }

    public List<Sales> list(List<String> values) throws InvalidArgumentException, TemplateMismatchException, SQLException, ClassNotFoundException {

        int range, page;
        String attribute, searchText;
        Map<String, Object> parameters = salesCLIValidator.validateList(values);

        range = Integer.parseInt(parameters.get("range").toString());
        page = Integer.parseInt(parameters.get("page").toString());
        attribute = (String) parameters.get("attribute");
        searchText = (String) parameters.get("searchtext");

        return salesServiceInterface.list(range, page, attribute, searchText);

    }

    public int count(List<String> values) throws SQLException, ClassNotFoundException, TemplateMismatchException {
        int actualLength;
        actualLength = values.size();
        if (actualLength == 0) {
            return salesServiceInterface.count("1970-01-01", "40000-01-01");
        }
        else if (actualLength == 1) {
            String date = values.get(0);
            return salesServiceInterface.count(date, date);
        } else if (actualLength == 2) {
            String from, to;
            from = values.get(0);
            to = values.get(1);
            return salesServiceInterface.count(from, to);
        } else {
            throw new TemplateMismatchException("Invalid argument length. Provided: " + actualLength);
        }

    }
}
