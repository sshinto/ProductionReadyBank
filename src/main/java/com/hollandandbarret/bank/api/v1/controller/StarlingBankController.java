package com.hollandandbarret.bank.api.v1.controller;

import com.hollandandbarret.bank.api.v1.controller.request.AccountRequest;
import com.hollandandbarret.bank.api.v1.controller.request.TransactionCreateRequest;
import com.hollandandbarret.bank.api.v1.controller.request.TransactionFilterRequest;
import com.hollandandbarret.bank.dto.BalanceDto;
import com.hollandandbarret.bank.dto.TransactionDetails;
import com.hollandandbarret.bank.exception.BankException;
import com.hollandandbarret.bank.service.transaction.TransactionService;
import com.hollandandbarret.bank.utils.TransactionType;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ExampleProperty;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.enums.ParameterStyle;
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
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/v1/starlingbank")
public class StarlingBankController {
    Logger logger = LoggerFactory.getLogger(TransactionController.class);
    private final TransactionService transactionService;

    public StarlingBankController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @Operation(method = "GET",
            parameters = {
                    @Parameter(in = ParameterIn.PATH, name = "accountUid", required = true,
                            examples = {
                                    @ExampleObject(name = "accountUid", value = "67141534-3893-483c-8aff-fe4f3b6291a3"
                                            ),
                                    })
            },
            summary="Get Balance from Starling Bank, \n" +
                    "URL:   https://api-sandbox.starlingbank.com/api/v2/accounts/{accountUid}/balance\n" +
                    "METHOD:  GET\n" +
                    "accountUID should be 67141534-3893-483c-8aff-fe4f3b6291a3, \n"


    )

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Get Balance from Starling Bank",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AccountController.class)) }),
    })
    @GetMapping("/balancestatement/{accountUid}")
    public ResponseEntity<String> getStarlingBankBalance( @PathVariable("accountUid") String accountUid) throws BankException {
        logger.debug("Balance statement");
        return new ResponseEntity<>(transactionService.callStarlingBankApi(accountUid), HttpStatus.OK);
    }

}

