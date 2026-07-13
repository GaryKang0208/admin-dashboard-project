package com.example.admindashboardproject.admin.controller;

import com.example.admindashboardproject.admin.dto.AdminRequest;
import com.example.admindashboardproject.admin.dto.AdminResponse;
import com.example.admindashboardproject.admin.service.AdminService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.validation.Valid;

@Getter
@RestController
@RequiredArgsConstructor
@RequestMapping("/commerce/admins")

public class AdminController {
    private final AdminService service;

    @PostMapping("/signup")
    public ResponseEntity<AdminResponse> signup(@Valid @RequestBody AdminRequest request) {

        AdminResponse response = service.signupadmin(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

}
