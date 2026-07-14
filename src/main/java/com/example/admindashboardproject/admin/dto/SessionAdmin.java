package com.example.admindashboardproject.admin.dto;

import com.example.admindashboardproject.admin.entity.Role;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor

// 세션 저장용
public class SessionAdmin {
    private final Long id;
    private final String email;
    private final Role role;
}
