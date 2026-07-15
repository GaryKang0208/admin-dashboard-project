package com.example.admindashboardproject.customer.dto;

import lombok.Getter;

@Getter
public class DeleteCustomerResponse {
    private String message;

    public DeleteCustomerResponse(String message) {
        this.message = message;
    }
}
