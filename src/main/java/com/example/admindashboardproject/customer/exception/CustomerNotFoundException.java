package com.example.admindashboardproject.customer.exception;
public class CustomerNotFoundException extends RuntimeException{
    public CustomerNotFoundException(String message) {
            super(message);
    }
}
