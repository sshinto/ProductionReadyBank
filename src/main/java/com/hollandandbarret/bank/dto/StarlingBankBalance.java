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
public class StarlingBankBalance {
    AvailableBalance availableBalance;
    ClearedBalance clearedBalance;
    PendingTransactions pendingTransactions;

}
