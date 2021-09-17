package com.hollandandbarret.bank.api.v1.controller;

import com.hollandandbarret.bank.api.v1.controller.request.AccountRequest;
import com.hollandandbarret.bank.dto.AccountDto;
import com.hollandandbarret.bank.exception.BankException;
import com.hollandandbarret.bank.service.account.AccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/account")
public class AccountController {
    Logger logger = LoggerFactory.getLogger(AccountController.class);
    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }
    @Operation(method = "POST",
            summary="Registered Holland and Barret Bank customers can utilize this service to get their account details by giving their account number"
    )
    @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(
            examples = @ExampleObject(value="{\n" +
                    "    \"accountNumber\": \"1\"\n" +
                    "}"),

            schema = @Schema(implementation = AccountRequest.class),
            mediaType = MediaType.APPLICATION_JSON_VALUE)
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Account details retrieved successfully",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AccountController.class)) }),
    })

    @PostMapping
    public ResponseEntity<AccountDto> getAccountByNumber(@RequestBody AccountRequest request ) throws BankException {
        logger.info("Get the account details");
        return new ResponseEntity<>(accountService.getAccount(request.getAccountNumber()), HttpStatus.OK);
    }

}
