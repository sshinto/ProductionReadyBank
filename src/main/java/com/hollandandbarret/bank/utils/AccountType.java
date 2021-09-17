package com.hollandandbarret.bank.utils;

public enum AccountType {
    SAVINGS ("SAVINGS"),
    CURRENT ("CURRENT");
    private String value;

    AccountType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return this.getValue();
    }
}