package com.example.admindashboardproject.admin.service;

import com.example.admindashboardproject.admin.config.PasswordEncoder;
import com.example.admindashboardproject.admin.dto.AdminLoginRequest;
import com.example.admindashboardproject.admin.dto.AdminRequest;
import com.example.admindashboardproject.admin.dto.AdminResponse;
import com.example.admindashboardproject.admin.dto.SessionAdmin;
import com.example.admindashboardproject.admin.entity.Admins;
import com.example.admindashboardproject.admin.entity.Status;
import com.example.admindashboardproject.admin.global.exception.AdminStatusErrorCode;
import com.example.admindashboardproject.admin.global.exception.DuplicateEmailException;
import com.example.admindashboardproject.admin.global.exception.InvalidCredentialException;
import com.example.admindashboardproject.admin.global.exception.InvalidStatus;
import com.example.admindashboardproject.admin.repository.AdminRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)

public class AdminService {
    private final AdminRepository repository;
    private final PasswordEncoder passwordEncoder;

    //세션에 로그인 정보 저장할 때 쓰는 이름표. 오타 방지용으로 상수화
    public static final String SESSION_KEY = "LOGIN_ADMIN_ID";

    public AdminResponse signupadmin(AdminRequest request) {
        // 이메일 중복체크 - 이미 가입된 이메일이면 막기
        if (repository.findByEmail(request.getEmail()).isPresent()) {
            throw new DuplicateEmailException("이미 사용 중인 이메일입니다.");
        }
        String encodedPassword = passwordEncoder.encode(request.getPassword());
        Admins admin = new Admins( request.getName(),
                request.getEmail(),
                encodedPassword,
                request.getPhone(),
                request.getRole() );

        Admins savedadmin = repository.save(admin);
        return new AdminResponse(
                savedadmin.getId(),
                savedadmin.getName(),
                savedadmin.getEmail(),
                savedadmin.getPhone(),
                savedadmin.getRole(),
                savedadmin.getStatus());
    }
}
