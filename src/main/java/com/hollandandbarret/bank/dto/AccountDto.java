package com.hollandandbarret.bank.dto;

import com.hollandandbarret.bank.utils.AccountType;
import lombok.*;

/**
 * Created by Savitha Shinto.
 */

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AccountDto {
    private Long accountNumber;
    private AccountType accountType;
    private int customerId;
}
