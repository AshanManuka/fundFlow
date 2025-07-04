package com.fundflow.fundFlowApp.controller;

import com.fundflow.fundFlowApp.service.CustomerService;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Paths;

@RestController
@Log4j2
@RequiredArgsConstructor
@RequestMapping(value = "/public")
public class PublicController {

    //private static final String UPLOAD_DIR = "uploads/";
    private final CustomerService customerService;

    @GetMapping("/app-status")
    public ResponseEntity<String> getAppStatus() {
        return ResponseEntity.ok("Application is running");
    }

    @PostMapping("/file-upload")
    public String uploadFile(@RequestParam("file") MultipartFile uploadedFile, @RequestParam("name") String uploadedName){
        return customerService.saveDocuments(uploadedFile, uploadedName);
    }

    @GetMapping("/get-image")
    public ResponseEntity<Resource> getImage(@RequestParam Long imgId){
        return customerService.getImageById(imgId);
    }

}

