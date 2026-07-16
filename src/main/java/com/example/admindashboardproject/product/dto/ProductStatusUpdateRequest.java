package com.example.admindashboardproject.product.dto;
import com.example.admindashboardproject.product.entity.ProductStatus;
import jakarta.validation.constraints.NotNull;

public record ProductStatusUpdateRequest(
        @NotNull(message = "변경할 상태값은 필수입니다.")
        ProductStatus status
) {
}
