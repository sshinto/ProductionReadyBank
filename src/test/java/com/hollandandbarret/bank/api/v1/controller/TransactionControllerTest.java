package com.hollandandbarret.bank.api.v1.controller;


import com.hollandandbarret.bank.api.v1.controller.request.AccountRequest;
import com.hollandandbarret.bank.api.v1.controller.request.TransactionCreateRequest;
import com.hollandandbarret.bank.api.v1.controller.request.TransactionFilterRequest;
import com.hollandandbarret.bank.dto.BalanceDto;
import com.hollandandbarret.bank.dto.TransactionDetails;
import com.hollandandbarret.bank.exception.BankException;
import com.hollandandbarret.bank.service.transaction.TransactionService;
import com.hollandandbarret.bank.utils.AccountType;
import com.hollandandbarret.bank.utils.TransactionType;
import org.apache.tomcat.jni.Local;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TransactionControllerTest {

    @InjectMocks
    TransactionController transactionController;

    @Mock
    TransactionService transactionService;

    TransactionDetails transactionDetails;
    TransactionFilterRequest transactionFilterRequest;
    TransactionCreateRequest transactionCreateRequest;
    List<BalanceDto> balanceDtos = new ArrayList<>();
    List<TransactionDetails> transactionDetailsList = new ArrayList<>();

    @BeforeEach
    public void setup() throws BankException {
        transactionCreateRequest = TransactionCreateRequest.builder().accountNumber(1l).amount(10.0).description("test").transactionDate(new Date()).transactionType(TransactionType.DEBIT.toString()).build();
        transactionDetails = TransactionDetails.builder().accountNumber(1L).transactionDate(new Date()).transactionId(1l).transactionType(TransactionType.CREDIT).amount(100.00).description("Test").build();
        transactionDetailsList.add(transactionDetails);
        transactionFilterRequest = TransactionFilterRequest.builder().customerNumber(1l).transactionFromDate("2017-11-15 15:30:14").transactionToDate("2017-11-15 15:30:14").build();
        BalanceDto balanceDto = BalanceDto.builder().totalBalance(100.00).accountNumber(1l).accountType(AccountType.SAVINGS)
                .amountCredited(10.0).amountDebited(10.0).transactionDetails(transactionDetailsList).transactionFromDate(LocalDateTime.now()).transactionToDate(LocalDateTime.now()).build();

        balanceDtos.add(balanceDto);
    }

    @Test
    void createPost() throws BankException {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        AccountRequest accountRequest =  AccountRequest.builder().accountNumber(1L).build();
        when(transactionService.create(any(TransactionDetails.class))).thenReturn(transactionDetails);

        ResponseEntity<TransactionDetails> responseEntity = transactionController.createTransactions(transactionCreateRequest);
        TransactionDetails results = responseEntity.getBody();
        Assertions.assertEquals(results,transactionDetails);
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(201);
    }

    @Test
    void getStatement() throws BankException {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        AccountRequest accountRequest =  AccountRequest.builder().accountNumber(1L).build();
        when(transactionService.getTransactions(any(Long.class), any(LocalDateTime.class), any(LocalDateTime.class))).thenReturn(balanceDtos);
        ResponseEntity<List<BalanceDto>> responseEntity = transactionController.getStatement(transactionFilterRequest);
        List<BalanceDto> results = responseEntity.getBody();
        Assertions.assertEquals(results.get(0).getAccountNumber(), balanceDtos.get(0).getAccountNumber());
        Assertions.assertEquals(results.get(0).getTransactionDetails(), balanceDtos.get(0).getTransactionDetails());
        Assertions.assertEquals(results.get(0), balanceDtos.get(0));
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
    }


    @Test
    void whenInvalidAccountNumber_ThrowsBankException() throws BankException {
        when(transactionService.getTransactions(any(Long.class), any(LocalDateTime.class), any(LocalDateTime.class))).thenThrow(BankException.class);
        Assertions.assertThrows(BankException.class, () -> {
            transactionController.getStatement(transactionFilterRequest);
        });
    }

    @Test
    void whenInvalidInput_ThrowsBankException() throws BankException {
        when(transactionService.create(any(TransactionDetails.class))).thenThrow(BankException.class);
        Assertions.assertThrows(BankException.class, () -> {
            transactionController.createTransactions(transactionCreateRequest);
        });
    }
}