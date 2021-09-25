package com.hollandandbarret.bank.api.v1.controller;

import com.hollandandbarret.bank.dto.StarlingBankBalance;
import com.hollandandbarret.bank.service.transaction.TransactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/v1/starlingbank")
public class StarlingBankController {
    Logger logger = LoggerFactory.getLogger(StarlingBankController.class);
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
                                    }),
                    @Parameter(in = ParameterIn.PATH, name = "token", required = true,
                            examples = {
                                    @ExampleObject(name = "token", value = "Bearer eyJhbGciOiJQUzI1NiIsInppcCI6IkdaSVAifQ.H4sIAAAAAAAA_31Sy5LbIBD8lZTOO1vWW9Ytt_xAPmCAkU0ZgQrQbrZS-fcggSzLceVGd8-rh_mdSeeyPsNJgqDRvDuPVkl9Yahv79yM2VvmZhYiGFVFIwRC2Z0IqqIT0LGihaEmVuXYVUy0IZh-TVmfN2XRtHlzPr1lEn0k6q4uFgI5N7P2P4wSZH9KEWo3gk5F2dWAxEqohrqBrhEMeN2cm7qsO95iqO3NjXTMKE85ttXQAhZVFzJ4yBD5CUpq8CzOmFd5HjKCre-ck3Mxq82ruivPLRCVOVQtcmAta6Bsy3xgpzrPafHguJloWUqcFLgyjkRvCcW3jbuu44PGkV4K_mt6EqQg7eUgyR55JZ0_MAkIYcPgPQnp7yAq3iO_jnSP3PGnlZ6-4eyvxkoXvhGkFvJDihlVDGaoUPM0GkcrgBvtrVGx0cIkzehB2hG9NBrMAMOshbtL7t59A7E1n50342aRRpSpsKIwiL70OE3q647WqBG1QE-9IEWhxAaTZm_kFyOTpYEshdnd_6Q4RtQmhZzCBjxd7OrjMfFfMaWS5Vfc3I3kMUyDPQ9wVRNeTU34RbRJESQTEexBIEe8JE9R25_gLWqHfJ8w0MBmdeu3n6Sd2rtFvDeMeCuw3EO4rVH6vaYyPNzBQ4WVALMcxDObsqwZpNrGj34O1BpliZOc_AG4oxSX6_AjfJiDi9nnOHDJzYFb6zwycWHhy1-V2MUXtXYxFuVXErMiAWlhiSbvg8F5StDjeq1grHjoeWS3Zkf2RT6YT33nPa3fxN3HMzWJIVEzc9yGFS7XsXV55NaoxxNaf-n5prI_fwG9DEUY6wUAAA.pKnwMF8IJwB3qv64QzlU1RlQrEZWHzrWq0mPHPPAMsNF42Qt3xD98zFGH73dBvfmbwx8DwsHqTsEi1s03llWuTODeHpORAh_lr9c87UhcHT6bkuhq76FvBULQ3qmI0FXWw5TY72zWQ7Cn7nhBn_ru5BXgXkHkRfF9pKZ-x40zR-OpQx3Ofercc23WMewl3gVLHvqZL0QfPsA-3SBIEGXbHugp6oduswBYJJsylGbLqgeqXorw5KBPqdN5sMBqU_58K8ynoNJvNekpVJtE2rzWRoxJOC9ezJTtqd90GQRufXqkCee-uHbei4cpehSCTJTNcFci9Br47m0XbBMLn-QRB896CRzc8VeUptp5Chf9DYNq4j_Yf55j4c09eYjNljhTRhwr_7iH3ZpWREkvvrMCPqj83T6FdaCzlmdjXYxQWxV7kr3gu6c1M0rRIfnRudzn1OLsGp52C8f4GvNbLcnzBrhzWxIqTHVZH66iOrSo1F9MzW7b_zTfEnLm51nRNtVfq3norcMrdf5XMc-kHmU8HVsazm5Fnhb00kcKfZI7te2C_mJK5G4zRf_qdDW2F7AlEF9jQAzT-wdr9kFXKV3VwDCeVd_A7dVgjz2QX3ay1z4wKtaA_frY1SPXvCHdTZ0r3mBy3Ga1aYgNJvFlW6r3YeitI4eAUeO80LQBldZMw0"
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
    @GetMapping("/balancestatement/{accountUid}/{token}")
    public ResponseEntity<StarlingBankBalance> getStarlingBankBalance(@PathVariable("accountUid") String accountUid, @PathVariable("token") String token)  {
        logger.debug("Balance statement");
        return new ResponseEntity<>(transactionService.callStarlingBankApi(accountUid,token), HttpStatus.OK);
    }

}

