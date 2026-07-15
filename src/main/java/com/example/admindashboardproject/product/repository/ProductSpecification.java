package com.example.admindashboardproject.product.repository;

import com.example.admindashboardproject.product.entity.Product;
import com.example.admindashboardproject.product.entity.ProductStatus;
import org.springframework.data.jpa.domain.Specification;

// 검색/필터 조건을 하나하나 "블록"으로 만들어두는 클래스
// 여기 메서드들은 전부 static이라 객체 안 만들고 ProductSpecification.hasKeyword(...) 이렇게 바로 호출
public class ProductSpecification {

    // 검색 키워드(상품명) - 부분 일치
    public static Specification<Product> hasKeyword(String keyword) {
        return (root, query, cb) -> {
            if (keyword == null || keyword.isBlank()) {
                return null; // null 반환 = "이 조건은 무시해라" (WHERE절에 안 붙음)
            }
            return cb.like(root.get("name"), "%" + keyword + "%");
        };
    }

    // 카테고리 필터 - 정확히 일치
    public static Specification<Product> hasCategory(String category) {
        return (root, query, cb) -> {
            if (category == null || category.isBlank()) {
                return null;
            }
            return cb.equal(root.get("category"), category);
        };
    }

    // 상태 필터 - 정확히 일치
    public static Specification<Product> hasStatus(ProductStatus status) {
        return (root, query, cb) -> {
            if (status == null) {
                return null;
            }
            return cb.equal(root.get("status"), status);
        };
    }
}
