package com.example.admindashboardproject.order.dto;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class CreateRequest {

    @NotBlank
    private  Long customerId;

    @NotBlank
    private Long productId;

    @NotNull
    @Min(1)
    private Integer quantity;

}
