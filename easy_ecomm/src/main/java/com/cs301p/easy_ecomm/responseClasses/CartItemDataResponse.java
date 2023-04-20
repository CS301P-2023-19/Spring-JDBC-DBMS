package com.cs301p.easy_ecomm.responseClasses;

public class CartItemDataResponse {
    private String name;
    private Float price;
    private int quantity;
    private int productId;


    public CartItemDataResponse() {
    }

    public CartItemDataResponse(String name, Float price, int quantity, int productId) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.productId = productId;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getPrice() {
        return this.price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public int getQuantity() {
        return this.quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getProductId() {
        return this.productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    @Override
    public String toString() {
        return "{" +
            "'\n name='" + getName() + "'" +
            ",\n price='" + getPrice() + "'" +
            ",\n quantity='" + getQuantity() + "'" +
            ",\n productId='" + getProductId() + "'" +
            "\n}";
    }

}
