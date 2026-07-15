package com.example.admindashboardproject.product.exception;

public class AdminNotFoundException extends RuntimeException{
    public AdminNotFoundException(String message){
        super(message);
    }
}
