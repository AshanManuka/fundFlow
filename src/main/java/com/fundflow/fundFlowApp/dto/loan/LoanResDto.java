package com.fundflow.fundFlowApp.dto.loan;

import lombok.*;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class LoanResDto {
    private long id;
    private double loanAmount;
    private int installment;
    private Date requestedDate;
    private String status;
    private String reason;
}
