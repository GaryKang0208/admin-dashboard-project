package com.example.admindashboardproject.product.dto;

// 상품 정보 수정
public record ProductUpdateRequest(
        String name,
        String category,
        Integer price
) {
}
