package com.example.admindashboardproject.product.dto;
import com.example.admindashboardproject.product.entity.Product;
import com.example.admindashboardproject.product.entity.ProductStatus;
import java.time.LocalDateTime;

public record ProductResponse(
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
    public static ProductResponse from(Product product){
        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getCategory(),
                product.getPrice(),
                product.getStock(),
                product.getStatus(),
                product.getCreatedAt(),
                product.getRegisteredAdmin().getName(),
                product.getRegisteredAdmin().getEmail()
        );
    }
}
