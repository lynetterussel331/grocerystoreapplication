package com.titusgt.grocerystoreapplication.enums;

public enum SaleType {

    BUY1_GET1("Buy 1 Get 1");

    private String label;

    SaleType(String label) {
        this.label = label;
    }

    public String get() {
        return label;
    }
}
