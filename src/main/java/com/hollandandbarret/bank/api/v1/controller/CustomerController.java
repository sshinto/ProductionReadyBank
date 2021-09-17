package com.hollandandbarret.bank.api.v1.controller;

import com.hollandandbarret.bank.dto.CustomerDto;
import com.hollandandbarret.bank.exception.BankException;
import com.hollandandbarret.bank.service.customer.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/customer")
public class CustomerController {
    Logger logger = LoggerFactory.getLogger(CustomerController.class);
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @Operation(method = "GET",
            summary="Registered Holland and Barret Bank customers can utilize this service to get their personal details by giving their customer number"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Customer details retrieved successfully",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AccountController.class)) }),
    })

    @GetMapping(value = "/{customerNumber}")
    public ResponseEntity<CustomerDto> getAccountByNumber(@PathVariable("customerNumber") long customerNumber) throws BankException {
        logger.info("Get the customer details");
        return new ResponseEntity<>(customerService.getCustomer(customerNumber), HttpStatus.OK);
    }

}
