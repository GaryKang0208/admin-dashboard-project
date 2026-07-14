package com.example.admindashboardproject.admin.controller.admin;

import com.example.admindashboardproject.admin.dto.*;
import com.example.admindashboardproject.admin.service.AdminManageService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    // 관리자 정보 수정
    @PatchMapping("/{id}")
    public ResponseEntity<AdminDetailResponse> updateAdmin(@PathVariable Long id,
                                                           @Valid @RequestBody AdminUpdateRequest request) {
        return ResponseEntity.ok(service.updateAdmin(id, request));
    }

    // 관리자 역할 변경
    @PatchMapping("/{id}/role")
    public ResponseEntity<AdminDetailResponse> changeAdminRole(@PathVariable Long id,
                                                               @Valid @RequestBody AdminRoleUpdateRequest request) {
        return ResponseEntity.ok(service.changeAdminRole(id, request));
    }
}