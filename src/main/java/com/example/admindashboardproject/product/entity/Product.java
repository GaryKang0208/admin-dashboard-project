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
        int newStock = this.stock + quantity;
        if (newStock < 0) {
            throw new InvalidStockException("재고는 0 미만이 될 수 없습니다.");
        }
        this.stock = newStock;
        updateStatusByStock();
    }

    public void changeStatus(ProductStatus status) {
        this.status = status;
    }

    private boolean deleted = false;
    public boolean isDeleted() {
        return deleted;
    }
    public void softDelete() {
        this.deleted = true;
    }
}