package com.example.admindashboardproject.product.exception;

// 상품 상세/수정/삭제 등에서 존재하지 않는 ID로 조회 시 발생시키는 전용 예외
public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException(String message) {
        super(message);
    }
}