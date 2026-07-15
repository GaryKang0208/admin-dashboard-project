package com.example.admindashboardproject.product.dto;

import com.example.admindashboardproject.product.entity.ProductStatus;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ProductCreateRequest(
        @NotBlank(message = "상품명은 필수입니다.")
        String name,

        @NotBlank(message = "카테고리는 필수입니다.")
        String category,

        @NotNull(message = "가격은 필수입니다")
        @Min(value = 0, message = "가격은 0 이상이어야 합니다")
        Integer price,

        @NotNull(message = "재고는 필수입니다")
        @Min(value = 0, message = "재고는 0 이상이어야 합니다")
        Integer stock,

        @NotNull(message = "상태는 필수입니다")
        ProductStatus status

) {
}
