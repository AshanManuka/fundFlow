package com.fundflow.fundFlowApp.service.impl;

import com.fundflow.fundFlowApp.dto.common.CommonResponse;
import com.fundflow.fundFlowApp.dto.loan.LoanReqDto;
import com.fundflow.fundFlowApp.entity.Customer;
import com.fundflow.fundFlowApp.entity.Loan;
import com.fundflow.fundFlowApp.repository.CustomerRepository;
import com.fundflow.fundFlowApp.repository.LoanRepository;
import com.fundflow.fundFlowApp.service.LoanService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@Log4j2
@RequiredArgsConstructor
public class LoanServiceImpl implements LoanService {

    private final CustomerRepository customerRepository;
    private final LoanRepository loanRepository;


    @Override
    public ResponseEntity<?> saveLoanRequest(String userName, LoanReqDto reqDto) {
        Customer customer = customerRepository.findByEmail(userName);
        int existsLoanCount = loanRepository.getLoanCountByCustomer(customer.getId());
        double emi = reqDto.getAmount()/reqDto.getInstallment();
        double totalScore = 50;
        double maxLoanAmount = 1000000;
        String status = "";
        String resMessage = "";

        if(reqDto.getAmount() > maxLoanAmount || reqDto.getAmount() < 0){
            log.error("Did not enter valid amount, {}", reqDto.getAmount() );
            return ResponseEntity.ok(new CommonResponse<>(false, "Enter Valid Amount"));
        }

        if (emi > customer.getIncome() * 0.4) {
            totalScore -= 25;
        }

        if(existsLoanCount > 2){
            totalScore -= 25;
        }else if(existsLoanCount == 2){
            totalScore -= 15;
        }else if(existsLoanCount == 1){
            totalScore -= 5;
        }

        double score = (1 - (reqDto.getAmount() / maxLoanAmount)) * 25;
        totalScore += score;

        double boostScore = ((double)(customer.getCreditScore() - 300) / (850 - 300)) * 25;
        totalScore += boostScore;
        totalScore = Math.min(100, totalScore);

        if(totalScore >= 70){
            status = "ELIGIBLE";
            resMessage = "Your requested amount is Eligible for a loan..!";
        }else{
            status = "NOT ELIGIBLE";
            resMessage = "Your requested amount is not Eligible for a loan..!";
        }

        Loan loan = Loan.builder()
                .loanAmount(reqDto.getAmount())
                .installment(reqDto.getInstallment())
                .reason(reqDto.getReason())
                .requestedDate(new Date())
                .updatedDate(new Date())
                .status(status)
                .customer(customer)
                .build();
        loanRepository.save(loan);

        return ResponseEntity.ok(new CommonResponse<>(true, resMessage));
    }


}
