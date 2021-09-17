package com.hollandandbarret.bank.api.v1.controller;

import com.hollandandbarret.bank.dto.CustomerDto;
import com.hollandandbarret.bank.exception.BankException;
import com.hollandandbarret.bank.service.customer.CustomerService;
import org.junit.jupiter.api.Assertions;
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
class CustomerControllerTest {

    @InjectMocks
    CustomerController customerController;

    @Mock
    CustomerService customerService;

    CustomerDto customerDto;

    @Test
    public void testGetCustomerById() throws BankException {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        customerDto =  CustomerDto.builder().customerId(1).address1("2").address2("Marrow Quay").city("Basingstoke").contactNumber("4565676576")
                .firstName("First").lastName("Test").build();

        when(customerService.getCustomer(any(Long.class))).thenReturn(customerDto);
        ResponseEntity<CustomerDto> responseEntity = customerController.getAccountByNumber(1l);
        CustomerDto results = responseEntity.getBody();
        Assertions.assertEquals(results.getFirstName(), customerDto.getFirstName());
        Assertions.assertEquals(results.getLastName(), customerDto.getLastName());
        Assertions.assertEquals(results.getCustomerId(), customerDto.getCustomerId());
        Assertions.assertEquals(results.getAddress1(), customerDto.getAddress1());
        Assertions.assertEquals(results.getAddress2(), customerDto.getAddress2());
        Assertions.assertEquals(results.getCity(), customerDto.getCity());
        Assertions.assertEquals(results.getContactNumber(), customerDto.getContactNumber());
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
    }

    @Test
    void whenInvalidCustomerNumber_ThrowsBankException() throws BankException {
        when(customerService.getCustomer(any(Long.class))).thenThrow(BankException.class);
        Assertions.assertThrows(BankException.class, () -> {
            customerController.getAccountByNumber(2);
        });
    }
}