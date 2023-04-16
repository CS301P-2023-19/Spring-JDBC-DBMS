package com.cs301p.easy_ecomm.entityClasses;

public class Admin {
    private int id;
    private String a_name;
    private String a_password;


    public Admin() {
    }

    public Admin(int id, String a_name, String a_password) {
        this.id = id;
        this.a_name = a_name;
        this.a_password = a_password;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getA_name() {
        return this.a_name;
    }

    public void setA_name(String a_name) {
        this.a_name = a_name;
    }

    public String getA_password() {
        return this.a_password;
    }

    public void setA_password(String a_password) {
        this.a_password = a_password;
    }

    @Override
    public String toString() {
        return "{" +
            "\n id='" + getId() + "'" +
            ",\n a_name='" + getA_name() + "'" +
            ",\n a_password='" + getA_password() + "'" +
            "\n}";
    }
}
