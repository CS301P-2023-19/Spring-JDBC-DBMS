package com.cs301p.easy_ecomm.entityClasses;

public class Wallet {
    private int id;
    private String credit_card_no;
    private Float money;


    public Wallet() {
    }

    public Wallet(int id, String credit_card_no, Float money) {
        this.id = id;
        this.credit_card_no = credit_card_no;
        this.money = money;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCredit_card_no() {
        return this.credit_card_no;
    }

    public void setCredit_card_no(String credit_card_no) {
        this.credit_card_no = credit_card_no;
    }

    public Float getMoney() {
        return this.money;
    }

    public void setMoney(Float money) {
        this.money = money;
    }

    @Override
    public String toString() {
        return "{" +
            "\n id='" + getId() + "'" +
            ",\n credit_card_no='" + getCredit_card_no() + "'" +
            ",\n money='" + getMoney() + "'" +
            "\n}";
    }

}
