package com.hollandandbarret.bank.dto;

import lombok.*;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class BaseAmount {
    private String currency;
    private double minorUnits;
}
