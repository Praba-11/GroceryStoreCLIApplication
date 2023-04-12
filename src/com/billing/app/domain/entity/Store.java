package com.billing.app.domain.entity;

public class Store {
    private String name;
    private int phoneNumber;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getGstNumber() {
        return gstNumber;
    }

    public void setGstNumber(int gstNumber) {
        this.gstNumber = gstNumber;
    }

    private String address;
    private int gstNumber;
}
