package com.example.ShopProject.utils;

public enum ProductType {
    FOOD("food"), DRINKS("drinks"), SANITARY("sanitary"), MAKEUP("makeup"), OTHERS("others");

    public String type;

    ProductType(String type) {
        this.type = type;
    }

    public  String getValue() {
        return this.type;
    }
}