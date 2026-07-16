package com.example.admindashboardproject.admin.controller.admin;
import com.example.admindashboardproject.admin.dto.*;
import com.example.admindashboardproject.admin.exception.UnauthorizedException;
import com.example.admindashboardproject.admin.service.AdminManageService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
// 관리자 Controller CRUD 구현
@RestController
@RequiredArgsConstructor
@RequestMapping("/commerce/admins")
public class AdminManageController {
    private final AdminManageService service;

    @GetMapping
    public ResponseEntity<PageResponse<AdminBaseResponse>> getAdminList(@ModelAttribute AdminSearchRequest condition) {
        return ResponseEntity.ok(service.getAdminList(condition));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AdminDetailResponse> getAdminDetail(@PathVariable Long id) {
        return ResponseEntity.ok(service.getAdminDetail(id));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<AdminBaseResponse> updateAdmin(
            @PathVariable Long id,
            @Valid @RequestBody AdminUpdateRequest request) {
        return ResponseEntity.ok(service.updateAdmin(id, request));
    }

    @PatchMapping("/{id}/role")
    public ResponseEntity<AdminBaseResponse> changeAdminRole(
            @PathVariable Long id,
            @Valid @RequestBody AdminRoleUpdateRequest request) {
        return ResponseEntity.ok(service.changeAdminRole(id, request));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<AdminBaseResponse> changeAdminStatus(
            @PathVariable Long id,
            @Valid @RequestBody AdminStatusUpdateRequest request) {
        return ResponseEntity.ok(service.changeAdminStatus(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAdmin(@PathVariable Long id) {
        service.deleteAdmin(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/approve")
    public ResponseEntity<AdminBaseResponse> approveAdmin(@PathVariable Long id) {
        return ResponseEntity.ok(service.approveAdmin(id));
    }

    @PatchMapping("/{id}/reject")
    public ResponseEntity<AdminBaseResponse> rejectAdmin(
            @PathVariable Long id,
            @Valid @RequestBody AdminRejectRequest request) {
        return ResponseEntity.ok(service.rejectAdmin(id, request));
    }

    @GetMapping("/me")
    public ResponseEntity<AdminBaseResponse> getMyProfile(HttpSession session) {
        SessionAdmin loginadmin = getLoginAdmin(session);

        return ResponseEntity.ok(service.getAdminBase(loginadmin.getId()));
    }

    @PatchMapping("/me")
    public ResponseEntity<AdminBaseResponse> updateMyProfile(
            HttpSession session,
            @Valid @RequestBody AdminUpdateRequest request) {
        SessionAdmin loginadmin = getLoginAdmin(session);
        return ResponseEntity.ok(service.updateAdmin(loginadmin.getId(), request));
    }

    @PatchMapping("/me/password")
    public ResponseEntity<Void> changedPassword(
            HttpSession session,
            @Valid @RequestBody PasswordChangeRequest request) {
        SessionAdmin loginadmin = getLoginAdmin(session);
        service.changePassword(loginadmin.getId(), request);
        return ResponseEntity.ok().build();
    }

    private SessionAdmin getLoginAdmin(HttpSession session) {
        SessionAdmin loginAdmin = (SessionAdmin) session.getAttribute("loginAdmin");
        if (loginAdmin == null) {
            throw new UnauthorizedException("로그인이 필요합니다.");
        }
        return loginAdmin;
    }
}