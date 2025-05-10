package com.fundflow.fundFlowApp.service.impl;

import com.fundflow.fundFlowApp.dto.common.CommonResponse;
import com.fundflow.fundFlowApp.dto.loan.LoanDetailResDto;
import com.fundflow.fundFlowApp.dto.loan.LoanReqDto;
import com.fundflow.fundFlowApp.dto.loan.LoanResDto;
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
import java.util.List;
import java.util.Optional;

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
            log.info("eligible loan request of customer:{}",customer.getEmail());
        }else{
            status = "NOT ELIGIBLE";
            resMessage = "Your requested amount is not Eligible for a loan..!";
            log.info("not eligible loan request of customer:{}",customer.getEmail());
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
        log.info("lon request saved");

        return ResponseEntity.ok(new CommonResponse<>(true, resMessage));
    }

    @Override
    public ResponseEntity<?> getAllLoanByCustomer(String userName) {
        List<LoanResDto> loanList = loanRepository.getAllLoanByEmail(userName);
        log.info("get and return loan history by customer email: {}",userName);
        return ResponseEntity.ok(new CommonResponse<>(true, loanList));
    }

    @Override
    public ResponseEntity<?> getAllLoans() {
        List<LoanDetailResDto> loanList = loanRepository.getAllLoans();
        log.info("Get all loans for admin");
        return ResponseEntity.ok(new CommonResponse<>(true,loanList));
    }

    @Override
    public ResponseEntity<?> filterLoans(String loanStatus, double minScore, double maxScore) {

        if(minScore == 0){
            minScore = 300;
        }
        if(maxScore == 0){
            maxScore = 850;
        }

        if(!loanStatus.isEmpty()){
            List<LoanDetailResDto> loanList = loanRepository.filterLoanByStatusAndScore(loanStatus, minScore, maxScore);
            log.info("filter and return loans by status:{}, minScore:{}, maxScore{}",loanStatus, minScore, maxScore);
            return ResponseEntity.ok(new CommonResponse<>(true, loanList));
        }else{
            List<LoanDetailResDto> loanList = loanRepository.filterLoanByScore(minScore, maxScore);
            log.info("filter and return loans by  minScore:{}, maxScore{}",minScore, maxScore);
            return ResponseEntity.ok(new CommonResponse<>(true, loanList));
        }

    }

    @Override
    public ResponseEntity<?> getLoansbyCustomer(long loanId) {
        Optional<Loan> loan = loanRepository.findById(loanId);
        if(loan.isPresent()){
            Loan existLoan = loan.get();
            long customerId = existLoan.getCustomer().getId();
            List<LoanDetailResDto> loanList = loanRepository.getAllLoansByCustomerId(customerId);
            log.info("get and return loans by cusId:{}",customerId);
            return ResponseEntity.ok(new CommonResponse<>(true, loanList));
        }else{
            log.error("No loan found loanId:{}",loanId);
            return ResponseEntity.ok(new CommonResponse<>(true, "No Customer found"));
        }
    }

    @Override
    public ResponseEntity<?> UpdateLoanStatus(long loanId, String status) {

        Optional<Loan> loan = loanRepository.findById(loanId);

        if(loan.isPresent()){
            Loan existLoan = loan.get();
            existLoan.setStatus(status);
            existLoan.setUpdatedDate(new Date());

            loanRepository.save(existLoan);
            log.info("updated loan status");
            return ResponseEntity.ok(new CommonResponse<>(true, "Loan Updated Successfully..!"));

        }else{
            log.error("no loan found loanId:{}",loanId);
            return ResponseEntity.ok(new CommonResponse<>(false, "No loan found"));
        }

    }


}
