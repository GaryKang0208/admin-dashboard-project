package com.example.admindashboardproject.order.dto;
import com.example.admindashboardproject.order.entity.OrderStatus;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class GetOrderResponse {
    private final Long id;
    private final String orderNumber;
    private final String customerName;
    private final String productName;
    private final Integer quantity;
    private final Integer totalPrice;
    private final LocalDateTime createdAt;
    private final OrderStatus status;
    private final String adminName;

}
