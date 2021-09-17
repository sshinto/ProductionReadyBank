package com.hollandandbarret.bank.service.transaction;
import com.hollandandbarret.bank.dto.BalanceDto;
import com.hollandandbarret.bank.dto.TransactionDetails;
import com.hollandandbarret.bank.exception.BankException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by Savitha Shinto.
 */
public interface TransactionService {
    TransactionDetails create(TransactionDetails transactionDetails) throws BankException;
    List<BalanceDto> getTransactions(long customerNumber, LocalDateTime from, LocalDateTime to) throws BankException;

}
