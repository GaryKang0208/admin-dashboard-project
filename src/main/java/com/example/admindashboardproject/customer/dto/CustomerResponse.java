package com.example.admindashboardproject.customer.dto;

import lombok.Getter;

@Getter
public class CustomerResponse {
    private final Long id;
    private final String name;
    private final String email;
    private final String phone;

    public CustomerResponse(Long id, String name, String email, String phone, int totalOrders, long totalPurchaseAmount) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.totalOrders = totalOrders;
        this.totalPurchaseAmount = totalPurchaseAmount;
    }

    private final int totalOrders;
    private final long totalPurchaseAmount;
}
