package com.hollandandbarret.bank.api.v1.controller;

import com.hollandandbarret.bank.api.v1.controller.request.AccountRequest;
import com.hollandandbarret.bank.dto.AccountDto;
import com.hollandandbarret.bank.exception.BankException;
import com.hollandandbarret.bank.service.account.AccountService;
import com.hollandandbarret.bank.utils.AccountType;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AccountControllerTest {
    @InjectMocks
    AccountController accountController;

    @Mock
    AccountService accountService;

    AccountDto accountDto;
    AccountRequest accountRequest;
    @BeforeEach
    public void setup() {
        accountDto =  AccountDto.builder().accountNumber(1L).accountType(AccountType.SAVINGS).customerId(1).build();
        accountRequest =  AccountRequest.builder().accountNumber(1L).build();
    }

    @Test
    void testGetAccountByNumber() throws BankException {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        when(accountService.getAccount(any(Long.class))).thenReturn(accountDto);
        ResponseEntity<AccountDto> responseEntity = accountController.getAccountByNumber(accountRequest);
        AccountDto results = responseEntity.getBody();
        Assertions.assertEquals(results.getAccountNumber(), accountDto.getAccountNumber());
        Assertions.assertEquals(results.getCustomerId(), accountDto.getCustomerId());
        Assertions.assertEquals(results.getAccountType(), accountDto.getAccountType());
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
    }

    @Test
    void whenInvalidAccountNumber_ThrowsBankException() throws BankException {
        when(accountService.getAccount(any(Long.class))).thenThrow(BankException.class);
        Assertions.assertThrows(BankException.class, () -> {
            accountController.getAccountByNumber(accountRequest);
        });
    }

}