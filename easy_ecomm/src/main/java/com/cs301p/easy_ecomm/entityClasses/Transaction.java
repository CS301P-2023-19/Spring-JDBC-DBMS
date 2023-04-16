package com.cs301p.easy_ecomm.entityClasses;

import java.sql.Date;

public class Transaction {
    private int id;
    private int customerId;
    private int sellerId;
    private int productId;
    private Date date;
    private boolean returnStatus;


    public Transaction() {
    }

    public Transaction(int id, int customerId, int sellerId, int productId, Date date, boolean returnStatus) {
        this.id = id;
        this.customerId = customerId;
        this.sellerId = sellerId;
        this.productId = productId;
        this.date = date;
        this.returnStatus = returnStatus;
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

    public int getSellerId() {
        return this.sellerId;
    }

    public void setSellerId(int sellerId) {
        this.sellerId = sellerId;
    }

    public int getProductId() {
        return this.productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public Date getDate() {
        return this.date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public boolean isReturnStatus() {
        return this.returnStatus;
    }

    public boolean getReturnStatus() {
        return this.returnStatus;
    }

    public void setReturnStatus(boolean returnStatus) {
        this.returnStatus = returnStatus;
    }

    @Override
    public String toString() {
        return "{" +
            "\n id='" + getId() + "'" +
            ",\n customerId='" + getCustomerId() + "'" +
            ",\n sellerId='" + getSellerId() + "'" +
            ",\n productId='" + getProductId() + "'" +
            ",\n date='" + getDate() + "'" +
            ",\n returnStatus='" + isReturnStatus() + "'" +
            "\n}";
    }

}
