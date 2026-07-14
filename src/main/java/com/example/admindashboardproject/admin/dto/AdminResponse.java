package com.example.admindashboardproject.admin.dto;

import com.example.admindashboardproject.admin.entity.Role;
import com.example.admindashboardproject.admin.entity.Status;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor

// 회원가입용
public class AdminResponse {
    private final String name;
    private final String email;
}
