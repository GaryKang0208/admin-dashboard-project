package com.example.admindashboardproject.admin.controller.admin;

import com.example.admindashboardproject.admin.dto.AdminDetailResponse;
import com.example.admindashboardproject.admin.dto.AdminSearchRequest;
import com.example.admindashboardproject.admin.dto.PageResponse;
import com.example.admindashboardproject.admin.service.AdminManageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/commerce/admins")
public class AdminManageController {

    private final AdminManageService service;

    // 관리자 리스트 조회
    @GetMapping
    public ResponseEntity<PageResponse<AdminDetailResponse>> getAdminList(@ModelAttribute AdminSearchRequest condition) {
        return ResponseEntity.ok(service.getAdminList(condition));
    }

    // 관리자 상세 조회
    @GetMapping("/{id}")
    public ResponseEntity<AdminDetailResponse> getAdminDetail(@PathVariable Long id) {
        return ResponseEntity.ok(service.getAdminDetail(id));
    }
}