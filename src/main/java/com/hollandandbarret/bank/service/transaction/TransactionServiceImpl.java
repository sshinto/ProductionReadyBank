package com.hollandandbarret.bank.service.transaction;

import com.hollandandbarret.bank.domain.model.Transaction;
import com.hollandandbarret.bank.domain.repository.TransactionRepository;
import com.hollandandbarret.bank.dto.*;
import com.hollandandbarret.bank.errors.BankErrors;
import com.hollandandbarret.bank.exception.BankException;
import com.hollandandbarret.bank.service.account.AccountService;
import com.hollandandbarret.bank.utils.TransactionType;
import com.hollandandbarret.bank.validations.TransactionValidation;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.*;
/**
 * Created by Savitha Shinto.
 */
@Service
public class TransactionServiceImpl implements TransactionService {
    Logger logger = LoggerFactory.getLogger(TransactionServiceImpl.class);
    private final TransactionRepository transactionRepository;
    private final AccountService accountService;
    private final TransactionValidation transactionValidation;
    public TransactionServiceImpl(TransactionRepository transactionRepository, AccountService accountService,TransactionValidation transactionValidation) {
        this.transactionRepository = transactionRepository;
        this.accountService = accountService;
        this.transactionValidation = transactionValidation;
    }
    @Autowired
    private ModelMapper modelMapper;

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
    private Transaction mapDtotoEntity(TransactionDetails transactionDetails) {
        return modelMapper.map(transactionDetails, Transaction.class);
    }

    private List<TransactionDetails> getListOfTransactionsDto(List<Transaction> transactions) {
        List<TransactionDetails> transactionDetails = new ArrayList<>();
        if(transactions !=null) {
            for (Transaction rec : transactions) {
                transactionDetails.add(getTransactionDto(rec));
            }
        }
        return transactionDetails;
    }

    private TransactionDetails getTransactionDto(Transaction transaction) {
        return modelMapper.map(transaction, TransactionDetails.class);
    }



    @Override
    public StarlingBankBalance callStarlingBankApi( String accountUid, String token) {
        HttpHeaders headers = new HttpHeaders();
        String url
                = "https://api-sandbox.starlingbank.com/api/v2/accounts/"+ accountUid+"/balance";

        headers.add("Authorization", token);
        headers.setContentType(MediaType.APPLICATION_JSON);
        // create request
        HttpEntity request = new HttpEntity(headers);

        // make a request
        ResponseEntity<StarlingBankTransactionDetails> response = new RestTemplate().exchange(url, HttpMethod.GET, request, StarlingBankTransactionDetails.class);
        //Set the balance
        if( response.getBody() !=null){
            StarlingBankTransactionDetails starlingBankTransactionDetails = response.getBody();
            if(starlingBankTransactionDetails!=null) {
                AvailableBalance availableBalance = new AvailableBalance();
                availableBalance.setMinorUnits(starlingBankTransactionDetails.getEffectiveBalance().getMinorUnits());
                availableBalance.setCurrency(starlingBankTransactionDetails.getEffectiveBalance().getCurrency());

                return StarlingBankBalance.builder().availableBalance(availableBalance)
                        .clearedBalance(starlingBankTransactionDetails.getClearedBalance())
                        .pendingTransactions(starlingBankTransactionDetails.getPendingTransactions())
                        .build();
            }
        }
            return null;
    }
}
