package com.fundflow.fundFlowApp.service;

import com.fundflow.fundFlowApp.dto.customer.CustomerRegisterReqDto;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface CustomerService {
    ResponseEntity<?> registerCustomer(CustomerRegisterReqDto reqBody);

    ResponseEntity<?> getAllCustomer();

    ResponseEntity<?> searchCustomer(String cusKeyword);

    ResponseEntity<?> updateCustomer(Long cusId, CustomerRegisterReqDto reqDto);

    ResponseEntity<?> deleteCustomer(long customerId);

    String saveDocuments(MultipartFile uploadedFile, String uploadedName);

    ResponseEntity<Resource> getImageById(Long imgId);
}
