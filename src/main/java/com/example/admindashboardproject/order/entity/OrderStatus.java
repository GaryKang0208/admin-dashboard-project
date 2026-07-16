package com.example.admindashboardproject.order.entity;

public enum OrderStatus {
    PREPARING,
    SHIPPING,
    DELIVERED,
    CANCELED;
    public boolean canTransitionTo(OrderStatus next) {
        return switch (this) {
            case PREPARING -> next == SHIPPING;
            case SHIPPING -> next == DELIVERED;
            default -> false;
        };
    }
}
