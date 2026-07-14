package com.example.admindashboardproject.customer.repository;

import com.example.admindashboardproject.customer.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer,Long> {
    List<Customer> findByName(String name);

    List<Customer> findByEmail(String email);

    List<Customer> findByPhone(String phone);

}
