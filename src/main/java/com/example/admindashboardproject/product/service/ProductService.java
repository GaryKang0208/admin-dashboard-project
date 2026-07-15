package com.example.admindashboardproject.product.service;

import com.example.admindashboardproject.product.dto.PageResponse; // common이 아니라 product.dto!
import com.example.admindashboardproject.product.repository.ProductSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import com.example.admindashboardproject.admin.entity.Admins;
import com.example.admindashboardproject.admin.repository.AdminRepository;
import com.example.admindashboardproject.product.dto.ProductCreateRequest;
import com.example.admindashboardproject.product.dto.ProductResponse;
import com.example.admindashboardproject.product.entity.Product;
import com.example.admindashboardproject.product.entity.ProductStatus;
import com.example.admindashboardproject.product.exception.AdminNotFoundException;
import com.example.admindashboardproject.product.exception.InvalidProductStatusException;
import com.example.admindashboardproject.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductService {

    private final ProductRepository productRepository;
    private final AdminRepository adminRepository;

    @Transactional
    public ProductResponse register(ProductCreateRequest request,Long adminId){
        if (request.stock() > 0 && request.status() == ProductStatus.SOLD_OUT){
            throw new InvalidProductStatusException("재고가 있는 상품은 품절 상태로 등록할 수 없습니다.");
        }
        if (request.stock() == 0 && request.status() == ProductStatus.SELLING){
            throw new InvalidProductStatusException("재고가 없는 상품은 판매중 상태로 등록할 수 없습니다.");
        }

        Admins admin = adminRepository.findById(adminId)
                .orElseThrow(()-> new AdminNotFoundException("존재하지 않는 관리자입니다."));

        Product product = new Product(
                request.name(),
                request.category(),
                request.price(),
                request.stock(),
                request.status(),
                admin
        );

        Product saved = productRepository.save(product);
        return ProductResponse.from(saved);
    }
    // 조회 전용
    public PageResponse<ProductResponse> getProducts(
            String keyword,
            String category,
            ProductStatus status,
            int page,
            int size,
            String sortBy,
            String sortDirection
    ) {
        // 1. 검색 조건 조립 (조건 없는 건 null -> 자동으로 무시됨)
        Specification<Product> spec = Specification
                .where(ProductSpecification.hasKeyword(keyword))
                .and(ProductSpecification.hasCategory(category))
                .and(ProductSpecification.hasStatus(status));

        // 2. 정렬 방향 결정 ("desc"일 때만 내림차순, 그 외엔 오름차순 기본값)
        Sort.Direction direction = "desc".equalsIgnoreCase(sortDirection)
                ? Sort.Direction.DESC
                : Sort.Direction.ASC;
        Sort sort = Sort.by(direction, sortBy);

        // 3. 페이지 번호 보정 - 사용자는 1페이지부터, JPA는 0페이지부터
        Pageable pageable = PageRequest.of(page - 1, size, sort);

        // 4. 실제 조회 실행
        Page<Product> productPage = productRepository.findAll(spec, pageable);

        // 5. Page<Product> -> Page<ProductResponse> 변환
        Page<ProductResponse> responsePage = productPage.map(ProductResponse::from);

        // 6. 우리 스펙에 맞는 PageResponse 형태로 감싸서 반환
        return PageResponse.from(responsePage);
    }


}
