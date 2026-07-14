package com.example.admindashboardproject.customer.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;

@Getter
public class CustomerUpdateResponse {
    @Size(min=5,max=100)
    private String name;
    @Email
    private String email;
    @Pattern(regexp = "^010-\\d{4}-\\d{4}$")
    private String phone;
}
