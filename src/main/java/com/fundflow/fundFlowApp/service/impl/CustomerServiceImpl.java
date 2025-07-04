package com.fundflow.fundFlowApp.service.impl;

import com.fundflow.fundFlowApp.dto.common.CommonResponse;
import com.fundflow.fundFlowApp.dto.customer.BasicCustomerResDto;
import com.fundflow.fundFlowApp.dto.customer.CustomerRegisterReqDto;
import com.fundflow.fundFlowApp.entity.Customer;
import com.fundflow.fundFlowApp.entity.Uploads;
import com.fundflow.fundFlowApp.entity.User;
import com.fundflow.fundFlowApp.repository.CustomerRepository;
import com.fundflow.fundFlowApp.repository.UploadsRepository;
import com.fundflow.fundFlowApp.repository.UserRepository;
import com.fundflow.fundFlowApp.service.CustomerService;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Log4j2
@Transactional
public class CustomerServiceImpl implements CustomerService {

    private final UserRepository userRepository;
    private final CustomerRepository customerRepository;
    private final UploadsRepository uploadsRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;

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

    @Override
    public ResponseEntity<?> getAllCustomer() {
        List<BasicCustomerResDto> customerList = customerRepository.getAllCustomer();
        log.info("loaded and return customer list");
        return ResponseEntity.ok(new CommonResponse<>(true,customerList));
    }

    @Override
    public ResponseEntity<?> searchCustomer(String cusKeyword) {
        List<BasicCustomerResDto> customerList = customerRepository.searchCustomerByKeyword(cusKeyword);
        log.info("loaded and return customer list by keyword :{}",cusKeyword);
        return ResponseEntity.ok(new CommonResponse<>(true,customerList));
    }

    @Override
    public ResponseEntity<?> updateCustomer(Long cusId, CustomerRegisterReqDto reqDto) {
        Optional<Customer> customer = customerRepository.findById(cusId);
        if (customer.isEmpty()) {
            log.error("No customer found, customerId: {}",cusId);
            return ResponseEntity.ok(new CommonResponse<>(false,"No Customer Found"));
        }else{

            Customer existsCustomer = customer.get();
            User existsUser = existsCustomer.getUser();

            if(!existsCustomer.getEmail().equals(reqDto.getEmail())){
                Customer checkCustomer = customerRepository.findByEmail(reqDto.getEmail());
                if(checkCustomer != null){
                    log.error("New email already exists, {}",reqDto.getEmail());
                    return ResponseEntity.ok(new CommonResponse<>(false,"New Email Already Exists..!"));
                }
            }

            existsUser.setUsername(reqDto.getEmail());
            existsUser.setPassword(passwordEncoder.encode(reqDto.getEmail()));
            existsUser = userRepository.save(existsUser);

            modelMapper.map(reqDto,existsCustomer);
            existsCustomer.setUpdateDate(new Date());
            existsCustomer.setUser(existsUser);
            customerRepository.save(existsCustomer);

            log.info("customer obj : {}",customer);
            return ResponseEntity.ok(new CommonResponse<>(true, "Customer Details Updated Successfully..!"));
        }

    }

    @Override
    public ResponseEntity<?> deleteCustomer(long customerId) {
        Optional<Customer> customer = customerRepository.findById(customerId);
        if(customer.isPresent()){
            Customer existsCustomer = customer.get();
            customerRepository.delete(existsCustomer);
            log.info("customer Deleted : {}",customerId);
            return ResponseEntity.ok(new CommonResponse<>(true, "Customer Deleted Successfully..!"));
        }else{
            log.error("customer not found by Id : {}",customerId);
            return ResponseEntity.ok(new CommonResponse<>(false, "Customer Not Found"));
        }
    }

    @Override
    public String saveDocuments(MultipartFile uploadedFile, String uploadedName) {
        log.info("catch document from service");

        if(uploadedFile.isEmpty()){
            return "the uploaded file is empty";
        }

        String originalFileName = Paths.get(uploadedFile.getOriginalFilename()).getFileName().toString();
        String uploadPath = System.getProperty("user.dir")+ File.separator+"uploads";


        File uploadDir = new File(uploadPath);
        if(!uploadDir.exists()){
            uploadDir.mkdirs();
        }

        File lastDest = new File(uploadDir +File.separator+ UUID.randomUUID()+"_"+originalFileName);
        try {
            uploadedFile.transferTo(lastDest);

            Uploads nwUpload = Uploads.builder()
                    .location("uploads/" + lastDest.getName())
                    .build();

            Uploads savedUpload = uploadsRepository.save(nwUpload);

            if(savedUpload == null){
                return "Something went wrong";
            }else{
                return "Ducument uploaded succesfully.!";
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public ResponseEntity<Resource> getImageById(Long imgId) {

        Optional<Uploads> uploadOpt = uploadsRepository.findById(imgId);
        if (uploadOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Uploads upload = uploadOpt.get();
        String filePath = upload.getLocation();

        try{

            File file = new File(System.getProperty("user.dir") +File.separator+ filePath);

            if(!file.exists()){
                return ResponseEntity.notFound().build();
            }

            Resource resource = new UrlResource(file.toURI());

            String contentType = Files.probeContentType(file.toPath());

            if(contentType == null){
                contentType = "application/octet-stream"; // default fallback
            }

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .body(resource);


        }catch(Exception e){
            e.printStackTrace();
        }

        return null;
    }


}
