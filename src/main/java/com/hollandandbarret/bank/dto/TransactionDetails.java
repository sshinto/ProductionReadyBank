package com.hollandandbarret.bank.dto;

import com.hollandandbarret.bank.utils.TransactionType;
import lombok.*;

import java.util.Date;

/**
 * Created by Savitha Shinto.
 */

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TransactionDetails {
    private Long transactionId;
    private long accountNumber;
    private String description;
    private TransactionType transactionType;
    private double amount;
    private Date transactionDate;
}
