package com.example.admindashboardproject.customer.entity;
import com.example.admindashboardproject.BaseEntity;
import com.example.admindashboardproject.customer.enums.CustomerStatus;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.LastModifiedDate;
import java.time.LocalDateTime;

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

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CustomerStatus status;

    private int totalOrders;

    private long totalPurchaseAmount;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    public Customer(String name, String email, String phone, CustomerStatus status, int totalOrders, long totalPurchaseAmount, LocalDateTime updatedAt) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.status = status;
        this.totalOrders = totalOrders;
        this.totalPurchaseAmount = totalPurchaseAmount;
        this.updatedAt = updatedAt;
    }

    public void update(String name, String email, String phone) {
        if (name !=null) {
            this.name = name;
        }
        if (email !=null) {
            this.email = email;
        }
        if (phone !=null) {
            this.phone = phone;
        }
    }

    public void changeStatus(CustomerStatus status) {

        this.status = status;
    }
}
