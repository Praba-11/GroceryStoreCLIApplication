package com.billing.app.domain.entity;

public class SalesItem {
    private String name;
    private int invoice;

    @Override
    public String toString() {
        return "SalesItem{" +
                "name='" + name + '\'' +
                ", invoice=" + invoice +
                ", code='" + code + '\'' +
                ", quantity=" + quantity +
                ", costPrice=" + costPrice +
                '}';
    }

    public int getInvoice() {
        return invoice;
    }

    public void setInvoice(int invoice) {
        this.invoice = invoice;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String code;
    private float quantity;
    private float costPrice;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public float getQuantity() {
        return quantity;
    }

    public void setQuantity(float quantity) {
        this.quantity = quantity;
    }

    public float getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(float costPrice) {
        this.costPrice = costPrice;
    }


}
