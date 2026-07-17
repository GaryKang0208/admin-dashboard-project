package com.example.admindashboardproject.product.service;
import com.example.admindashboardproject.product.dto.*;
import com.example.admindashboardproject.product.exception.ProductNotFoundException;
import com.example.admindashboardproject.product.repository.ProductSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import com.example.admindashboardproject.admin.entity.Admins;
import com.example.admindashboardproject.admin.repository.AdminRepository;
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
@Transactional
// Product  Service 구현
public class ProductService {
    private final ProductRepository productRepository;
    private final AdminRepository adminRepository;


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

    @Transactional(readOnly = true)
    public PageResponse<ProductResponse> getProducts(
            String keyword,
            String category,
            ProductStatus status,
            int page,
            int size,
            String sortBy,
            String sortDirection
    ) {
        Specification<Product> spec = Specification
                .where(ProductSpecification.hasKeyword(keyword))
                .and(ProductSpecification.hasCategory(category))
                .and(ProductSpecification.hasStatus(status))
                .and(ProductSpecification.notDeleted());
        Sort.Direction direction = "desc".equalsIgnoreCase(sortDirection)
                ? Sort.Direction.DESC
                : Sort.Direction.ASC;
        Sort sort = Sort.by(direction, sortBy);
        Pageable pageable = PageRequest.of(page - 1, size, sort);
        Page<Product> productPage = productRepository.findAll(spec, pageable);
        Page<ProductResponse> responsePage = productPage.map(ProductResponse::from);
        return PageResponse.from(responsePage);
    }

    @Transactional(readOnly = true)
    public ProductResponse getProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("존재하지 않는 상품입니다."));
        if (product.isDeleted()) {
            throw new ProductNotFoundException("존재하지 않는 상품입니다.");
        }
        return ProductResponse.from(product);
    }

    public ProductResponse update(Long id, ProductUpdateRequest request) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("존재하지 않는 상품입니다."));
        product.update(request.name(), request.category(), request.price());
        if (product.isDeleted()) {
            throw new ProductNotFoundException("존재하지 않는 상품입니다.");
        }
        return ProductResponse.from(product);
    }

    public ProductResponse updateStock(Long id, ProductStockUpdateRequest request) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("존재하지 않는 상품입니다."));
        product.updateStock(request.stock());
        if (product.isDeleted()) {
            throw new ProductNotFoundException("존재하지 않는 상품입니다.");
        }
        return ProductResponse.from(product);
    }

    public ProductResponse updateStatus(Long id, ProductStatusUpdateRequest request) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("존재하지 않는 상품입니다."));
        product.changeStatus(request.status());
        if (product.isDeleted()) {
            throw new ProductNotFoundException("존재하지 않는 상품입니다.");
        }
        return ProductResponse.from(product);
    }

    public void delete(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("존재하지 않는 상품입니다."));
        product.softDelete();
    }
}
