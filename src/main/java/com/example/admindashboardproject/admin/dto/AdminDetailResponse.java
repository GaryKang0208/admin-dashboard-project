package com.example.admindashboardproject.admin.dto;

import com.example.admindashboardproject.admin.entity.Role;
import com.example.admindashboardproject.admin.entity.Status;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor

// 관리자 리스트 각 항목 + 관리자 상세조회 + 내 프로필 조회에서 공통으로 사용
public class AdminDetailResponse {
    private final Long id;
    private final String name;
    private final String email;
    private final String phone;
    private final Role role;
    private final Status status;
    private final LocalDateTime createdAt;
    private final LocalDateTime approvedAt;
}
