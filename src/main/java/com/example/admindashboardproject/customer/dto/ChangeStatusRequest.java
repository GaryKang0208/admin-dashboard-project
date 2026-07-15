package com.example.admindashboardproject.customer.dto;



import com.example.admindashboardproject.customer.enums.CustomerStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;


@Getter
public class ChangeStatusRequest {
    @NotNull(message = "고객 상태는 필수 입니다.")
    private CustomerStatus status;
}
