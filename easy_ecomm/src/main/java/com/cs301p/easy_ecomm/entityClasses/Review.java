package com.cs301p.easy_ecomm.entityClasses;

public class Review {
    private int id;
    private int customerId;
    private int productId;
    private int stars;
    private String content;    


    public Review() {
    }

    public Review(int id, int customerId, int productId, int stars, String content) {
        this.id = id;
        this.customerId = customerId;
        this.productId = productId;
        this.stars = stars;
        this.content = content;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
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
            ",\n customerId='" + getCustomerId() + "'" +
            ",\n productId='" + getProductId() + "'" +
            ",\n stars='" + getStars() + "'" +
            ",\n content='" + getContent() + "'" +
            "\n}";
    }
}
