package com.cs301p.easy_ecomm.entityClasses;

public class Customer {
    private int id;
    private String name;
    private String email;
    private String password;
    private String phone;
    private String address;
    private int walletId;

    public Customer() {
    }

    public Customer(int id, String name, String email, String password, String phone, String address, int walletId) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.address = address;
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

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getWalletId() {
        return this.walletId;
    }

    public void setWalletId(int walletId) {
        this.walletId = walletId;
    }

    @Override
    public String toString() {
        // return "{" +
        // "\n id='" + getId() + "'" +
        // ",\n name='" + getName() + "'" +
        // ",\n email='" + getEmail() + "'" +
        // ",\n password='" + getPassword() + "'" +
        // ",\n phone='" + getPhone() + "'" +
        // ",\n address='" + getAddress() + "'" +
        // ",\n walletId='" + getWalletId() + "'" +
        // "\n}";

        return "{" +
                "\n id='" + getId() + "'" +
                ",\n name='" + getName() + "'" +
                ",\n email='" + getEmail() + "'" +
                ",\n phone='" + getPhone() + "'" +
                ",\n address='" + getAddress() + "'" +
                ",\n walletId='" + getWalletId() + "'" +
                "\n}";
    }
}
