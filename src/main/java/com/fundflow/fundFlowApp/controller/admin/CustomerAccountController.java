package com.fundflow.fundFlowApp.controller.admin;

import com.fundflow.fundFlowApp.dto.customer.CustomerRegisterReqDto;
import com.fundflow.fundFlowApp.service.CustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Log4j2
@RequiredArgsConstructor
@RequestMapping(value = "/admin")
public class CustomerAccountController {

    private final CustomerService customerService;

    @PostMapping("/register-customer")
    public ResponseEntity<?> registerCustomer(@RequestBody CustomerRegisterReqDto reqBody){
        log.info("Request for customer Registration, customer-nic: {}",reqBody.getNic());
        return customerService.registerCustomer(reqBody);
    }

    @GetMapping("/all-customer")
    public ResponseEntity<?> getAllCustomer(){
        log.info("Request for get all customers");
        return customerService.getAllCustomer();
    }

    @GetMapping("/search-customer")
    public ResponseEntity<?> searchCustomer(@RequestParam String cusKeyword){
        log.info("Request for search customer by keyword: {}",cusKeyword);
        return customerService.searchCustomer(cusKeyword);
    }

    @PutMapping("/update-customer")
    public ResponseEntity<?> updateCustomer(@RequestParam Long cusId, @RequestBody CustomerRegisterReqDto reqDto){
        log.info("Request for update customer, cusId: {}",cusId);
        return customerService.updateCustomer(cusId,reqDto);
    }

    @DeleteMapping("/delete-customer")
    public ResponseEntity<?> deleteCustomer(@RequestParam long customerId){
        log.info("delete customer id:{}",customerId);
        return customerService.deleteCustomer(customerId);
    }


}
