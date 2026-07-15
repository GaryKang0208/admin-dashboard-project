package com.example.admindashboardproject.order.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor

public class PageInfo {

    private final int currentPage;
    private final int pageSize;
    private final Long totalElements;
    private final int totalPages;
}
