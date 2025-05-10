package com.fundflow.fundFlowApp.service;

import com.fundflow.fundFlowApp.dto.loan.LoanReqDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

public interface LoanService {

    ResponseEntity<?> saveLoanRequest(String userName, LoanReqDto reqDto);
}
