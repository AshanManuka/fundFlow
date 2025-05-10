package com.fundflow.fundFlowApp.dto.loan;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class LoanReqDto {
    private double amount;
    private int installment;
    private String reason;
}
