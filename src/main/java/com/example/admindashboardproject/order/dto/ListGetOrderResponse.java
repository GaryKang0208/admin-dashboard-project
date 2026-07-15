package com.example.admindashboardproject.order.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor

public class ListGetOrderResponse {
    private final List<GetOrderResponse> orders;
    private final PageInfo pageInfo;
}
