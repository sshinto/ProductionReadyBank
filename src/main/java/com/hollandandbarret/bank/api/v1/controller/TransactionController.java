package com.hollandandbarret.bank.api.v1.controller;

import com.hollandandbarret.bank.api.v1.controller.request.AccountRequest;
import com.hollandandbarret.bank.api.v1.controller.request.TransactionCreateRequest;
import com.hollandandbarret.bank.api.v1.controller.request.TransactionFilterRequest;
import com.hollandandbarret.bank.dto.BalanceDto;
import com.hollandandbarret.bank.dto.TransactionDetails;
import com.hollandandbarret.bank.exception.BankException;
import com.hollandandbarret.bank.service.transaction.TransactionService;
import com.hollandandbarret.bank.utils.TransactionType;
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

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/v1/transaction")
public class TransactionController {
    Logger logger = LoggerFactory.getLogger(TransactionController.class);
    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }
    @Operation(method = "POST",
            summary="Registered Holland and Barret Bank can utilize this service to get add the transaction account details by giving their customer account number"
    )
    @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(
            examples = @ExampleObject(value="{\"accountNumber\":\"1\",\n" +
                    "   \"description\": \"Salary\",\n" +
                    "   \"transactionType\": \"CREDIT\",\n" +
                    "   \"transactionStatus\": \"SUCCESSFUL\",\n" +
                    "   \"amount\": \"100.00\" \n" +
                    "\n" +
                    "}"),

            schema = @Schema(implementation = AccountRequest.class),
            mediaType = MediaType.APPLICATION_JSON_VALUE)
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Transaction created retrieved successfully",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AccountController.class)) }),
    })

    @PostMapping("/create")
    public ResponseEntity<TransactionDetails> createTransactions(@RequestBody TransactionCreateRequest request) throws BankException {
        TransactionDetails transactionDetails = new TransactionDetails();
        transactionDetails.setTransactionType(TransactionType.valueOf(request.getTransactionType()));
        transactionDetails.setAmount(request.getAmount());
        transactionDetails.setAccountNumber(request.getAccountNumber());
        transactionDetails.setDescription(request.getDescription());
        Date date = new Date();
        transactionDetails.setTransactionDate(date);
        return new ResponseEntity<>(transactionService.create(transactionDetails), HttpStatus.CREATED);
    }


    @Operation(method = "POST",
            summary="Registered Holland and Barret Bank customers can utilize this service to get their transaction details by giving their account number, from date and to date in the format yyyy-MM-dd HH:mm:ss "
    )
    @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(
            examples = @ExampleObject(value="{\"customerNumber\":\"1\",\n" +
                    "  \"transactionFromDate\":\"2021-05-01 00:00:00\",\n" +
                    "   \"transactionToDate\":\"2021-06-01 00:00:00\"\n" +
                    "}"),

            schema = @Schema(implementation = AccountRequest.class),
            mediaType = MediaType.APPLICATION_JSON_VALUE)
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Customer transaction details retrieved successfully",
                    content = { @Content(mediaType = "application/json",
                    schema = @Schema(implementation = AccountController.class)) }),
    })

    @PostMapping(value = "/statement")
    public ResponseEntity<List<BalanceDto>> getStatement(@RequestBody TransactionFilterRequest transactionFilterRequest
                                                          ) throws BankException {
        logger.info("Get the transaction details");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime fromdate = LocalDateTime.parse(transactionFilterRequest.getTransactionFromDate(), formatter);
        LocalDateTime toDate = LocalDateTime.parse(transactionFilterRequest.getTransactionToDate(), formatter);

        return new ResponseEntity<>(transactionService.getTransactions(transactionFilterRequest.getCustomerNumber(),
                fromdate, toDate), HttpStatus.OK);
    }

}
