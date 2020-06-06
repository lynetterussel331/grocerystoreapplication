package com.titusgt.grocerystoreapplication.enums;

public enum ProductType {

    PIECE("PC"),
    BULK("BULK"),
    SALE("SALE");

    private String label;

    ProductType(String label) {
        this.label = label;
    }

    public String get() {
        return label;
    }
}
