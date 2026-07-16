package com.example.admindashboardproject.admin.dto;
import com.example.admindashboardproject.admin.entity.Status;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class AdminStatusUpdateRequest {
    @NotNull(message = "상태는 필수 입력값입니다.")
    private Status status;
}
