package com.example.admindashboardproject.order.entity;

import com.example.admindashboardproject.BaseEntity;
import com.example.admindashboardproject.admin.entity.Admins;
import com.example.admindashboardproject.customer.entity.Customer;
import com.example.admindashboardproject.product.entity.Product;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "orders")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Orders extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50, nullable = false)
    private String orderNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id",nullable = false)
    private Customer customer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id" , nullable = false)
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "admin_id", nullable = false)
    private Admins admin;

    @Column(nullable = false)
    private Integer quantity;

    @Column(nullable = false)
    private Integer totalPrice;


    @Enumerated(EnumType.STRING)
    @Column(length = 50 ,nullable = false)
    private OrderStatus status;

    @Column(columnDefinition = "TEXT")
    private String cancelReason;


    private LocalDateTime cancelAt;

    public Orders(String orderNumber, Customer customer, Product product, Admins admin , Integer quantity){
        this.orderNumber = orderNumber;
        this.customer = customer;
        this.product = product;
        this.admin = admin;
        this.quantity = quantity;

        this.totalPrice = product.getPrice() * quantity;
        this.status = OrderStatus.PREPARING;

    }

    public void updateStatus(OrderStatus status) {
        this.status = status;
    }


    public void cancel(String cancelReason) {
        this.status = OrderStatus.CANCELED;
        this.cancelReason = cancelReason;
        this.cancelAt = LocalDateTime.now();
    }









}


