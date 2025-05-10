package com.fundflow.fundFlowApp.dto.customer;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class BasicCustomerResDto {
    private long id;
    private String name;
    private String nic;
    private String email;
    private double income;
    private double creditScore;
}
