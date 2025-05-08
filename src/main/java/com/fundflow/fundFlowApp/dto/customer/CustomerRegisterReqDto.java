package com.fundflow.fundFlowApp.dto.customer;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class CustomerRegisterReqDto {
    private String name;
    private String nic;
    private String email;
    private double monthlyIncome;
    private double creditScore;
}
