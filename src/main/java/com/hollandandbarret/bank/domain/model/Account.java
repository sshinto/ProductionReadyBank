package com.hollandandbarret.bank.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Created by Savitha Shinto.
 */

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Account extends BaseDomainObject{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long accountNumber;
    private String accountType;
    private int customerId;

}
