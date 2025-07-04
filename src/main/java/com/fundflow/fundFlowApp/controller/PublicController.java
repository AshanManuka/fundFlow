package com.fundflow.fundFlowApp.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Paths;

@RestController
@Log4j2
@RequestMapping(value = "/public")
public class PublicController {

    //private static final String UPLOAD_DIR = "uploads/";

    @GetMapping("/app-status")
    public ResponseEntity<String> getAppStatus() {
        return ResponseEntity.ok("Application is running");
    }

    @PostMapping("/file-upload")
    public String uploadFile(@RequestParam("file") MultipartFile uploadedFile, @RequestParam("name") String uploadedName){

        try {
            if (uploadedFile.isEmpty()) {
                return "Uploaded file is empty!";
            }

            //String originalFileName = Paths.get(uploadedFile.getOriginalFilename()).getFileName().toString();
            String originalFileName = uploadedFile.getOriginalFilename();
            String uploadPath = System.getProperty("user.dir") + File.separator + "uploads";

            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) uploadDir.mkdirs();

            File destination = new File(uploadPath + File.separator + originalFileName);
            uploadedFile.transferTo(destination);

            log.info("Saved file '{}' as '{}'", uploadedName, originalFileName);
            return "The file has uploaded with name: " + uploadedName;

        } catch (Exception e) {
            e.printStackTrace(); // view full error in logs
            return "Error: " + e.getMessage();
        }


    }

}

