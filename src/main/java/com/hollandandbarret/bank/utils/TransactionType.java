package com.hollandandbarret.bank.utils;

public enum TransactionType {
    DEBIT ("DEBIT"), CREDIT ("CREDIT");
    private String value;

    TransactionType(final String value) {
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
