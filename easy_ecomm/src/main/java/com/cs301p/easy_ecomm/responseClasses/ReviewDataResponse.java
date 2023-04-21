package com.cs301p.easy_ecomm.responseClasses;

public class ReviewDataResponse {
    private int id;
    private int productId;
    private String customerName;
    private String productName;
    private int stars;
    private String content;


    public ReviewDataResponse() {
    }

    public ReviewDataResponse(int id, int productId, String customerName, String productName, int stars, String content) {
        this.id = id;
        this.productId = productId;
        this.customerName = customerName;
        this.productName = productName;
        this.stars = stars;
        this.content = content;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProductId() {
        return this.productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getCustomerName() {
        return this.customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getProductName() {
        return this.productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getStars() {
        return this.stars;
    }

    public void setStars(int stars) {
        this.stars = stars;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "{" +
            "\n id='" + getId() + "'" +
            ",\n productId='" + getProductId() + "'" +
            ",\n customerName='" + getCustomerName() + "'" +
            ",\n productName='" + getProductName() + "'" +
            ",\n stars='" + getStars() + "'" +
            ",\n content='" + getContent() + "'" +
            "\n}";
    }
}
