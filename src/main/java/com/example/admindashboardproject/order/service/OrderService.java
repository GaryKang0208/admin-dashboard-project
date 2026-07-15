package com.example.admindashboardproject.order.service;


import com.example.admindashboardproject.admin.entity.Admins;
import com.example.admindashboardproject.admin.repository.AdminRepository;
import com.example.admindashboardproject.order.dto.*;
import com.example.admindashboardproject.order.entity.OrderStatus;
import com.example.admindashboardproject.order.entity.Orders;
import com.example.admindashboardproject.order.exception.InvalidQuantityException;
import com.example.admindashboardproject.order.exception.NotFoundException;
import com.example.admindashboardproject.order.exception.ProductException;
import com.example.admindashboardproject.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final CustomerRepository customerRepository;
    private final AdminRepository adminRepository;

    @Transactional
    public CreateResponse create(CreateRequest request, Long adminId) {

        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new NotFoundException("상품을 찾을 수 없습니다"));
        Customer customer = customerRepository.findById(request.getCustomerId())
                .orElseThrow(() -> new NotFoundException("고객을 찾을 수 없습니다"));
        Admins admin = adminRepository.getReferenceById(adminId);
        validateOrderRequest(product, request.getQuantity());
        decreaseStockAndUpdateStatus(product, request.getQuantity());
        productRepository.save(product);
        String orderNumber = generateOrderNumber();
        Orders order = new Orders(orderNumber, customer, product, admin, request.getQuantity());
        Orders saved = orderRepository.save(order);

        return new CreateResponse(
                saved.getId(),
                saved.getOrderNumber(),
                customer.getName(),
                product.getName(),
                saved.getQuantity(),
                saved.getTotalPrice(),
                saved.getStatus(),
                saved.getCreatedAt()
        );
    }
    public ListGetOrderResponse getOrders(
            String keyword, int page, int size, String sortBy, String sortOrder, OrderStatus status) {
        String sortProperty = resolveSortProperty(sortBy);
        Sort sort = sortOrder.equalsIgnoreCase("desc")
                ? Sort.by(sortProperty).descending()
                : Sort.by(sortProperty).ascending();
        Pageable pageable = PageRequest.of(page - 1, size, sort);
        Page<Orders> orderPage;
        if (keyword != null && !keyword.isBlank() && status != null) {
            orderPage = orderRepository.findByStatusAndOrderNumberContainingOrStatusAndCustomerCustomerNameContaining(
                    status, keyword, status, keyword, pageable
            );
        } else if (keyword != null && !keyword.isBlank()) {
            orderPage = orderRepository.findByOrderNumberContainingOrCustomerCustomerNameContaining(
                    keyword, keyword, pageable
            );
        } else if (status != null) {
            orderPage = orderRepository.findByStatus(status, pageable);
        } else {
            orderPage = orderRepository.findAll(pageable);
        }
        List<GetOrderResponse> orders = orderPage.getContent()
                .stream()
                .map(this::toResponse)
                .toList();

        PageInfo pageInfo = new PageInfo(
                orderPage.getNumber() + 1,
                orderPage.getSize(),
                orderPage.getTotalElements(),
                orderPage.getTotalPages()
        );

        return new ListGetOrderResponse(orders, pageInfo);
    }











    private GetOrderResponse toResponse(Orders order) {
        return new GetOrderResponse(
                order.getId(),
                order.getOrderNumber(),
                order.getCustomer().getName(),
                order.getProduct().getName(),
                order.getQuantity(),
                order.getTotalPrice(),
                order.getCreatedAt(),
                order.getStatus(),
                order.getAdmin() != null ? order.getAdmin().getName() : null
        );
    }

    private String resolveSortProperty(String sortBy) {
        return switch (sortBy) {
            case "quantity" -> "quantity";
            case "totalPrice" -> "totalPrice";
            case "createdAt" -> "createdAt";
            default -> "createdAt";
        };
    }
}













    private void decreaseStockAndUpdateStatus(Product product, int quantity) {
        int remainingStock = product.getStock() - quantity;
        product.setStock(remainingStock);

        if (product.getStatus() != ProductStatus.DISCONTINUED) {
            product.setStatus(resolveStatusByStock(remainingStock));
        }
    }

    private ProductStatus resolveStatusByStock(int stock) {
        return (stock == 0) ? ProductStatus.SOLD_OUT : ProductStatus.ON_SALE;
    }

    // ==========================================================

    private void validateOrderRequest(Product product, Integer quantity) {
        if (quantity < 1) {
            throw new InvalidQuantityException("수량은 1 이상이어야 합니다");
        }
        if (product.getStatus() == ProductStatus.DISCONTINUED) {
            throw new ProductException("상품이 단종 상태입니다");
        }
        if (product.getStatus() == ProductStatus.SOLD_OUT) {
            throw new ProductException("상품이 품절 상태입니다");
        }
        if (product.getStock() < quantity) {
            throw new InvalidQuantityException("재고가 부족합니다.");
        }
    }

    private String generateOrderNumber() {
        String datePart = LocalDate.now().format(DateTimeFormatter.BASIC_ISO_DATE);
        String randomPart = UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        return "ORD-" + datePart + "-" + randomPart;
    }



}




















