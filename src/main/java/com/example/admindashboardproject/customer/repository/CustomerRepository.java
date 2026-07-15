package com.example.admindashboardproject.customer.repository;

import com.example.admindashboardproject.customer.entity.Customer;
import com.example.admindashboardproject.customer.enums.CustomerStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface CustomerRepository extends JpaRepository<Customer,Long> {

    Page<Customer> findByNameContainingOrEmailContaining(String keyword, String keyword1, Pageable pageable);

    Page<Customer> findByStatus(CustomerStatus status, Pageable pageable);

    @Query("""
        SELECT c
        FROM Customer c
        WHERE (c.name LIKE %:keyword%
            OR c.email LIKE %:keyword%)
        AND c.status = :status
        """)
    Page<Customer> findByKeywordAndStatus(
            @Param("keyword") String keyword,
            @Param("status") CustomerStatus status,
            Pageable pageable
    );
}