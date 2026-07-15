package com.example.admindashboardproject.admin.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter

// 정보수정/내프로필 수정 공용
public class AdminUpdateRequest {

    @NotBlank(message = "이름은 필수 입력값입니다.")
    private String name;

    @NotBlank(message = "이메일은 필수 입력값입니다.")
    @Email(message = "이메일 형식이 아닙니다.")
    private String email;

    @NotBlank(message = "전화번호는 필수 입력입니다.")
    @Pattern(regexp = "^010-\\d{4}-\\d{4}$",
             message = "전화번호는 010-XXXX-XXXX 형식이어야 합니다.")
    private String phone;
}
