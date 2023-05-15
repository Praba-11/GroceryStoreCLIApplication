package com.billing.app.domain.entity;

import java.util.Date;

public class Sales {
    private Date date;
    private int invoice;
    SalesItem salesItem;
    int grandTotal;

    public int getGrandTotal() {
        return grandTotal;
    }

    public void setGrandTotal(int grandTotal) {
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

    public SalesItem getSalesItem() {
        return salesItem;
    }

    public void setSalesItem(SalesItem salesItem) {
        this.salesItem = salesItem;
    }
}
