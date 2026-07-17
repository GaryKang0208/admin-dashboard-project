package com.example.admindashboardproject.product.repository;
import com.example.admindashboardproject.product.entity.Product;
import com.example.admindashboardproject.product.entity.ProductStatus;
import org.springframework.data.jpa.domain.Specification;

public class ProductSpecification {

    public static Specification<Product> hasKeyword(String keyword) {
        return (root, query, cb) -> {
            if (keyword == null || keyword.isBlank()) {
                return null;
            }
            return cb.like(root.get("name"), "%" + keyword + "%");
        };
    }

    public static Specification<Product> hasCategory(String category) {
        return (root, query, cb) -> {
            if (category == null || category.isBlank()) {
                return null;
            }
            return cb.equal(root.get("category"), category);
        };
    }

    public static Specification<Product> hasStatus(ProductStatus status) {
        return (root, query, cb) -> {
            if (status == null) {
                return null;
            }
            return cb.equal(root.get("status"), status);
        };
    }
    public static Specification<Product> notDeleted() {
        return (root, query, cb) -> cb.equal(root.get("deleted"), false);}

}
