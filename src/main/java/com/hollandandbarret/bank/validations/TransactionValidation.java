package com.hollandandbarret.bank.validations;

import com.hollandandbarret.bank.dto.TransactionDetails;
import com.hollandandbarret.bank.errors.BankErrors;
import com.hollandandbarret.bank.exception.BankException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class TransactionValidation {
    Logger logger = LoggerFactory.getLogger(TransactionValidation.class);
    public void validateInputFields(TransactionDetails transactionDetails) throws BankException {
        if (transactionDetails.getAccountNumber() <=0 ) {
            throw new BankException(BankErrors.INVALID_ACCOUNT);
        }
        if (transactionDetails.getAmount() <=0 ) {
            throw new BankException(BankErrors.INVALID_AMOUNT);
        }
        if (transactionDetails.getTransactionDate() == null) {
            throw new BankException(BankErrors.DATE_NULL);
        }

    }
}
