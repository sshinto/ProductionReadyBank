package com.hollandandbarret.bank.service.customer;

import com.hollandandbarret.bank.domain.model.Customer;
import com.hollandandbarret.bank.domain.repository.CustomerRepository;
import com.hollandandbarret.bank.dto.CustomerDto;
import com.hollandandbarret.bank.errors.BankErrors;
import com.hollandandbarret.bank.exception.BankException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

/**
 * Created by Savitha Shinto.
 */
@Service
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CustomerDto getCustomer(long customerNumber) throws BankException {
        Optional<Customer> customerInDb = customerRepository.findById(customerNumber);
        if (customerInDb.isPresent()) {
            return getCustomerDto(customerInDb.get());
        } else {
            throw new BankException(BankErrors.INVALID_CUSTOMER);
        }

    }


    private CustomerDto getCustomerDto(Customer customer)   {
        return modelMapper.map(customer, CustomerDto.class);
    }
}
