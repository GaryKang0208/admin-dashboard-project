package com.example.admindashboardproject.customer.dto;


import com.example.admindashboardproject.customer.enums.CustomerStatus;
import lombok.Getter;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Getter
public class ChangeStatusResponse {
    private final Long id;
    private final String name;
    private final CustomerStatus status;
    private final LocalDateTime updatedAt;

    public ChangeStatusResponse(Long id, String name, CustomerStatus status, LocalDateTime updatedAt) {
        this.id = id;
        this.name = name;
        this.status = status;
        this.updatedAt = updatedAt;
    }
}
