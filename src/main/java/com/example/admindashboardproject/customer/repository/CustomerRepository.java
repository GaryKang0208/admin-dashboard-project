package com.example.admindashboardproject.customer.repository;

import com.example.admindashboardproject.customer.entity.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CustomerRepository extends JpaRepository<Customer,Long> {

    Page<Customer> findByNameContainingOrEmailContaining(String keyword, String keyword1, Pageable pageable);
}
