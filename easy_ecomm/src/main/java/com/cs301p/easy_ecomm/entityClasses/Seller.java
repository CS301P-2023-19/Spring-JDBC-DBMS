package com.cs301p.easy_ecomm.entityClasses;

public class Seller {
    private int id;
    private String name;
    private String email;
    private String password;
    private String phone;
    private int walletId;

    public Seller() {
    }

    public Seller(int id, String name, String email, String password, String phone, int walletId) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.walletId = walletId;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return this.phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getWalletId() {
        return this.walletId;
    }

    public void setWalletId(int walletId) {
        this.walletId = walletId;
    }

    @Override
    public String toString() {
        return "{" +
            "\n id='" + getId() + "'" +
            ",\n name='" + getName() + "'" +
            ",\n email='" + getEmail() + "'" +
            ",\n password='" + getPassword() + "'" +
            ",\n phone='" + getPhone() + "'" +
            ",\n walletId='" + getWalletId() + "'" +
            "\n}";
    }
}
