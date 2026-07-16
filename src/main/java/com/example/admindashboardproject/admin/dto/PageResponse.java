package com.example.admindashboardproject.admin.dto;
import lombok.Getter;
import org.springframework.data.domain.Page;
import java.util.List;

@Getter
public class PageResponse<T> {
    private final List<T> content;
    private final int currentPage;
    private final int pageSize;
    private final long totalCount;
    private final int totalPages;

    public PageResponse(Page<T> page) {
        this.content = page.getContent();
        this.currentPage = page.getNumber() + 1;
        this.pageSize = page.getSize();
        this.totalCount = page.getTotalElements();
        this.totalPages = page.getTotalPages();
    }
}