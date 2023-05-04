package com.billing.app.domain.entity;

public class Unit {
    private int id;
    private String name;
    private String code;
    private String description;
    private boolean isDividable;

    public Unit(int id, String name, String code, String description, boolean isDividable) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.description = description;
        this.isDividable = isDividable;
    }

    public Unit() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isDividable() {
        return isDividable;
    }

    public void setDividable(boolean dividable) {
        isDividable = dividable;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Unit{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", code='" + code + '\'' +
                ", description='" + description + '\'' +
                ", isDividable=" + isDividable +
                '}';
    }
}
