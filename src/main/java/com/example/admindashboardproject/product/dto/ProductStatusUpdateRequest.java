package com.example.admindashboardproject.product.dto;

import com.example.admindashboardproject.product.entity.ProductStatus;
import jakarta.validation.constraints.NotNull;

// 상품 상태 변경이 별도 엔드포인트(PATCH /products/{id}/status)라 DTO도 분리
public record ProductStatusUpdateRequest(
        @NotNull(message = "변경할 상태값은 필수입니다.")
        ProductStatus status
) {
}
