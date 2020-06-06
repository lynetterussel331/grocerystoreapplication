package com.titusgt.grocerystoreapplication.utils;

public enum ProductType {

    PIECE("PC"),
    BULK("BULK"),
    SALE("SALE"),
    BUY1_GET1("Buy 1 Get 1");

    private String label;

    ProductType(String label) {
        this.label = label;
    }

    public String get() {
        return label;
    }
}
