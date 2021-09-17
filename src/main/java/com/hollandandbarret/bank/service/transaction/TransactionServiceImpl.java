package com.hollandandbarret.bank.service.transaction;

import com.hollandandbarret.bank.domain.model.Transaction;
import com.hollandandbarret.bank.domain.repository.TransactionRepository;
import com.hollandandbarret.bank.dto.AccountDto;
import com.hollandandbarret.bank.dto.BalanceDto;
import com.hollandandbarret.bank.dto.CustomerDto;
import com.hollandandbarret.bank.dto.TransactionDetails;
import com.hollandandbarret.bank.errors.BankErrors;
import com.hollandandbarret.bank.exception.BankException;
import com.hollandandbarret.bank.service.account.AccountService;
import com.hollandandbarret.bank.service.customer.CustomerService;
import com.hollandandbarret.bank.utils.TransactionType;
import com.hollandandbarret.bank.validations.TransactionValidation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
/**
 * Created by Savitha Shinto.
 */
@Service
public class TransactionServiceImpl implements TransactionService {
    Logger logger = LoggerFactory.getLogger(TransactionService.class);
    private final TransactionRepository transactionRepository;
    private final AccountService accountService;
    private final CustomerService customerService;
    private final TransactionValidation transactionValidation;
    public TransactionServiceImpl(TransactionRepository transactionRepository, AccountService accountService, CustomerService customerService, TransactionValidation transactionValidation) {
        this.transactionRepository = transactionRepository;
        this.accountService = accountService;
        this.customerService = customerService;
        this.transactionValidation = transactionValidation;
    }

    @Override
    public TransactionDetails create(TransactionDetails transactionDetails) throws BankException {
        logger.info("Create Transactions");
        transactionValidation.validateInputFields(transactionDetails);
        try {
            Transaction transaction =  transactionRepository.save(mapDtotoEntity(transactionDetails));
            return getTransactionDto(transaction);
        } catch (Exception ex) {
            throw new BankException(BankErrors.UNKNOWN_ERROR);
        }
    }


    @Override
    public List<BalanceDto> getTransactions(long customerNumber, LocalDateTime from, LocalDateTime to) throws BankException {
        logger.info("Get transactions");
        List<BalanceDto> balanceDtoList = new ArrayList<>();
        CustomerDto customer = customerService.getCustomer(customerNumber);
        List<AccountDto> accounts = accountService.getAllAccountByCustomerId(customerNumber);
        for(AccountDto accountDto: accounts) {
            List<Transaction> transactions = transactionRepository.findTransactionByAccountNumberAndDate(accountDto.getAccountNumber(), from, to);
            List<TransactionDetails> transactionDetails = getListOfTransactionsDto(transactions);
            BalanceDto balanceDto = new BalanceDto();
            balanceDto.setAccountType(accountDto.getAccountType());
            balanceDto.setAccountNumber(accountDto.getAccountNumber());
            balanceDto.setTotalBalance(getBalance(transactionDetails));
            balanceDto.setTransactionFromDate(from);
            balanceDto.setTransactionToDate(to);
            balanceDto.setAmountCredited(getTransactionTypeBalnce(transactionDetails, TransactionType.CREDIT));
            balanceDto.setAmountDebited(getTransactionTypeBalnce(transactionDetails, TransactionType.DEBIT));
            balanceDto.setTransactionDetails(transactionDetails);
            balanceDtoList.add(balanceDto);
        }

        return balanceDtoList;
    }

    private double getBalance(List<TransactionDetails> transactionDetails) {
        double balance = 0;
        for(TransactionDetails transactionDetail : transactionDetails) {
            if (transactionDetail.getTransactionType() == TransactionType.CREDIT) {
                balance = balance + transactionDetail.getAmount();
            } else {
                balance = balance - transactionDetail.getAmount();
            }
        }
        return balance;

    }
    private double getTransactionTypeBalnce(List<TransactionDetails> transactionDetails, TransactionType transactionType ) {
        double balance = 0;
        for(TransactionDetails transactionDetail : transactionDetails) {
            if (transactionDetail.getTransactionType() == transactionType ) {
                balance = balance + transactionDetail.getAmount();
            }
        }
        return balance;

    }


    //
    private Transaction mapDtotoEntity(TransactionDetails transactionDetails) throws BankException {
        Transaction transaction = new Transaction();
        transaction.setAccountNumber(transactionDetails.getAccountNumber());
        transaction.setAmount(transactionDetails.getAmount());
        transaction.setTransactionDate(transactionDetails.getTransactionDate());
        transaction.setDescription(transactionDetails.getDescription());
        transaction.setTransactionType(transactionDetails.getTransactionType().getValue());
        return transaction;
    }

    private List<TransactionDetails> getListOfTransactionsDto(List<Transaction> transactions) throws BankException {
        List<TransactionDetails> transactionDetails = new ArrayList<>();
        if(transactions !=null) {
            for (Transaction rec : transactions) {
                transactionDetails.add(getTransactionDto(rec));
            }
        }
        return transactionDetails;
    }

    private TransactionDetails getTransactionDto(Transaction transaction) throws BankException {
        TransactionDetails transactionDetails = new TransactionDetails();
        transactionDetails.setAccountNumber(transaction.getAccountNumber());
        transactionDetails.setTransactionId(transaction.getTransactionId());
        transactionDetails.setAmount(transaction.getAmount());
        transactionDetails.setTransactionDate(transaction.getTransactionDate());
        transactionDetails.setDescription(transaction.getDescription());
        transactionDetails.setTransactionType(TransactionType.valueOf(transaction.getTransactionType()));
        return transactionDetails;

    }
}
