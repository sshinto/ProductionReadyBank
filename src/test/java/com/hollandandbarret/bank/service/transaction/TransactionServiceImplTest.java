package com.hollandandbarret.bank.service.transaction;

import com.hollandandbarret.bank.domain.model.Transaction;
import com.hollandandbarret.bank.domain.repository.TransactionRepository;
import com.hollandandbarret.bank.dto.AccountDto;
import com.hollandandbarret.bank.dto.BalanceDto;
import com.hollandandbarret.bank.dto.CustomerDto;
import com.hollandandbarret.bank.dto.TransactionDetails;
import com.hollandandbarret.bank.exception.BankException;
import com.hollandandbarret.bank.service.account.AccountService;
import com.hollandandbarret.bank.service.customer.CustomerService;
import com.hollandandbarret.bank.utils.AccountType;
import com.hollandandbarret.bank.utils.TransactionType;
import com.hollandandbarret.bank.validations.TransactionValidation;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TransactionServiceImplTest {

    @InjectMocks
    TransactionServiceImpl transactionService;

    @Mock
    private AccountService accountService;
    @Mock
    private CustomerService customerService;
    @Mock
    private TransactionValidation transactionValidation;
    @Mock
    private TransactionRepository transactionRepository;

    Transaction transaction;
    TransactionDetails transactionDetails;
    List<Transaction> transactions = new ArrayList<>();
    AccountDto accountDto;
    List<AccountDto> accounts = new ArrayList<>();
    CustomerDto customerDto;

    @BeforeEach
    public void setup() {
        Date date = new Date();
        transaction = Transaction.builder().transactionId(1l).transactionType("CREDIT").accountNumber(1l).description("Test").amount(100.0).transactionDate(date)
              .build();
        transactionDetails =TransactionDetails.builder().transactionId(1l).transactionType(TransactionType.CREDIT).accountNumber(1l).description("Test").amount(100.0).transactionDate(date)
                .build();
        customerDto =  CustomerDto.builder().customerId(1).address1("1").address2("Lemon Quay").city("Truro").contactNumber("455465")
                .firstName("Test").lastName("Test").build();
        accountDto =  AccountDto.builder().accountNumber(1L).accountType(AccountType.SAVINGS).customerId(1).build();
        accounts.add(0, accountDto);
    }

    @Test
    void getTransactionDetailsByCustomerId() throws BankException {
        when(customerService.getCustomer(1)).thenReturn(customerDto);
        when(accountService.getAllAccountByCustomerId(1)).thenReturn(accounts);
        when(transactionRepository.findTransactionByAccountNumberAndDate(any(Long.class), any(LocalDateTime.class), any(LocalDateTime.class))).thenReturn(transactions);
        Date date = new Date();
        List<BalanceDto>  results = transactionService.getTransactions(1, LocalDateTime.now(), LocalDateTime.now());
        Assertions.assertEquals(results.get(0).getAccountNumber(), transactionDetails.getAccountNumber());
        Assertions.assertEquals(results.get(0).getAccountType(), AccountType.SAVINGS);
        Assertions.assertEquals(results.get(0).getTotalBalance(), 0.0);
    }

    @Test
    void create() throws BankException {
        when(transactionRepository.save(any(Transaction.class))).thenReturn(transaction);
        TransactionDetails created = transactionService.create(transactionDetails);
        Assertions.assertEquals(created.getAccountNumber(), transaction.getAccountNumber());
        Assertions.assertEquals(created.getTransactionType(),TransactionType.valueOf(transaction.getTransactionType()));
        Assertions.assertEquals(created.getTransactionId(), transaction.getTransactionId());
        Assertions.assertEquals(created.getAmount(), transaction.getAmount());
        Assertions.assertEquals(created.getDescription(), transaction.getDescription());
    }

    @Test
    void whenInvalidInput_ThrowsBankException() {
        when(transactionRepository.save(any(Transaction.class))).thenThrow(NullPointerException.class);
        Assertions.assertThrows(BankException.class, () -> {
            transactionService.create(transactionDetails);
        });
    }
}