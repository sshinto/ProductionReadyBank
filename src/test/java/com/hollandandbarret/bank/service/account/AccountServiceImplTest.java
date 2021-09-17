package com.hollandandbarret.bank.service.account;

import com.hollandandbarret.bank.domain.model.Account;
import com.hollandandbarret.bank.domain.repository.AccountRepository;
import com.hollandandbarret.bank.dto.AccountDto;
import com.hollandandbarret.bank.exception.BankException;
import com.hollandandbarret.bank.utils.AccountType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AccountServiceImplTest {

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    AccountServiceImpl accountService;

    Account account;
    AccountDto accountDto;
    List<Account> accounts = new ArrayList<>();

    @BeforeEach
    public void setup() {
        account = Account.builder().accountNumber(1L).accountType(AccountType.SAVINGS.getValue()).customerId(1).build();
        accountDto =  AccountDto.builder().accountNumber(1L).accountType(AccountType.SAVINGS).customerId(1).build();
        accounts.add(0, account);
    }

    @Test
    void getAccount() throws BankException {
        when(accountRepository.findById(1l)).thenReturn(Optional.ofNullable(account));
        AccountDto results = accountService.getAccount(1l);
        Assertions.assertEquals(results.getAccountNumber(), accountDto.getAccountNumber());
        Assertions.assertEquals(results.getCustomerId(), accountDto.getCustomerId());
        Assertions.assertEquals(results.getAccountType(), accountDto.getAccountType());
    }

    @Test
    void getAllAccountByCustomerId() throws BankException {
        when(accountRepository.findAccountByCustomerNumber(1l)).thenReturn(accounts);
        List<AccountDto> results = accountService.getAllAccountByCustomerId(1);
        Assertions.assertEquals(results.get(0).getAccountNumber(), accountDto.getAccountNumber());
        Assertions.assertEquals(results.get(0).getCustomerId(), accountDto.getCustomerId());
        Assertions.assertEquals(results.get(0).getAccountType(), accountDto.getAccountType());
    }

    @Test
    void whenInvalidAccountNumberIsSent_ThrowsBankException() {
        Exception exception = assertThrows(BankException.class, () -> {
            accountService.getAccount(3l );
        });
        String expectedMessage = "Invalid Account";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));

    }

    @Test
    void whenInvalidCustomerIDIsSent_ThrowsBankException() {
        Exception exception = assertThrows(BankException.class, () -> {
            accountService.getAllAccountByCustomerId(3);
        });
        String expectedMessage = "Invalid Customer";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));

    }

}