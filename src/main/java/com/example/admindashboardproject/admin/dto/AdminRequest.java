package com.example.admindashboardproject.admin.dto;

import com.example.admindashboardproject.admin.entity.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter

public class AdminRequest {
    @NotBlank(message = "이름은 필수 입력값입니다.")
    private String name;

    @NotBlank(message = "이메일은 필수 입력값입니다.")
    @Email(message = "이메일 형식이 아닙니다.")
    private String email;

    @NotBlank(message = "비밀번호는 필수 입력값입니다.")
    @Size(min = 8, max = 20, message = "비밀번호는 최소 8자 이상입니다.")
    private String password;

    @NotBlank(message = "전화번호는 필수 입력입니다.")
    @Pattern(regexp = "^010-\\d{4}-\\d{4}$",
             message = "전화번호는 010-XXXX-XXXX 형식이어야 합니다.")
    private String phone;

    private Role role;
}
