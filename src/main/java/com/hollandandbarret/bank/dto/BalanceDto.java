package com.hollandandbarret.bank.dto;

import com.hollandandbarret.bank.utils.AccountType;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class BalanceDto {
    private Long accountNumber;
    private AccountType accountType;
    private LocalDateTime transactionFromDate;
    private LocalDateTime transactionToDate;
    private double amountCredited;
    private double amountDebited;
    private double totalBalance;
    private List<TransactionDetails> transactionDetails;
}
