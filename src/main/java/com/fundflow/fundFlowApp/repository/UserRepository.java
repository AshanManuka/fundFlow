package com.fundflow.fundFlowApp.repository;

import com.fundflow.fundFlowApp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);
}
