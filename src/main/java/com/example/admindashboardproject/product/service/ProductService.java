package com.example.admindashboardproject.product.service;

import com.example.admindashboardproject.admin.entity.Admins;
import com.example.admindashboardproject.admin.repository.AdminRepository;
import com.example.admindashboardproject.product.dto.ProductCreateRequest;
import com.example.admindashboardproject.product.dto.ProductResponse;
import com.example.admindashboardproject.product.entity.Product;
import com.example.admindashboardproject.product.entity.ProductStatus;
import com.example.admindashboardproject.product.exception.AdminNotFoundException;
import com.example.admindashboardproject.product.exception.InvalidProductStatusException;
import com.example.admindashboardproject.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductService {

    private final ProductRepository productRepository;
    private final AdminRepository adminRepository;

    @Transactional
    public ProductResponse register(ProductCreateRequest request,Long adminId){
        if (request.stock() > 0 && request.status() == ProductStatus.SOLD_OUT){
            throw new InvalidProductStatusException("재고가 있는 상품은 품절 상태로 등록할 수 없습니다.");
        }
        if (request.stock() == 0 && request.status() == ProductStatus.SELLING){
            throw new InvalidProductStatusException("재고가 없는 상품은 판매중 상태로 등록할 수 없습니다.");
        }

        Admins admin = adminRepository.findById(adminId)
                .orElseThrow(()-> new AdminNotFoundException("존재하지 않는 관리자입니다."));

        Product product = new Product(
                request.name(),
                request.category(),
                request.price(),
                request.stock(),
                request.status(),
                admin
        );

        Product saved = productRepository.save(product);
        return ProductResponse.from(saved);
    }


}
