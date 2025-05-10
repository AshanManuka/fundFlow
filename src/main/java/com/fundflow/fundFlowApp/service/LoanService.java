package com.fundflow.fundFlowApp.service;

import com.fundflow.fundFlowApp.dto.loan.LoanReqDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

public interface LoanService {

    ResponseEntity<?> saveLoanRequest(String userName, LoanReqDto reqDto);

    ResponseEntity<?> getAllLoanByCustomer(String userName);

    ResponseEntity<?> getAllLoans();

    ResponseEntity<?> filterLoans(String loanStatus, double minScore, double maxScore);

    ResponseEntity<?> getLoansbyCustomer(long loanId);

    ResponseEntity<?> UpdateLoanStatus(long loanId, String status);
}
