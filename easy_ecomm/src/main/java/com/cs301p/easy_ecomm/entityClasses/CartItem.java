package com.cs301p.easy_ecomm.entityClasses;

public class CartItem {
    private int customerId;
    private int productId;
    private int quantity;


    public CartItem() {
    }

    public CartItem(int customerId, int productId, int quantity) {
        this.customerId = customerId;
        this.productId = productId;
        this.quantity = quantity;
    }

    public int getCustomerId() {
        return this.customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getProductId() {
        return this.productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return this.quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "{" +
            "\n customerId='" + getCustomerId() + "'" +
            ",\n productId='" + getProductId() + "'" +
            ",\n quantity='" + getQuantity() + "'" +
            "\n}";
    }
}
