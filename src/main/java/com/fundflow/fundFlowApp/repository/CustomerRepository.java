package com.fundflow.fundFlowApp.repository;

import com.fundflow.fundFlowApp.dto.customer.BasicCustomerResDto;
import com.fundflow.fundFlowApp.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    @Query("SELECT c FROM Customer c WHERE c.nic=?1 OR c.email=?2")
    Customer checkCustomerIsExists(String nic, String email);

    @Query("SELECT new com.fundflow.fundFlowApp.dto.customer.BasicCustomerResDto(c.id, c.name, c.nic, c.email, c.income, c.creditScore) FROM Customer c")
    List<BasicCustomerResDto> getAllCustomer();
}
