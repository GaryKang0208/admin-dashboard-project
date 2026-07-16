package com.example.admindashboardproject.order.repository;
import com.example.admindashboardproject.order.entity.OrderStatus;
import com.example.admindashboardproject.order.entity.Orders;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Orders,Long> {
    Page<Orders> findAll(Pageable pageable);
    Page<Orders> findByStatus(OrderStatus status, Pageable pageable);
    Page<Orders> findByOrderNumberContainingOrCustomerNameContaining(
            String orderNumber,
            String customerName,
            Pageable pageable
    );
    Page<Orders> findByStatusAndOrderNumberContainingOrStatusAndCustomerNameContaining(
            OrderStatus status1,
            String orderNumber,
            OrderStatus status2,
            String customerName,
            Pageable pageable
    );
    void deleteByCustomer_Id(Long customerId);
}