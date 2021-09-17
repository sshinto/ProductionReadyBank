package com.hollandandbarret.bank.service.customer;

import com.hollandandbarret.bank.dto.CustomerDto;
import com.hollandandbarret.bank.exception.BankException;

/**
 * Created by Savitha Shinto.
 */
public interface CustomerService {
    CustomerDto getCustomer(long customerNumber) throws BankException;
}
