package com.example.admindashboardproject.order.service;
import com.example.admindashboardproject.admin.entity.Admins;
import com.example.admindashboardproject.admin.repository.AdminRepository;
import com.example.admindashboardproject.customer.entity.Customer;
import com.example.admindashboardproject.customer.repository.CustomerRepository;
import com.example.admindashboardproject.order.dto.*;
import com.example.admindashboardproject.order.entity.OrderStatus;
import com.example.admindashboardproject.order.entity.Orders;
import com.example.admindashboardproject.order.exception.*;
import com.example.admindashboardproject.order.repository.OrderRepository;
import com.example.admindashboardproject.product.entity.Product;
import com.example.admindashboardproject.product.entity.ProductStatus;
import com.example.admindashboardproject.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
// Service 구현
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
            orderPage = orderRepository.findByStatusAndOrderNumberContainingOrStatusAndCustomerNameContaining(
                    status, keyword, status, keyword, pageable
            );
        } else if (keyword != null && !keyword.isBlank()) {
            orderPage = orderRepository.findByOrderNumberContainingOrCustomerNameContaining(
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

    public GetOrderDetailResponse getOrderDetail(Long id) {
        Orders order = orderRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("주문을 찾을 수 없습니다."));
        Admins admin = order.getAdmin(); // null이면 고객 직접 주문
        return new GetOrderDetailResponse(
                order.getOrderNumber(),
                order.getCustomer().getName(),
                order.getCustomer().getEmail(),
                order.getProduct().getName(),
                order.getQuantity(),
                order.getTotalPrice(),
                order.getCreatedAt(),
                order.getStatus(),
                admin != null ? admin.getName() : null,
                admin != null ? admin.getEmail() : null,
                admin != null ? admin.getRole().name() : null
        );
    }

    @Transactional
    public UpdateStatusResponse updateStatus(Long id, OrderStatus status) {
        Orders order = orderRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("주문을 찾을 수 없습니다."));
        OrderStatus currentStatus = order.getStatus();
        if (!currentStatus.canTransitionTo(status)) {
            throw new InvalidStatusOrder(
                    String.format("%s 상태에서 %s로 변경할 수 없습니다.", currentStatus, status)
            );
        }
        order.updateStatus(status);
        Orders saved = orderRepository.save(order);
        return new UpdateStatusResponse(saved.getId(), saved.getOrderNumber(), saved.getStatus());
    }

    @Transactional
    public CancelResponse cancelOrder(Long id, String cancelReason) {
        Orders order = orderRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("주문을 찾을 수 없습니다."));
        if (cancelReason == null || cancelReason.isBlank()) {
            throw new InvalidCancelException("취소 사유는 필수입니다.");
        }
        if (order.getStatus() != OrderStatus.PREPARING) {
            throw new InvalidCancelException("준비중 상태에서만 취소할 수 있습니다.");
        }
        order.cancel(cancelReason);
        Product product = order.getProduct();
        restoreStockAndUpdateStatus(product, order.getQuantity());
        productRepository.save(product);
        Orders saved = orderRepository.save(order);
        return new CancelResponse(
                saved.getId(),
                saved.getOrderNumber(),
                saved.getStatus(),
                saved.getCancelReason()
        );
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

    private void decreaseStockAndUpdateStatus(Product product, int quantity) {
        product.updateStock(-quantity);
    }

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

    private void restoreStockAndUpdateStatus(Product product, int quantity) {
        product.updateStock(quantity);
    }
}



























