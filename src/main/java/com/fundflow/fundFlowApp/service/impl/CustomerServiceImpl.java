package com.fundflow.fundFlowApp.service.impl;

import com.fundflow.fundFlowApp.dto.common.CommonResponse;
import com.fundflow.fundFlowApp.dto.customer.CustomerRegisterReqDto;
import com.fundflow.fundFlowApp.entity.Customer;
import com.fundflow.fundFlowApp.entity.User;
import com.fundflow.fundFlowApp.repository.CustomerRepository;
import com.fundflow.fundFlowApp.repository.UserRepository;
import com.fundflow.fundFlowApp.service.CustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;

@Service
@RequiredArgsConstructor
@Log4j2
public class CustomerServiceImpl implements CustomerService {

    private final UserRepository userRepository;
    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public ResponseEntity<?> registerCustomer(CustomerRegisterReqDto reqBody) {

        try {
            if (!StringUtils.hasText(reqBody.getName()) || !StringUtils.hasText(reqBody.getNic()) || !StringUtils.hasText(reqBody.getEmail()) || reqBody.getMonthlyIncome() < 0 || reqBody.getCreditScore() < 0) {
                log.error("Some inputs missing, data Obj: {}", reqBody);
                return ResponseEntity.ok(new CommonResponse<>(false, "Empty Input"));
            }

            log.info("Checked Input Data:{}", reqBody);
            Customer tempCustomer = customerRepository.checkCustomerIsExists(reqBody.getNic(), reqBody.getEmail());


            if (tempCustomer != null) {
                log.error("customer data already exists, nic:{} or email:{}", reqBody.getNic(), reqBody.getEmail());
                return ResponseEntity.ok(new CommonResponse<>(false, "Customer Data Already Exists"));
            }


            User tempUser = User.builder()
                    .username(reqBody.getEmail())
                    .password(passwordEncoder.encode(reqBody.getEmail()))
                    .role("CUSTOMER")
                    .build();

            tempUser = userRepository.save(tempUser);
            log.info("created user account, userId:{}", tempUser.getId());

            tempCustomer = Customer.builder()
                    .nic(reqBody.getNic())
                    .name(reqBody.getName())
                    .email(reqBody.getEmail())
                    .income(reqBody.getMonthlyIncome())
                    .creditScore(reqBody.getCreditScore())
                    .createdDate(new Date())
                    .updateDate(new Date())
                    .user(tempUser)
                    .build();

            tempCustomer = customerRepository.save(tempCustomer);
            log.info("customer registered, customerId:{}", tempCustomer.getId());

            return ResponseEntity.ok(new CommonResponse<>(true, "Customer Registered Successful..!"));

        }catch (Exception exception){
            log.error("Exception occurred during customer registration: {}", exception.getMessage(), exception);
            return ResponseEntity.internalServerError().body(new CommonResponse<>(false, "Something went wrong"));
        }



    }
}
