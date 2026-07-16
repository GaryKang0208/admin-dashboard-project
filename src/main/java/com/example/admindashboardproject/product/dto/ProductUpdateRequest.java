package com.example.admindashboardproject.product.dto;

public record ProductUpdateRequest(
        String name,
        String category,
        Integer price
) {
}
