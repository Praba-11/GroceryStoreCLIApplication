package com.billing.app.domain.entity;
import java.util.ArrayList;
import java.util.Date;

public class Purchase {
    private Date date;
    private int invoice;
    ArrayList<PurchaseItem> listOfPurchaseItem;

    public Date getDate() {
        return date;
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

    public ArrayList<PurchaseItem> getListOfPurchaseItem() {
        return listOfPurchaseItem;
    }

    public void setListOfPurchaseItem(ArrayList<PurchaseItem> listOfPurchaseItem) {
        this.listOfPurchaseItem = listOfPurchaseItem;
    }

    @Override
    public String toString() {
        return "Purchase{" +
                "date=" + date +
                ", invoice=" + invoice +
                ", listOfPurchaseItem=" + listOfPurchaseItem +
                '}';
    }
}
