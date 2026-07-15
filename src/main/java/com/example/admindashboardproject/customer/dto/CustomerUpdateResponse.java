package com.example.admindashboardproject.customer.dto;

import com.example.admindashboardproject.customer.enums.CustomerStatus;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CustomerUpdateResponse {
    private final Long id;
    private final String name;
    private final String email;
    private final String phone;
    private final CustomerStatus status;
    private final LocalDateTime updatedAt;

    public CustomerUpdateResponse(Long id, String name, String email, String phone, CustomerStatus status, LocalDateTime updatedAt) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.status = status;
        this.updatedAt = updatedAt;
    }
}
