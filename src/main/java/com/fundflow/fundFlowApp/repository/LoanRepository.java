package com.fundflow.fundFlowApp.repository;

import com.fundflow.fundFlowApp.entity.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface LoanRepository extends JpaRepository<Loan,Long> {

    @Query("SELECT COUNT(l.id) FROM Loan l WHERE l.customer.id = ?1 AND (l.status = 'ONGOING' OR l.status = 'ELIGIBLE')")
    int getLoanCountByCustomer(Long id);

}
