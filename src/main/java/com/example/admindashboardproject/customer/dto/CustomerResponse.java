package com.example.admindashboardproject.customer.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CustomerResponse {
    private final Long id;
    private final String name;
    private final String email;
    private final String phone;
    private final String status;
    private final int totalOrders;
    private final long totalPurchaseAmount;
    private final LocalDateTime createdAt;

    public CustomerResponse(Long id, String name, String email, String phone, String status, int totalOrders, long totalPurchaseAmount, LocalDateTime createdAt) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.status = status;
        this.totalOrders = totalOrders;
        this.totalPurchaseAmount = totalPurchaseAmount;
        this.createdAt = createdAt;
    }
}
