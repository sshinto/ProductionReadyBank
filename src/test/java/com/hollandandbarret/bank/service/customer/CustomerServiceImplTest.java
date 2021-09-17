package com.hollandandbarret.bank.service.customer;

import com.hollandandbarret.bank.domain.model.Customer;
import com.hollandandbarret.bank.domain.repository.CustomerRepository;
import com.hollandandbarret.bank.dto.CustomerDto;
import com.hollandandbarret.bank.exception.BankException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomerServiceImplTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    CustomerServiceImpl customerService;

    Customer customer;
    CustomerDto customerDto;

    @BeforeEach
    public void setup() {
        customer = Customer.builder().customerId(1).address1("1").address2("Lemon Quay").city("Truro").contactNumber("455465")
                .firstName("Test").lastName("Test").build();
        customerDto =  CustomerDto.builder().customerId(1).address1("1").address2("Lemon Quay").city("Truro").contactNumber("455465")
                .firstName("Test").lastName("Test").build();
    }

    @Test
    void getAccount() throws BankException {
        when(customerRepository.findById(1l)).thenReturn(Optional.ofNullable(customer));
        CustomerDto results = customerService.getCustomer(1);
        Assertions.assertEquals(results.getFirstName(), customerDto.getFirstName());
        Assertions.assertEquals(results.getLastName(), customerDto.getLastName());
        Assertions.assertEquals(results.getCustomerId(), customerDto.getCustomerId());
        Assertions.assertEquals(results.getAddress1(), customerDto.getAddress1());
        Assertions.assertEquals(results.getAddress2(), customerDto.getAddress2());
        Assertions.assertEquals(results.getCity(), customerDto.getCity());
        Assertions.assertEquals(results.getContactNumber(), customerDto.getContactNumber());
    }

    @Test
    void whenInvalidCustomerIdIsSent_ThrowsBankException() {
        Exception exception = assertThrows(BankException.class, () -> {
            customerService.getCustomer(3 );
        });
        String expectedMessage = "Invalid Customer";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));

    }


}