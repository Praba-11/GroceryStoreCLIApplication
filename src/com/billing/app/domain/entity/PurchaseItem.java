package com.billing.app.domain.entity;

public class PurchaseItem {
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

    @Override
    public String toString() {
        return "PurchaseItem{" +
                "code='" + code + '\'' +
                ", quantity=" + quantity +
                ", costPrice=" + costPrice +
                '}';
    }
}
