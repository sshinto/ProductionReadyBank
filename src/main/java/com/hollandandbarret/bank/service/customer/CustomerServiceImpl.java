package com.hollandandbarret.bank.service.customer;

import com.hollandandbarret.bank.domain.model.Customer;
import com.hollandandbarret.bank.domain.repository.CustomerRepository;
import com.hollandandbarret.bank.dto.CustomerDto;
import com.hollandandbarret.bank.errors.BankErrors;
import com.hollandandbarret.bank.exception.BankException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created by Savitha Shinto.
 */
@Service
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public CustomerDto getCustomer(long customerNumber) throws BankException {
        Optional<Customer> customerInDb = customerRepository.findById(customerNumber);
        if (customerInDb.isPresent()) {
            return getCustomerDto(customerInDb.get());
        } else {
            throw new BankException(BankErrors.INVALID_CUSTOMER);
        }

    }

    //
    private Customer mapDtotoEntity(CustomerDto customerDto) throws BankException {
        Customer customer = new Customer();
        customer.setAddress1(customerDto.getAddress1());
        customer.setAddress2(customerDto.getAddress2());
        customer.setCity(customerDto.getCity());
        customer.setContactNumber(customerDto.getContactNumber());
        customer.setCustomerId(customerDto.getCustomerId());
        customer.setDob(customerDto.getDob());
        customer.setFirstName(customerDto.getFirstName());
        customer.setLastName(customerDto.getLastName());
        return customer;
    }

    private List<CustomerDto> getListOfTransactionsDto(List<Customer> customers) throws BankException {
        List<CustomerDto> customerDtos = new ArrayList<>();
        if (customerDtos != null) {
            for (Customer rec : customers) {
                customerDtos.add(getCustomerDto(rec));
            }
        }
        return customerDtos;
    }

    private CustomerDto getCustomerDto(Customer customer) throws BankException {
        CustomerDto customerDto = new CustomerDto();
        customerDto.setAddress1(customer.getAddress1());
        customerDto.setAddress2(customer.getAddress2());
        customerDto.setCity(customer.getCity());
        customerDto.setContactNumber(customer.getContactNumber());
        customerDto.setCustomerId(customer.getCustomerId());
        customerDto.setDob(customer.getDob());
        customerDto.setFirstName(customer.getFirstName());
        customerDto.setLastName(customer.getLastName());
        return customerDto;

    }
}
