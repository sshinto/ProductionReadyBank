package com.hollandandbarret.bank.exception;

import com.hollandandbarret.bank.errors.BankErrors;
import org.springframework.http.HttpStatus;

/**
 * Created by Savitha Shinto.
 */
public class BankException extends Exception {
    private BankErrors bankErrors;

    public BankException(String errors, HttpStatus internalServerError) {
        super(errors);
    }

    public BankErrors getBankErrors() {
        return bankErrors;
    }

    public BankException(BankErrors bankErrors) {
        super(bankErrors.getErrorMessage());
        this.bankErrors = bankErrors;
    }
}
