package com.cs301p.easy_ecomm.entityClasses;

public class Product {
    private int id;
    private String type;
    private String name;
    private int sellerId;
    private Float price;
    private int quantityAvailable;


    public Product() {
    }

    public Product(int id, String type, String name, int sellerId, Float price, int quantityAvailable) {
        this.id = id;
        this.type = type;
        this.name = name;
        this.sellerId = sellerId;
        this.price = price;
        this.quantityAvailable = quantityAvailable;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSellerId() {
        return this.sellerId;
    }

    public void setSellerId(int sellerId) {
        this.sellerId = sellerId;
    }

    public Float getPrice() {
        return this.price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public int getQuantityAvailable() {
        return this.quantityAvailable;
    }

    public void setQuantityAvailable(int quantityAvailable) {
        this.quantityAvailable = quantityAvailable;
    }

    @Override
    public String toString() {
        return "{" +
            "\n id='" + getId() + "'" +
            ",\n type='" + getType() + "'" +
            ",\n name='" + getName() + "'" +
            ",\n sellerId='" + getSellerId() + "'" +
            ",\n price='" + getPrice() + "'" +
            ",\n quantityAvailable='" + getQuantityAvailable() + "'" +
            "\n}";
    }
}
