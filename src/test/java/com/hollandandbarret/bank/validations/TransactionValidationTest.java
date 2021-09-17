package com.hollandandbarret.bank.validations;

import com.hollandandbarret.bank.dto.TransactionDetails;
import com.hollandandbarret.bank.exception.BankException;
import com.hollandandbarret.bank.utils.TransactionType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
@ExtendWith(MockitoExtension.class)
class TransactionValidationTest {
    @InjectMocks
    private TransactionValidation transactionValidation;
    TransactionDetails transactionDetails;

    @BeforeEach
    public void setup() {
        Date date = new Date();
        transactionDetails = TransactionDetails.builder().transactionId(1l).transactionType(TransactionType.CREDIT).accountNumber(1l).description("Test").amount(100.0).transactionDate(date)
                .build();
    }

    @Test
    void whenAccountNumberIsInvalidBankExceptionThrown() throws BankException {
        transactionDetails.setAccountNumber(-1);
        Exception exception = assertThrows(BankException.class, () -> {
            transactionValidation.validateInputFields(transactionDetails);
        });

        String expectedMessage = "Invalid Account";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void whenAmountIsLessThanZeroBankExceptionThrown() throws BankException {
        transactionDetails.setAmount(-1);
        Exception exception = assertThrows(BankException.class, () -> {
            transactionValidation.validateInputFields(transactionDetails);
        });
        String expectedMessage = "Amount cannot be less 0";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void whenTransactionDateIsNullBankExceptionThrown() throws BankException {
        transactionDetails.setTransactionDate(null);
        Exception exception = assertThrows(BankException.class, () -> {
            transactionValidation.validateInputFields(transactionDetails);
        });
        String expectedMessage = "Transaction Date is null";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }
}