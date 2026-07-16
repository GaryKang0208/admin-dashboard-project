package com.example.admindashboardproject.customer.dto;
import jakarta.validation.constraints.*;
import lombok.Getter;

@Getter
public class CustomerUpdateRequest {
    @Size(min=2,max=10)
    private String name;
    @Email(message = "이메일 형식이 올바르지 않습니다")
    private String email;
    @Pattern(regexp = "^010-\\d{4}-\\d{4}$",message = "전화번호 형식이 올바르지 않습니다.")
    private String phone;
}
