package com.example.admindashboardproject.customer.dto;

import com.example.admindashboardproject.customer.enums.CustomerStatus;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

@Getter
@Setter
public class CustomerSearchRequest {
        private String keyword;
        private int page =1;
        private int size =10;
        private String sortBy ="createdAt";
        private String direction="desc";
        private CustomerStatus status;

}
