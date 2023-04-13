package com.billing.app.domain.entity;

public class Store {
    private String name;
    private long phoneNumber;
    private String address;
    private long gstNumber;

    public Store(String name, long phoneNumber, String address, long gstNumber) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.gstNumber = gstNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(long phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public long getGstNumber() {
        return gstNumber;
    }

    public void setGstNumber(long gstNumber) {
        this.gstNumber = gstNumber;
    }

}
