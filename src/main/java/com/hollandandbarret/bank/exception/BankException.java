package com.hollandandbarret.bank.exception;

import com.hollandandbarret.bank.errors.BankErrors;

/**
 * Created by Savitha Shinto.
 */
public class BankException extends Exception {
    private final BankErrors bankErrors;
    public BankErrors getBankErrors() {
        return bankErrors;
    }

    public BankException(BankErrors bankErrors) {
        super(bankErrors.getErrorMessage());
        this.bankErrors = bankErrors;
    }
}
