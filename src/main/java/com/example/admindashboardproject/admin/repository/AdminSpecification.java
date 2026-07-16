package com.example.admindashboardproject.admin.repository;
import com.example.admindashboardproject.admin.entity.Admins;
import com.example.admindashboardproject.admin.entity.Role;
import com.example.admindashboardproject.admin.entity.Status;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;
import java.util.ArrayList;
import java.util.List;

public class AdminSpecification {
    public static Specification<Admins> search(String keyword, Role role, Status status) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            // 이름, 이메일에 검색어가 포함되어 있으면 조건 추가
            if (StringUtils.hasText(keyword)) {
                Predicate nameLike = criteriaBuilder.like(root.get("name"), "%" + keyword + "%");
                Predicate emailLike = criteriaBuilder.like(root.get("email"), "%" + keyword + "%");
                predicates.add(criteriaBuilder.or(nameLike, emailLike));
            }
            if (role != null) {
                predicates.add(criteriaBuilder.equal(root.get("role"), role));
            }
            if (status != null) {
                predicates.add(criteriaBuilder.equal(root.get("status"), status));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}