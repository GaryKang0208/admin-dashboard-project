package com.example.admindashboardproject.admin.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter

// 비밀번호 변경
public class PasswordChangeRequest {

    @NotBlank(message = "현재 비밀번호는 필수 입력값입니다.")
    private String currentPassword;

    @NotBlank(message = "새 비밀번호는 필수 입력값입니다.")
    @Size(min = 8, max = 20, message = "비밀번호는 최소 8자 이상입니다.")
    private String newPassword;
}
