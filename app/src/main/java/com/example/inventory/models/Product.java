package com.example.inventory.models;

public class Product {
    String ID, name, price, qty, desc, brand;
//    String DOP, MRP, BUYDT, SELDT, COMname, modelnum,pics, desc, Barcode;

    public Product(){}

    public Product(String ID, String name, String price, String qty, String desc, String brand) {
        this.ID = ID;
        this.name = name;
        this.price = price;
        this.qty = qty;
        this.desc = desc;
        this.brand = brand;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }
}
