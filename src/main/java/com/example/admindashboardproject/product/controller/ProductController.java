package com.example.admindashboardproject.product.controller;

import com.example.admindashboardproject.admin.dto.SessionAdmin;
import com.example.admindashboardproject.product.dto.*;
import com.example.admindashboardproject.product.entity.ProductStatus;
import com.example.admindashboardproject.product.service.ProductService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    // 상품 등록
    @PostMapping
    public ResponseEntity<ProductResponse> register(
            @Valid @RequestBody ProductCreateRequest request,
            HttpSession session
    ) {
        // 세션에서 로그인 정보 가져오기
        SessionAdmin sessionAdmin = (SessionAdmin) session.getAttribute("loginAdmin");

        // 로그인 안 한 상태(null)면 여기서 막음
        if (sessionAdmin == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        }

        Long adminId = sessionAdmin.getId();

        ProductResponse response = productService.register(request, adminId);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    // 상품 리스트 조회
    @GetMapping
    public ResponseEntity<PageResponse<ProductResponse>> getProducts(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String category,
            @RequestParam(required = false)ProductStatus status,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDirection
            ){
        PageResponse<ProductResponse> response = productService.getProducts(
                keyword, category, status, page, size, sortBy, sortDirection
        );
        return ResponseEntity.ok(response);
    }
    // 상품 상세 조회
    @GetMapping("/{productId}")
    public ResponseEntity<ProductResponse> getProduct(@PathVariable Long productId) {
        ProductResponse response = productService.getProduct(productId);
        return ResponseEntity.ok(response);
    }
    // 상품 정보 수정
    @PatchMapping("/{productId}")
    public ResponseEntity<ProductResponse> update(
            @PathVariable Long productId, @RequestBody ProductUpdateRequest request){
        ProductResponse response = productService.update(productId, request);
        return ResponseEntity.ok(response);
    }
    // 상품 재고 변경
    @PatchMapping("/{productId}/stock")
    public ResponseEntity<ProductResponse> updateStock(
            @PathVariable Long productId,
            @RequestBody ProductStockUpdateRequest request
    ) {
        ProductResponse response = productService.updateStock(productId, request);
        return ResponseEntity.ok(response);
    }
}