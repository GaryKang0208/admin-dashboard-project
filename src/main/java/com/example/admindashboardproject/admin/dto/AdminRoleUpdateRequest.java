package com.example.admindashboardproject.admin.dto;

import com.example.admindashboardproject.admin.entity.Role;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter

// 역할 변경
public class AdminRoleUpdateRequest {

    @NotNull(message = "역할은 필수 입력값입니다.")
    private Role role;
}
