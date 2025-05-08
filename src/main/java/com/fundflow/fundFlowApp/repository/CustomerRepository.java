package com.fundflow.fundFlowApp.repository;

import com.fundflow.fundFlowApp.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    @Query("SELECT c FROM Customer c WHERE c.nic=?1 OR c.email=?2")
    Customer checkCustomerIsExists(String nic, String email);
}
