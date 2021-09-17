package com.hollandandbarret.bank.dto;

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
public class CustomerDto {
    protected long customerId;
    private String firstName;
    private String lastName;
    private Date dob;
    private String address1;
    private String address2;
    private String city;
    private String contactNumber;
}
