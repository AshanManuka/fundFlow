package com.fundflow.fundFlowApp.service;

import com.fundflow.fundFlowApp.dto.customer.CustomerRegisterReqDto;
import org.springframework.http.ResponseEntity;

public interface CustomerService {
    ResponseEntity<?> registerCustomer(CustomerRegisterReqDto reqBody);

    ResponseEntity<?> getAllCustomer();
}
