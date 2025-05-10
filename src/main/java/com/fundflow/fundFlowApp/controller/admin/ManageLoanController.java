package com.fundflow.fundFlowApp.controller.admin;

import com.fundflow.fundFlowApp.service.LoanService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Log4j2
@RequiredArgsConstructor
@RequestMapping(value = "/admin")
public class ManageLoanController {

    private final LoanService loanService;

    @GetMapping("/all-loan")
    public ResponseEntity<?> getAllLoans(){
        log.info("Request for all loan");
        return loanService.getAllLoans();
    }

    @GetMapping("/filter-loan")
    public ResponseEntity<?> filterLoans(@RequestParam String loanStatus, @RequestParam double minScore, @RequestParam double maxScore){
        log.info("Request for filter loans, status:{}, minScore:{}, maxScore:{}",loanStatus, minScore, maxScore);
        return loanService.filterLoans(loanStatus, minScore, maxScore);
    }

    @GetMapping("/single-customer-loan")
    public ResponseEntity<?> getLoansByCustomer(@RequestParam long customerId){
        log.info("Request for get al loans by customer:{}",customerId);
        return loanService.getLoansbyCustomer(customerId);
    }
}
