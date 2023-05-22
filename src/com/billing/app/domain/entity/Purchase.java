package com.billing.app.domain.entity;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Purchase {
    private Date date;
    private int invoice;
    private List<PurchaseItem> listOfPurchaseItem;
    private float grandTotal;

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

    public List<PurchaseItem> getListOfPurchaseItem() {
        return listOfPurchaseItem;
    }

    public void setListOfPurchaseItem(List<PurchaseItem> listOfPurchaseItem) {
        this.listOfPurchaseItem = listOfPurchaseItem;
    }

    @Override
    public String toString() {
        return "Purchase{" +
                "date=" + date +
                ", invoice=" + invoice +
                ", listOfPurchaseItem=" + listOfPurchaseItem +
                ", grandTotal=" + grandTotal +
                '}';
    }
}
