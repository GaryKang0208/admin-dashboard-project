package com.example.admindashboardproject.order.dto;
import com.example.admindashboardproject.order.entity.OrderStatus;
import lombok.Getter;

@Getter
public class UpdateStatusRequest {
    private OrderStatus status;
}
