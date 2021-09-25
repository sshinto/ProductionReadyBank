package com.hollandandbarret.bank.dto;

import lombok.*;

/**
 * Created by Savitha Shinto.
 */

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class StarlingBankTransactionDetails {
    ClearedBalance clearedBalance;
    EffectiveBalance effectiveBalance;
    PendingTransactions pendingTransactions;
    AcceptedOverdraft acceptedOverdraft;
    Amount amount;
}
