package com.hollandandbarret.bank.api.v1.controller.request;

import lombok.Builder;
import lombok.Data;
@Data
@Builder
public class TransactionFilterRequest {
    private Long customerNumber;
    private String transactionFromDate;
    private String transactionToDate;
}
