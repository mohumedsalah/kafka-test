package com.cyshield.Partition.dto;

import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class Customer {
    private String index;
    private String customerId;
    private String firstName;
    private String lastName;
    private String company;
    private String city;
    private String country;
    private String phone1;
    private String phone2;
    private String email;
    private String subscriptionDate;
    private String website;
}