package com.example.admindashboardproject.admin.controller.admin;

import com.example.admindashboardproject.admin.dto.*;
import com.example.admindashboardproject.admin.global.exception.UnauthorizedException;
import com.example.admindashboardproject.admin.service.AdminManageService;
import jakarta.servlet.http.HttpSession;
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

    // 관리자 상태 변경
    @PatchMapping("/{id}/status")
    public ResponseEntity<AdminDetailResponse> changeAdminStatus(@PathVariable Long id,
                                                                 @Valid @RequestBody AdminStatusUpdateRequest request) {
        return ResponseEntity.ok(service.changeAdminStatus(id, request));
    }

    // 관리자 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAdmin(@PathVariable Long id) {
        service.deleteAdmin(id);
        return ResponseEntity.noContent().build();
    }

    // 관리자 승인
    @PatchMapping("/{id}/approve")
    public ResponseEntity<AdminDetailResponse> approveAdmin(@PathVariable Long id) {
        return ResponseEntity.ok(service.approveAdmin(id));
    }

    // 관리자 거부
    @PatchMapping("/{id}/reject")
    public ResponseEntity<AdminDetailResponse> rejectAdmin(@PathVariable Long id,
                                                           @Valid @RequestBody AdminRejectRequest request) {
        return ResponseEntity.ok(service.rejectAdmin(id, request));
    }

    // 세션에서 로그인한 관리자 정보를 꺼내는 헬퍼 메서드
    private SessionAdmin getLoginAdmin(HttpSession session) {
        SessionAdmin loginAdmin = (SessionAdmin) session.getAttribute("loginAdmin");
        if (loginAdmin == null) {
            throw new UnauthorizedException("로그인이 필요합니다.");
        }
        return loginAdmin;
    }

    // 내프로필 조회
    @GetMapping("/me")
    public ResponseEntity<AdminDetailResponse> getMyProfile(HttpSession session) {
        SessionAdmin loginadmin = getLoginAdmin(session);

        return ResponseEntity.ok(service.getAdminDetail(loginadmin.getId()));
    }
}