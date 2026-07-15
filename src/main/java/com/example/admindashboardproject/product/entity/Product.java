package com.example.admindashboardproject.product.entity;

import com.example.admindashboardproject.product.exception.InvalidStockException;
import com.example.admindashboardproject.BaseEntity;
import com.example.admindashboardproject.admin.entity.Admins;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "products")
@Getter
@NoArgsConstructor
public class Product extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String category;

    private Integer price;

    private Integer stock;

    @Enumerated(EnumType.STRING)
    private ProductStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    // ERD의 admin_id FK 컬럼과 매칭시킴
    // 관리자 정보가 바뀌어도(개명, 이메일 변경) products 테이블을 따로 안 고쳐도 됨
    // "등록 관리자명, 이메일을 응답에 포함해야 한다"는 요구사항을 registeredAdmin.getName() 형태로 그대로 조회 가능
    @JoinColumn(name = "admin_id")
    private Admins registeredAdmin;

    public Product(String name, String category, Integer price, Integer stock, ProductStatus status, Admins registeredAdmin) {
        this.name = name;
        this.category = category;
        this.price = price;
        this.stock = stock;
        this.status = status;
        this.registeredAdmin = registeredAdmin;
    }
    public void update(String name, String category, Integer price) {
        // 요청 값이 null이 아닐 때만 필드를 바꿈
        if (name != null) {
            this.name = name;
        }
        if (category != null) {
            this.category = category;
        }
        if (price != null) {
            this.price = price;
        }
    }
    public void updateStatusByStock(){
        if (this.status == ProductStatus.DISCONTINUED){
            return;
        }
        if (this.stock == 0){
            this.status = ProductStatus.SOLD_OUT;
        }
        else {
            this.status = ProductStatus.SELLING;
        }
    }
    public void updateStock(int quantity) {
        int newStock = this.stock + quantity; // 증감값을 현재 재고에 더함(음수면 자동 차감)

        if (newStock < 0) {
            throw new InvalidStockException("재고는 0 미만이 될 수 없습니다.");
        }

        this.stock = newStock;
        updateStatusByStock(); // 이미 있는 메서드를 그대로 재사용해서 상태 재계산
    }
    // 상태를 사용자가 보낸 값으로 그대로 교체 (검증 없음)
    public void changeStatus(ProductStatus status) {
        this.status = status;
    }
}