package com.example.admindashboardproject.admin.dto;

import com.example.admindashboardproject.admin.entity.Role;
import com.example.admindashboardproject.admin.entity.Status;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor

public class AdminResponse {
    private final Long id;
    private final String name;
    private final String email;
    private final String phone;
    private final Role role;
    private final Status status;
}
