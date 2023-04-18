package com.cs301p.easy_ecomm.utilClasses;

public class OrderBy {
    private String attr;
    private String direction;   // ASC, DESC

    public OrderBy(String attr, String direction) {
        this.attr = attr;
        this.direction = direction;
    }

    public String getAttr() {
        return this.attr;
    }

    public void setAttr(String attr) {
        this.attr = attr;
    }

    public String getDirection() {
        return this.direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }
}
