package com.money.api.model;

public enum SaleType {

    INCOME("Income"),
    DEBIT("Debit");

    private String desc;

    SaleType(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

}
