package com.example.admindashboardproject.customer.entity;

import com.example.admindashboardproject.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "customers")
public class Customer extends BaseEntity {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private String phone;
    @Column(nullable = false)
    private String status;
    private int totalOrders;
    private long totalPurchaseAmount;

    public Customer(String name, String email, String phone, String status, int totalOrders, long totalPurchaseAmount) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.status = status;
        this.totalOrders = totalOrders;
        this.totalPurchaseAmount = totalPurchaseAmount;
    }

    public void update(String name, String email, String phone) {
        this.name = name;
        this.email = email;
        this.phone = phone;
    }
}
