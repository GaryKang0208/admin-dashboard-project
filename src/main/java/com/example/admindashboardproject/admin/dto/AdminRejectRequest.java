package com.example.admindashboardproject.admin.dto;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class AdminRejectRequest {
    @NotBlank(message = "거부 사유는 필수 입력값입니다.")
    private String rejectReason;
}
