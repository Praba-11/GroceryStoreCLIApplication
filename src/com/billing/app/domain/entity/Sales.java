package com.billing.app.domain.entity;

import java.util.Date;
import java.util.List;

public class Sales {
    private Date date;
    private int invoice;
    List<SalesItem> listOfSalesItem;
    float grandTotal;

    public float getGrandTotal() {
        return grandTotal;
    }

    public void setGrandTotal(float grandTotal) {
        this.grandTotal = grandTotal;
    }

    public java.sql.Date getDate() {
        return (java.sql.Date) date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getInvoice() {
        return invoice;
    }

    public void setInvoice(int invoice) {
        this.invoice = invoice;
    }

    public List<SalesItem> getListOfSalesItem() {
        return listOfSalesItem;
    }

    public void setListOfSalesItem(List<SalesItem> listOfSalesItem) {
        this.listOfSalesItem = listOfSalesItem;
    }

    @Override
    public String toString() {
        return "Sales{" +
                "date=" + date +
                ", invoice=" + invoice +
                ", listOfSalesItem=" + listOfSalesItem +
                ", grandTotal=" + grandTotal +
                '}';
    }
}
