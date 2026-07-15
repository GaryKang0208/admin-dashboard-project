package com.example.admindashboardproject.admin.controller.auth;

import com.example.admindashboardproject.admin.dto.AdminLoginRequest;
import com.example.admindashboardproject.admin.dto.AdminRequest;
import com.example.admindashboardproject.admin.dto.AdminResponse;
import com.example.admindashboardproject.admin.dto.SessionAdmin;
import com.example.admindashboardproject.admin.service.AdminService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Getter
@RestController
@RequiredArgsConstructor
@RequestMapping("/commerce/admins")

// 회원가입/로그인/로그아웃
public class AdminController {
    private final AdminService service;

    @PostMapping("/signup")
    public ResponseEntity<AdminResponse> signup(@Valid @RequestBody AdminRequest request) {

        AdminResponse response = service.signupadmin(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<Void> login(@Valid @RequestBody AdminLoginRequest request, HttpSession session) {


        SessionAdmin sessionAdmin = service.loginadmin(request);
        session.setAttribute("loginAdmin", sessionAdmin);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpSession session) {
        session.invalidate();
        return ResponseEntity.ok().build();
    }
}
