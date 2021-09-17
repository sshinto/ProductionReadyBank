package com.hollandandbarret.bank.errors;

import org.springframework.http.HttpStatus;

public enum BankErrors {

    INVALID_CUSTOMER("Invalid Customer", 100, HttpStatus.BAD_REQUEST),
    INVALID_ACCOUNT("Invalid Account", 103, HttpStatus.BAD_REQUEST),
    UNKNOWN_ERROR("Unknown Error", 999, HttpStatus.BAD_REQUEST),
    DATE_NULL("Transaction Date is null", 101, HttpStatus.BAD_REQUEST),
    INVALID_AMOUNT("Amount cannot be less 0", 102, HttpStatus.BAD_REQUEST);

    private final String errorMessage;
    private final long errorCode;
    private final HttpStatus httpStatusCode;

    BankErrors(String errorMessage, long errorCode, HttpStatus httpStatusCode) {
        this.errorMessage = errorMessage;
        this.errorCode = errorCode;
        this.httpStatusCode = httpStatusCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public long getErrorCode() {
        return errorCode;
    }

    public HttpStatus getHttpStatusCode() {
        return httpStatusCode;
    }
}
