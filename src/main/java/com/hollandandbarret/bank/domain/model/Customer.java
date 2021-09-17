package com.hollandandbarret.bank.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

/**
 * Created by Savitha Shinto.
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Customer extends BaseDomainObject {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected long customerId;
    private String firstName;
    private String lastName;
    private Date dob;
    private String address1;
    private String address2;
    private String city;
    private String contactNumber;
}
