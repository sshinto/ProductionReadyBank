package com.hollandandbarret.bank.service.account;

import com.hollandandbarret.bank.dto.AccountDto;
import com.hollandandbarret.bank.exception.BankException;

import java.util.List;

/**
 * Created by Savitha Shinto.
 */
public interface AccountService {
    public AccountDto getAccount(Long accountNumber) throws BankException;
    public List<AccountDto> getAllAccountByCustomerId(long customerNumber) throws BankException;
}
