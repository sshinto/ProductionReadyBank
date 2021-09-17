package com.hollandandbarret.bank.service.account;

import com.hollandandbarret.bank.domain.model.Account;
import com.hollandandbarret.bank.domain.repository.AccountRepository;
import com.hollandandbarret.bank.dto.AccountDto;
import com.hollandandbarret.bank.errors.BankErrors;
import com.hollandandbarret.bank.exception.BankException;
import com.hollandandbarret.bank.utils.AccountType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created by Savitha Shinto.
 */
@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    AccountRepository accountRepository;

    @Override
    public AccountDto getAccount(Long accountNumber) throws BankException {
        Optional<Account> accountInDb = accountRepository.findById(accountNumber);
        if (accountInDb.isPresent()) {
            return getAccountDto(accountInDb.get());
        } else {
            throw new BankException(BankErrors.INVALID_ACCOUNT);
        }

    }

    @Override
    public  List<AccountDto> getAllAccountByCustomerId(long customerNumber) throws BankException {
        List<AccountDto> accountDtos = new ArrayList<>();
        List<Account> accountsInDb = accountRepository.findAccountByCustomerNumber(customerNumber);
        if (accountsInDb != null && accountsInDb.size() > 0) {
            int index =0;
            for(Account account : accountsInDb){
                accountDtos.add(index, getAccountDto(account) );
                index ++;
            }
            return accountDtos;
        } else {
            throw new BankException(BankErrors.INVALID_CUSTOMER);
        }
    }

    //
    private Account mapDtotoEntity(AccountDto accountDto) {
        Account account = new Account();
        account.setAccountNumber(accountDto.getAccountNumber());
        account.setCustomerId(accountDto.getCustomerId());
        account.setAccountType(accountDto.getAccountType().getValue());
        return account;
    }

    private List<AccountDto> getListOfTransactionsDto(List<Account> accounts)  {
        List<AccountDto> accountDtos = new ArrayList<>();
        if(accountDtos !=null) {
            for (Account rec : accounts) {
                accountDtos.add(getAccountDto(rec));
            }
        }
        return accountDtos;
    }

    private AccountDto getAccountDto(Account account)  {
        AccountDto accountDto = new AccountDto();
        accountDto.setAccountNumber(account.getAccountNumber());
        accountDto.setCustomerId(account.getCustomerId());
        accountDto.setAccountType(AccountType.valueOf(account.getAccountType()));
        return accountDto;

    }

}
