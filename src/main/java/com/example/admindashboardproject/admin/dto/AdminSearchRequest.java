package com.example.admindashboardproject.admin.dto;
import com.example.admindashboardproject.admin.entity.Role;
import com.example.admindashboardproject.admin.entity.Status;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminSearchRequest {
    private String keyword;
    private int page = 1;
    private int size = 10;
    private String sortBy = "createdAt";
    private String sortOrder = "desc";
    private Role role;
    private Status status;
}
