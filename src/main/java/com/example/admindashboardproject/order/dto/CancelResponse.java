package com.example.admindashboardproject.order.dto;
import com.example.admindashboardproject.order.entity.OrderStatus;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CancelResponse {
    private final Long id;
    private final String orderNumber;
    private final OrderStatus status;
    private final String cancelReason;

}
