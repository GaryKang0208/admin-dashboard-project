package com.example.admindashboardproject.product.repository;

import com.example.admindashboardproject.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ProductRepository extends JpaRepository<product, Long>,
        JpaSpecificationExecutor<product> {
}
