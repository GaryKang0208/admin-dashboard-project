package com.example.admindashboardproject.customer.exception;

import com.example.admindashboardproject.customer.enums.CustomerStatus;

import java.time.LocalDateTime;

public class CustomerNotFoundException extends RuntimeException{
    public CustomerNotFoundException(String message){
            super(message);
    }
}
