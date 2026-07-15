package com.example.admindashboardproject.admin.entity;

import com.example.admindashboardproject.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "admins")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Admins extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50, nullable = false)
    private String name;

    @Column(length = 50, nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String phone;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;

    @Column
    private LocalDateTime approvedAt;

    @Column
    private LocalDateTime rejectedAt;

    @Column(columnDefinition = "TEXT")
    private String rejectReason; // <- 거부 사유라 LocalDateTime에서 String으로 수정

    public Admins(String name, String email, String password, String phone, Role role) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.role = role;
        this.status = Status.PENDING; // 회원 가입시 승인대기 상태를 기본값으로
    }

    // 관리자 정보 관리 비즈니스 메서드
    public void updateInfo(String name, String email, String phone) {
        this.name = name;
        this.email = email;
        this.phone = phone;
    }

    public void changeRole(Role role) {
        this.role = role;
    }

    public void changeStatus(Status status) {
        this.status = status;
    }

    public void approve() {
        this.status = Status.ACTIVE;
        this.approvedAt = LocalDateTime.now();
    }

    public void reject(String reason) {
        this.status = Status.REJECTED;
        this.rejectedAt = LocalDateTime.now();
        this.rejectReason = reason;
    }

    public void changePassword(String encodedPassword) {
        this.password = encodedPassword;
    }


}