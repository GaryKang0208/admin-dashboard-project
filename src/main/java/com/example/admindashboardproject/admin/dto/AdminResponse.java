package com.example.admindashboardproject.admin.dto;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class AdminResponse {
    private final String name;
    private final String email;
}
