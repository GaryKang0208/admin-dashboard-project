package com.example.admindashboardproject.product.controller;

import com.example.admindashboardproject.admin.dto.SessionAdmin;
import com.example.admindashboardproject.product.dto.PageResponse;
import com.example.admindashboardproject.product.dto.ProductCreateRequest;
import com.example.admindashboardproject.product.dto.ProductResponse;
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
    @GetMapping("/{productId}")
    public ResponseEntity<ProductResponse> getProduct(@PathVariable Long productId) {
        ProductResponse response = productService.getProduct(productId);
        return ResponseEntity.ok(response);
    }
}