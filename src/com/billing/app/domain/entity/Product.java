package com.billing.app.domain.entity;

public class Product {
    private String code;
    private String name;
    private String unitCode;
    private String type;
    private float price;
    private int stock;
    private boolean isDeleted;
    public Product() {

    }

    public Product(String code, String name, String unitCode, String type, float price, int stock, boolean isDeleted) {
        this.code = code;
        this.name = name;
        this.unitCode = unitCode;
        this.type = type;
        this.price = price;
        this.stock = stock;
        this.isDeleted = isDeleted;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUnitCode() {
        return unitCode;
    }

    public void setUnitCode(String unitCode) {
        this.unitCode = unitCode;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    @Override
    public String toString() {
        return "Product{" +
                "code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", unitCode='" + unitCode + '\'' +
                ", type='" + type + '\'' +
                ", price=" + price +
                ", stock=" + stock +
                ", isDeleted=" + isDeleted +
                '}';
    }

}
