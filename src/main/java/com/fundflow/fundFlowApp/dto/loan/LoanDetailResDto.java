package com.fundflow.fundFlowApp.dto.loan;

import lombok.*;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class LoanDetailResDto {
    private long id;
    private String name;
    private String email;
    private String nic;
    private double income;
    private double creditScore;
    private String status;
    private double loanAmount;
    private int installment;
    private String reason;
    private Date requestedDate;
}
