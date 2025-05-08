package com.fundflow.fundFlowApp.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Log4j2
@RequestMapping(value = "/public")
public class PublicController {

    @GetMapping("/app-status")
    public ResponseEntity<String> getAppStatus() {
        return ResponseEntity.ok("Application is running");
    }

}
