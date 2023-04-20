package com.cs301p.easy_ecomm.utilClasses;

public class FilterBy {
    private Boolean isBetween;
    private String attr;
    private String l_val;
    private String h_val;

    public FilterBy(Boolean isBetween, String attr, String l_val, String h_val) {
        this.isBetween = isBetween;
        this.attr = attr;
        this.l_val = l_val;
        this.h_val = h_val;
    }

    public Boolean getIsBetween() {
        return this.isBetween;
    }

    public void setIsBetween(Boolean isBetween) {
        this.isBetween = isBetween;
    }

    public String getAttr() {
        return this.attr;
    }

    public void setAttr(String attr) {
        this.attr = attr;
    }

    public String getL_val() {
        return this.l_val;
    }

    public void setL_val(String l_val) {
        this.l_val = l_val;
    }

    public String getH_val() {
        return this.h_val;
    }

    public void setH_val(String h_val) {
        this.h_val = h_val;
    }
}
