package com.fundflow.fundFlowApp.controller.admin;

import com.fundflow.fundFlowApp.service.LoanService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
}
