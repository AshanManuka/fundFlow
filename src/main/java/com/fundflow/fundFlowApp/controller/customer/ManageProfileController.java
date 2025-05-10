package com.fundflow.fundFlowApp.controller.customer;

import com.fundflow.fundFlowApp.dto.common.CommonResponse;
import com.fundflow.fundFlowApp.dto.loan.LoanReqDto;
import com.fundflow.fundFlowApp.service.LoanService;
import com.fundflow.fundFlowApp.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Log4j2
@RequiredArgsConstructor
@RequestMapping(value = "/customer")
public class ManageProfileController {

    private final JwtUtil jwtUtil;
    private final LoanService loanService;

    @GetMapping("/welcome")
    public ResponseEntity<?> welcomeAPI(){
        return ResponseEntity.ok(new CommonResponse<>(true, "Welcome Customer"));
    }

    @PostMapping("/request-loan")
    public ResponseEntity<?> requestLoan(@RequestHeader("Authorization") String headerToken, @RequestBody LoanReqDto reqDto){
        String userName = getUserNameByToken(headerToken);
        log.info("Request for loan by customer : {}",userName);
        return loanService.saveLoanRequest(userName,reqDto);
    }


    @GetMapping("/loan-history")
    public ResponseEntity<?> getLoanHistory(@RequestHeader("Authorization") String headerToken){
        String userName = getUserNameByToken(headerToken);
        log.info("Request for get all loans by customer, customer :{}",userName);
        return loanService.getAllLoanByCustomer(userName);
    }


    private String getUserNameByToken(String tokenHeader){
        if (tokenHeader != null && tokenHeader.startsWith("Bearer ")) {
            String token = tokenHeader.substring(7);
            return jwtUtil.extractUsername(token);
        }else{
            return null;
        }

    }

}
