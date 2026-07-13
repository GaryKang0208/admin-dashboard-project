package com.example.admindashboardproject.admin.service;

import com.example.admindashboardproject.admin.config.PasswordEncoder;
import com.example.admindashboardproject.admin.dto.AdminRequest;
import com.example.admindashboardproject.admin.dto.AdminResponse;
import com.example.admindashboardproject.admin.entity.Admins;
import com.example.admindashboardproject.admin.repository.AdminRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)

public class AdminService {
    private final AdminRepository repository;
    private final PasswordEncoder passwordEncoder;
    @Transactional
    public AdminResponse signupadmin(@Valid AdminRequest request) {
        if (repository.findByEmail(request.getEmail()).isPresent()) {
            throw new IllegalArgumentException("이미 사용 중인 이메일입니다.");
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
