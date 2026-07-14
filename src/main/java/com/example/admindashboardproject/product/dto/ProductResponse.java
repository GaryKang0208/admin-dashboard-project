package com.example.admindashboardproject.product.dto;

import com.example.admindashboardproject.product.entity.Product;
import com.example.admindashboardproject.product.entity.ProductStatus;

import java.time.LocalDateTime;

public class ProductResponse(
        Long id,
        String name,
        String category,
        Integer price,
        Integer stock,
        ProductStatus status,
        LocalDateTime createdAt,
        String registeredAdminName,
        String registerAdminEmail
){
    // -> 변환 로직을 여기 하나로 모아두면 앞으로 필드가 늘어나도 이 메서드 하나만 고치면 됨
    public static ProductResponse from(Product product){
        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getCategory(),
                product.getPrice(),
                product.getStock(),
                product.getStatus(),
                product.getCreatedAt(),
                product.getRegisteredAdmin().getName(), // FetchType.LAZY -> 이 시점에 실제 DB 추가 조회 발생
                product.getRegisteredAdmin().getEmail()  // 리스트 조회 시 문제 가능성은 Service 단계에서 다룰 예정
        );
    }
}
