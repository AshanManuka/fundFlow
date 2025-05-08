package com.fundflow.fundFlowApp.controller.admin;

import com.fundflow.fundFlowApp.dto.customer.CustomerRegisterReqDto;
import com.fundflow.fundFlowApp.service.CustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Log4j2
@RequiredArgsConstructor
@RequestMapping(value = "/admin")
public class CustomerAccountController {

    private final CustomerService customerService;

    @PostMapping("/register-customer")
    public ResponseEntity<?> registerCustomer(@RequestBody CustomerRegisterReqDto reqBody){
        log.info("Request for customer Registration, customer-nic: {}",reqBody.getNic());
        customerService.registerCustomer(reqBody);
        return null;
    }


}
