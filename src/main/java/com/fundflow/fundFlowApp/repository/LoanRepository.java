package com.fundflow.fundFlowApp.repository;

import com.fundflow.fundFlowApp.dto.loan.LoanDetailResDto;
import com.fundflow.fundFlowApp.dto.loan.LoanResDto;
import com.fundflow.fundFlowApp.entity.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LoanRepository extends JpaRepository<Loan,Long> {

    @Query("SELECT COUNT(l.id) FROM Loan l WHERE l.customer.id = ?1 AND (l.status = 'ONGOING' OR l.status = 'ELIGIBLE')")
    int getLoanCountByCustomer(Long id);

    @Query("SELECT new com.fundflow.fundFlowApp.dto.loan.LoanResDto(l.id, l.loanAmount, l.installment, l.requestedDate, l.status, l.reason) FROM Loan l WHERE l.customer.email = ?1")
    List<LoanResDto> getAllLoanByEmail(String email);

    @Query("SELECT new com.fundflow.fundFlowApp.dto.loan.LoanDetailResDto(l.id, c.name, c.email, c.nic, c.income, c.creditScore, l.status, l.loanAmount, l.installment, l.reason, l.requestedDate) FROM Loan l JOIN l.customer c")
    List<LoanDetailResDto> getAllLoans();

}
